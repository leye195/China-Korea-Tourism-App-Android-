package com.example.leeyoungjae.fragmentapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Bean.Result;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.YoujiUploadRequest;
import com.example.leeyoungjae.fragmentapp.Service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteYoujiActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 1000;
    private static final int REQUEST_IMAGE = 1001;
    private String userid;
    private String place;
    private String title,content;
    private ArrayList<String> phs;
    private List<String> path;
    private  StringBuilder sub_imgs;
    private File[]filenames;
    @BindView(R.id.head_img)
    ImageView iv_photo;
    @BindView(R.id.add_head)
    TextView iv_text;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.rbtn1)
    RadioButton rbtn1;
    @BindView(R.id.rbtn2)
    RadioButton rbtn2;
    @BindView(R.id.add_sub_photo)
    Button select_subbtn;
    @BindView(R.id.send_btn)
    FloatingActionButton sendbtn;
    private Bitmap bitmap;
    private Retrofit retrofit;
    private PostService postService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_youji);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        phs=new ArrayList<>();
        path=new ArrayList<>();
        sub_imgs=new StringBuilder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/Post/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        postService=retrofit.create(PostService.class);
        ButterKnife.bind(this);
    }
    @OnCheckedChanged({R.id.rbtn1,R.id.rbtn2})
    public void select_place(CompoundButton view, boolean ischanged){
        switch (view.getId()){
            case R.id.rbtn1:
                if(ischanged){
                    rbtn1.setChecked(true);
                    rbtn2.setChecked(false);
                    place="韩国";
                }
                break;
            case R.id.rbtn2:
                if(ischanged){
                    rbtn2.setChecked(true);
                    rbtn1.setChecked(false);
                    place="中国";
                }
                break;
            default:
                break;
        }
    }
    /*@OnClick(R.id.send_btn)
    public void send_clicked(){
        TextView title_text=findViewById(R.id.title_text);
        TextView content_text=findViewById(R.id.content_text);
        //发送游记,保存游记数据
        //在一个路径里创建一个folder并保存img，
        userid=getIntent().getStringExtra("userID");
        title=title_text.getText().toString();
        content=content_text.getText().toString();
        //用户名，(用户照)，题目，地方，内容，时间，访问数，封面路径
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse=new JSONObject(response);
                    String Res=jsonResponse.getString("response");
                    Toast.makeText(WriteYoujiActivity.this,Res,Toast.LENGTH_SHORT).show();
                    if(Res.toString().equals("Upload_Success")){
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        //上传
        YoujiUploadRequest youjiUploadRequest;
        youjiUploadRequest=new YoujiUploadRequest(userid,place,title,content,"0","0",bitmap,sub_imgs.toString(),responseListener);
        RequestQueue queue = Volley.newRequestQueue(WriteYoujiActivity.this);
        queue.add(youjiUploadRequest);
    }*/
    @OnClick(R.id.send_btn)
    public void send_clicked2(){
        TextView title_text=findViewById(R.id.title_text);
        TextView content_text=findViewById(R.id.content_text);

        userid=getIntent().getStringExtra("userID");
        title=title_text.getText().toString();
        content=content_text.getText().toString();

        RequestBody userid_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),userid);
        RequestBody title_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),title);
        RequestBody content_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),content);
        RequestBody place_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),place);
        RequestBody upload_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),"0");
        RequestBody visitor_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),"0");
        RequestBody img_Request=RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"),imgToString(bitmap));

        Map<String,RequestBody>parameters=new HashMap<>();
        parameters.put("userId",userid_Request);
        parameters.put("place",place_Request);
        parameters.put("title",title_Request);
        parameters.put("content",content_Request);
        parameters.put("upload",upload_Request);
        parameters.put("visitor",visitor_Request);
        parameters.put("img",img_Request);

        List<MultipartBody.Part>parts=new ArrayList<>(path.size());
        for(int i=0;i<filenames.length;i++){
            String filenameByTimeStamp= String.valueOf(System.currentTimeMillis())+i+".jpg";
            RequestBody requestFile= RequestBody.create(MediaType.parse("multipart/form-data"),filenames[i]);
            MultipartBody.Part part = MultipartBody.Part.createFormData("sub_imgs[]",filenameByTimeStamp,requestFile);
            parts.add(part);
        }
        Call<Result>call=postService.uploadPost(parameters,parts);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                Log.d("Status",""+response.code());
                if(response.isSuccessful()){
                    Log.d("Post Upload","Success");
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Log.d("Post Upload","Failed check your network connection "+response.code());
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("Upload Error",t.getMessage());
            }
        });
    }
    @OnClick(R.id.add_sub_photo)
    public void onAddSubPicture(){
        MultiImageSelector.create(getApplicationContext())
                .showCamera(false)
                .multi()
                .origin(phs)
                .start(this,REQUEST_IMAGE);
    }
    public void onAddPicture(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
    }
    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[]imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST_CODE&&resultCode==RESULT_OK&&null!=data){
            Uri uri=data.getData();
            try{
                //나중에 이미지뷰에 뿌려주기 위해 담아놓음.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv_photo.setImageBitmap(bitmap);
                iv_photo.setVisibility(View.VISIBLE);
                iv_text.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(requestCode==REQUEST_IMAGE){
            if(data!=null){
                List<String>path = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                // 处理你自己的逻辑
                for(String s:path){
                    sub_imgs.append(s);
                    sub_imgs.append("\n");
                }
                TextView sub_count=findViewById(R.id.sub_count);
                sub_count.setText(path.size()+"/9");
                Log.d("tag", "Images: " + path);
                String filenameByTimeStamp= String.valueOf(System.currentTimeMillis())+".jpg";
                Log.d("FileName",filenameByTimeStamp);
                filenames=new File[path.size()];
                for(int i=0;i<path.size();i++){
                    filenames[i]=new File(path.get(i));
                    Log.d("Img",filenames[i].getAbsolutePath());
                    if(!filenames[i].exists()){
                        Log.e("fail","not exist");
                        return;
                    }
                }
            }
        }
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
}
