package com.example.leeyoungjae.fragmentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Activity.YoujiDetailActivity;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.DeletePostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class postsAdapter extends BaseAdapter {
    private List<Youji> uList;
    private List<Youji> saveList;

    private Context context;
    private Activity parentActivity;
    private TextView cnt;
    private static boolean[] checks;
    private String[] b_num;//同样定义数组来保存Textview的状态

    public postsAdapter(Context context, List<Youji>uList, List<Youji>saveList, TextView cnt, Activity parentActivity){
        this.uList=uList;
        this.saveList=saveList;
        this.context=context;
        this.parentActivity=parentActivity;
        this.cnt=cnt;
        checks=new boolean[uList.size()];
        b_num=new String[uList.size()];
        for(int i=0;i<uList.size();i++){
            b_num[i]="1";
        }
    }
    @Override
    public int getCount() {
        return uList.size();
    }

    @Override
    public Object getItem(int position) {
        return uList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=convertView.inflate(context,R.layout.mypost2,null);
        //final TextView user_id=v.findViewById(R.id.user_id);
        final TextView title=v.findViewById(R.id.post_title);
        final TextView upload=v.findViewById(R.id.upload_date);
        final ImageView img=v.findViewById(R.id.post_img);
        final String post_id=uList.get(position).getPostid();
        final String imgUrl=uList.get(position).getImgUrl();
        final String content=uList.get(position).getContent();
        final String poster=uList.get(position).getUserID();
        final String user_id=uList.get(position).getUserID();
        //user_id.setText("ID: "+uList.get(position).getUserID());
        //final int pos=position;
        title.setText(uList.get(position).getTitle());
        upload.setText(uList.get(position).getUpload_date());
        Picasso.with(context)
                .load(imgUrl).into(img);
        v.setTag(uList.get(position).getUserID());

        /*CheckBox checkBox=v.findViewById(R.id.c_btn);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[pos]=isChecked;
            }
        });
        //checkBox.setChecked(checks[pos]);*/
        Button delete_btn=v.findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Toast.makeText(parentActivity,uList.get(position).getPostid(),Toast.LENGTH_SHORT).show();
                Response.Listener<String>responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json=new JSONObject(response);
                            boolean success=json.getBoolean("success");
                            if(success){
                                uList.remove(position);
                                for(int i=0;i<saveList.size();i++){
                                    if(saveList.get(position).getTitle().equals(title.getText().toString())&&saveList.get(position).getUpload_date().equals(upload.getText().toString())){
                                        saveList.remove(position);
                                        break;
                                    }
                                }
                                Toast.makeText(parentActivity,"删除成功",Toast.LENGTH_SHORT).show();
                                cnt.setText(uList.size()+"篇游记");
                                notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(parentActivity,"删除失败",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeletePostRequest deleteRequest=new DeletePostRequest(post_id.toString(),user_id.toString(),title.getText().toString(),responseListener);
                RequestQueue queue=Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });
        LinearLayout linearLayout=v.findViewById(R.id.content);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parentActivity,uList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(),YoujiDetailActivity.class);
                intent.putExtra("postid",post_id);
                intent.putExtra("userid",user_id);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("content",content);
                intent.putExtra("imgUrl",imgUrl);
                intent.putExtra("poster",user_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//直接使用context的startActivity则会报上面的错误，根据错误提示信息,可以得知,如果要使用这种方式需要打开新的TASK
                v.getContext().startActivity(intent);
            }
        });
        return v;
    }
}
