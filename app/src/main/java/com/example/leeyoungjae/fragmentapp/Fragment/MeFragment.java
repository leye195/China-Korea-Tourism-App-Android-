package com.example.leeyoungjae.fragmentapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Activity.LoginActivity;
import com.example.leeyoungjae.fragmentapp.Activity.Main2Activity;
import com.example.leeyoungjae.fragmentapp.Activity.MyYoujiActivity;
import com.example.leeyoungjae.fragmentapp.R;


public class MeFragment extends Fragment {
    private String userID="";
    private boolean isLogin=false;
    private SharedPreferences sp;
    private Bundle bundle;
    public MeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v= (ViewGroup) inflater.inflate(R.layout.fragment_me, container, false);
        Button loginForm=v.findViewById(R.id.ToLogin);
        TextView textView=v.findViewById(R.id.me_text);
        Button SignOutButton=v.findViewById(R.id.user_logout);
        Button myYouji=v.findViewById(R.id.my_youji);
        //userID=getArguments().getString("userID","");
        CheckLogin();
        System.out.println(":???:"+userID);
        savedInstanceState=getArguments();
        //userID=savedInstanceState.getString("userID","");

        if(userID.equals("")){
            Button manage=v.findViewById(R.id.user_list);
            manage.setVisibility(View.GONE);
            SignOutButton.setVisibility(View.GONE);
            loginForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivityForResult(intent,1000);
                }
            });
            myYouji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            if(!userID.equals("admin")){
                Button manage=v.findViewById(R.id.user_list);
                manage.setVisibility(View.GONE);
            }
            loginForm.setVisibility(View.GONE);
            ImageView userimg=v.findViewById(R.id.user_img);
            textView.setText(userID);
            SignOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_SHORT).show();
                    sp=getContext().getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(getActivity(),Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            });
            myYouji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),MyYoujiActivity.class);
                    intent.putExtra("userId",userID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        return v;
    }
    private void CheckLogin(){
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        userID=sp.getString("loginUserName","");
        isLogin=sp.getBoolean("isLogin",false);
    }
}
