package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.ValidateCheckRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.user_id)
    EditText user_id;
    @BindView(R.id.user_pw)
    EditText user_pw;
    @BindView(R.id.user_pw_ag)
    EditText user_pw_ag;
    @BindView(R.id.gender_rg)
    RadioGroup gender;
    @BindView(R.id.regist)
    Button signUpBtn;
    @BindView(R.id.check_valid)
    Button checkValid;
    private String gen;
    private boolean success;
    private boolean checkid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    @OnCheckedChanged({R.id.gender_m,R.id.gender_f})
    public void check_gender(CompoundButton view,boolean ischanged){
        switch (view.getId()){
            case R.id.gender_m:
                if(ischanged){
                    gen = "male";
                }
                break;
            case R.id.gender_f:
                if(ischanged){
                    gen = "female";
                }
                break;
            default:
                break;
        }
    }
    @OnClick(R.id.regist)
    public void signUpBtn(){
        String userid = user_id.getText().toString();
        String userpw = user_pw.getText().toString();
        String userpw_ag = user_pw_ag.getText().toString();
        String gender = gen;
        if (!userpw.equals(userpw_ag))
            Toast.makeText(RegisterActivity.this, "请检查密码", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(userid))
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(userpw))
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(gender))
            Toast.makeText(RegisterActivity.this,"请选择性别",Toast.LENGTH_SHORT).show();
        else {
            if(checkid==true)
                new HttpAsyncTask().execute(userid, userpw, gender);
            else
                Toast.makeText(getApplicationContext(),"请检查你的id是否已被占用",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.check_valid)
    public void checkValid(){
        String url="http://10.0.2.2/develop/Travel/users/CheckValidate.php?userId="+user_id.getText().toString();
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject json=new JSONObject(response);
                    boolean success=json.getBoolean("validate");
                    Log.i("TAG",success+"??");
                    if(success==true){
                        checkid=true;
                        Toast.makeText(getApplicationContext(),"此ID可以使用",Toast.LENGTH_SHORT).show();
                    }else{
                        checkid=false;
                        Toast.makeText(getApplicationContext(),"此ID已被使用",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        ValidateCheckRequest loginRequest=new ValidateCheckRequest(url,responseListener);
        RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
        queue.add(loginRequest);
    }
    public void onBack(View view) {
        super.onBackPressed();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, boolean[]> {
        private AlertDialog dialog;
        private boolean[] successs={true};

        @Override
        protected boolean[] doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userId", strings[0])
                    .add("userPassword", strings[1])
                    .add("gender", strings[2])
                    .build();
            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://10.0.2.2/develop/Travel/Register.php")
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", "NetWork connection Failure: " + e);
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        successs[0] = json.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return successs;
        }
        @Override
        protected void onPostExecute(boolean[] flags) {
            super.onPostExecute(flags);
            if(flags[0]){
                success=flags[0];
                Log.i("TAG","POST:"+success);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                dialog=builder.setMessage("注册成功").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                dialog.show();
            }else{
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                dialog=builder.setMessage("注册失败").setNegativeButton("重试",null).create();
                dialog.show();
            }
        }
    }
}