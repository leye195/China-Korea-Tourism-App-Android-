package com.example.leeyoungjae.fragmentapp.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Request.LoginRequest;
import com.example.leeyoungjae.fragmentapp.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    // UI references.
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.sign_in_button)
    Button SignInButton;
    @BindView(R.id.register_button)
    Button RegisterButton;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.sign_in_button)
    public void SignInClicked(){
        final String userID=mEmailView.getText().toString();
        final String userPW=mPasswordView.getText().toString();
        Log.i("TAG","Clicked");
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try{
                    JSONObject json=new JSONObject(response);
                    boolean success=json.getBoolean("success");
                    Log.i("TAG",success+"??");
                    if(success){
                        Intent intent=null;
                        String userID=json.getString("userId");
                        String userPW=json.getString("userPassword");
                        Log.i("TAG", userID + ":" + userPW);
                        intent = new Intent(LoginActivity.this, Main2Activity.class);
                        intent.putExtra("userId", userID);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(LoginActivity.this, userID + ":" + userPW, Toast.LENGTH_SHORT).show();
                        setResult(1000,intent);
                        saveLoginStatus(true,userID,userPW);
                        finish();
                        //startActivity(intent);
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("登录失败，id或password错误").setNegativeButton("try Again",null).create().show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        //System.out.println(userID+":"+userPW);
        LoginRequest loginRequest=new LoginRequest(userID,userPW,responseListener);
        RequestQueue queue=Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
    @OnClick(R.id.register_button)
    public void register_Clicked(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void saveLoginStatus(boolean status,String userName,String password) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        editor.putString("password",password);
        //提交修改
        editor.commit();
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
}

