package com.example.leeyoungjae.fragmentapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Adapter.LinearLayoutBaseAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.SightRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.gallery_viewAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.CommentList;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
import com.example.leeyoungjae.fragmentapp.Bean.comment;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.CommentUploadRequest;
import com.example.leeyoungjae.fragmentapp.Request.DeletePostRequest;
import com.example.leeyoungjae.fragmentapp.Request.YoujiUploadRequest;
import com.example.leeyoungjae.fragmentapp.Service.CommentService;
import com.example.leeyoungjae.fragmentapp.Service.SightService;
import com.example.leeyoungjae.fragmentapp.View.MyLinearLayoutForListView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoujiDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private String postid;
    private String poster;
    private String imgUrl;
    private String title;
    private String content;
    private String userid="guest";

    private TextView title_txt;
    private TextView content_txt;
    private ImageView img;
    private EditText user_comment;
    private ImageView open_menu;
    private Button send_btn;
    private Dialog mCameraDialog;

    private RecyclerView gallery_view;
    private gallery_viewAdapter g_adapter;
    private ArrayList<String>imgs;
    private String sub_imgs;

    private Retrofit retrofit;
    private CommentService commentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youji_detail);
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        commentService=retrofit.create(CommentService.class);
        poster=getIntent().getStringExtra("poster");
        if(getIntent().getStringExtra("userid")!=null)
            userid=getIntent().getStringExtra("userid");
        if(userid.equals(""))
            userid="guest";
        open_menu=findViewById(R.id.open_menu);
        System.out.println(poster+" "+userid);
        open_menu.setOnClickListener(this);

        postid=getIntent().getStringExtra("postid");
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        imgUrl=getIntent().getStringExtra("imgUrl");
        sub_imgs=getIntent().getStringExtra("sub_imgs");

        img=findViewById(R.id.head_img);
        title_txt=findViewById(R.id.title_text);
        content_txt=findViewById(R.id.content_text);
        user_comment=findViewById(R.id.user_comment);
        send_btn=findViewById(R.id.send_btn);
        if(imgUrl!=null){
            Picasso.with(getApplicationContext())
                    .load(imgUrl)
                    .into(img);
        }else {
            img.setImageResource(R.drawable.ic_launcher_background);
        }
        title_txt.setText(title);
        content_txt.setText(content);
        initData(postid);
        final MyAdapter adapter = new MyAdapter(this, list);
        final MyLinearLayoutForListView forListView = findViewById(R.id.li_list);
        forListView.setAdapter(adapter);
        if(userid=="guest"){
            user_comment.setEnabled(false);
            send_btn.setEnabled(false);
        }
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid!="guest") {
                    if (TextUtils.isEmpty(user_comment.getText())) { //检查要发表的留言内容是否为空
                        Toast.makeText(YoujiDetailActivity.this, "空", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(YoujiDetailActivity.this, user_comment.getText().toString(), Toast.LENGTH_SHORT).show();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date_time=format.format(new Date());
                        comment c=new comment(postid,"",userid,user_comment.getText().toString(),date_time);
                        list.add(c);
                        Response.Listener<String> responseListener=new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonResponse=new JSONObject(response);
                                    String Res=jsonResponse.getString("response");
                                    Toast.makeText(YoujiDetailActivity.this,Res,Toast.LENGTH_SHORT).show();
                                    if(Res.toString().equals("Upload_Success")){
                                        Toast.makeText(YoujiDetailActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        CommentUploadRequest commentUploadRequest=new CommentUploadRequest(userid,postid,user_comment.getText().toString(),responseListener);
                        RequestQueue queue = Volley.newRequestQueue(YoujiDetailActivity.this);
                        queue.add(commentUploadRequest);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(YoujiDetailActivity.this,"请先登入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private List<comment> list;
    private void initData(String postid){
        list = new ArrayList<>();
        getComments(postid);
        getGalley();
    }
    public void getGalley(){
        imgs=new ArrayList<>();
        gallery_view=findViewById(R.id.gallery_view);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        gallery_view.setLayoutManager(manager);
        g_adapter=new gallery_viewAdapter(getApplicationContext());
        if(sub_imgs!=null){
            for(String s:sub_imgs.split(",")){
                imgs.add(s);
            }
        }
        g_adapter.addItems(imgs);
        gallery_view.setAdapter(g_adapter);
    }
    /**
     * 将ScollView 移动到最底端
     * @param scroll ScollView
     * @param inner ScollView里面一层控件的View
     * @param moreHeight 多余的高度
     */
    public static void scrollToBottom(final View scroll, final View inner ,final int moreHeight) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                if (scroll == null || inner == null) {
                    return;
                }
                int offset = inner.getMeasuredHeight() - scroll.getHeight()
                        + moreHeight;
                if (offset < 0) {
                    offset = 0;
                }
                scroll.scrollTo(0, offset);
            }
        });
    }
    public void onMove(View view) {
        Toast.makeText(YoujiDetailActivity.this,"Move",Toast.LENGTH_SHORT).show();
        final ScrollView scroll=findViewById(R.id.head);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部
            }
        });
    }
    public void onBack(View view) {
        super.onBackPressed();
    }

    private void setDialog() {
        mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.button_dialog, null);
        //初始化视图
        root.findViewById(R.id.btn_choose_home).setOnClickListener(this);
        root.findViewById(R.id.btn_choose_cancel).setOnClickListener(this);
        if(userid.equals("guest")||!userid.equals(poster)){
            root.findViewById(R.id.btn_choose_delete).setVisibility(View.GONE);
        }else{
            root.findViewById(R.id.btn_choose_delete).setOnClickListener(this);
        }
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_menu:
                setDialog();
                break;
            case R.id.btn_choose_home:
                mCameraDialog.dismiss();
                final Intent intent=new Intent(YoujiDetailActivity.this,Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_choose_cancel:
                mCameraDialog.dismiss();
                break;
            case R.id.btn_choose_delete:
                Response.Listener<String>responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json=new JSONObject(response);
                            boolean success=json.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                Intent vintent=new Intent();
                                setResult(202,vintent);
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeletePostRequest deletePostRequest=new DeletePostRequest(postid,userid,title,responseListener);
                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                queue.add(deletePostRequest);
                break;
        }
    }
    public void getComments(String postid){
        Call<CommentList>call=commentService.getComments(postid);
        call.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, retrofit2.Response<CommentList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        list=response.body().getResult();
                        //TextView textView=findViewById(R.id.empty_alert);
                        if(list.size()>0) {
                            final MyAdapter adapter = new MyAdapter(YoujiDetailActivity.this, list);
                            final MyLinearLayoutForListView forListView = findViewById(R.id.li_list);
                            forListView.setAdapter(adapter);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
            }
        });
    }
    class MyAdapter extends LinearLayoutBaseAdapter {
        public MyAdapter(Context context, List<comment>list) {
            super(context, list);
        }
        @Override
        public View getView(int position) {
            View view = getLayoutInflater().inflate(R.layout.comment_list, null);
            TextView content_txt = view.findViewById(R.id.content_txt);
            TextView id_txt= view.findViewById(R.id.id_txt);
            TextView datetime_txt=view.findViewById(R.id.datetime_txt);
            content_txt.setText(list.get(position).getContent());
            id_txt.setText("ID: "+list.get(position).getUserid());
            datetime_txt.setText(list.get(position).getDatetime());
            return view;
        }
    }
}
