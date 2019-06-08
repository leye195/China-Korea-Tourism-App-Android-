package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.leeyoungjae.fragmentapp.Fragment.LeftFragment;
import com.example.leeyoungjae.fragmentapp.Fragment.MeFragment;
import com.example.leeyoungjae.fragmentapp.Fragment.PostFragment;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
import com.example.leeyoungjae.fragmentapp.Fragment.YoujiFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private String UserID="";
    private boolean isLogin;
    private List<Youji> youji_list;
    //private GridView youjigrid;
    private LeftFragment fragment1=null;
    private PostFragment fragment3=null;
    //private YoujiFragment fragment3=null;
    private MeFragment fragment4=null;
    private Bundle bundle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction=fragmentManager.beginTransaction();
            for(android.support.v4.app.Fragment fragment:fragmentManager.getFragments()){
                transaction.hide(fragment);
            }
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   if(!fragment1.isAdded()) {
                        fragment1 = new LeftFragment();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment1).addToBackStack(null).commit();
                        bundle=new Bundle();
                        bundle.putString("userID",UserID);
                        fragment1.setArguments(bundle);
                        transaction.add(R.id.fm_lay, fragment1).commit();
                    }
                    else{
                      //  transaction.show(fragment1).hide(fragment3).hide(fragment4).commit();
                       transaction.replace(R.id.fm_lay, fragment1).commit();
                    }
                    System.out.println("111:"+UserID);
                    return true;
                case R.id.navigation_youji:
                    //if(!fragment3.isAdded()) {
                        //fragment3 = new YoujiFragment();
                        fragment3=new PostFragment();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment1).addToBackStack(null).commit();
                        bundle=new Bundle();
                        bundle.putString("userID",UserID);
                        fragment3.setArguments(bundle);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment1).addToBackStack(null).commit();
                        transaction.replace(R.id.fm_lay, fragment3).commit();
                    //}
                    //else{
                      // transaction.show(fragment3).hide(fragment1).hide(fragment4).commit();
                    //}
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay,new YoujiFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_me:
                    //if(!fragment4.isAdded()) {
                        fragment4 = new MeFragment();
                        bundle=new Bundle();
                        bundle.putString("userID",UserID);
                        fragment4.setArguments(bundle);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment1).addToBackStack(null).commit();
                        transaction.replace(R.id.fm_lay, fragment4).commit();
                    //}
                    //else{
                      // transaction.show(fragment4).hide(fragment1).hide(fragment3).commit();
                    //}
                    System.out.println("444:"+UserID);
                    //MeFragment mefragment=new MeFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay,mefragment).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextMessage = findViewById(R.id.message);
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bundle=new Bundle();

        if(fragment1==null){
            fragment1=new LeftFragment();
        }
        if(fragment3==null){
            //fragment3=new YoujiFragment();
            fragment3=new PostFragment();
        }
        if(fragment4==null){
            fragment4=new MeFragment();
        }
        CheckLogin();
        if(isLogin){
            System.out.println(UserID+"::"+isLogin);
        }
        new BackgroundTask().execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000&&resultCode==1000){
            UserID=data.getStringExtra("userId");
            System.out.println("[id:]"+UserID);
            bundle.putString("userId",UserID);
            fragment4 = new MeFragment();
            fragment4.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment4).addToBackStack(null).commit();
        }
        else if(requestCode==1000&&resultCode==2000){
            fragment4 = new MeFragment();
            fragment4.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fm_lay, fragment4).addToBackStack(null).commit();
         }
    }
    private void CheckLogin(){
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        UserID=sp.getString("loginUserName","");
        isLogin=sp.getBoolean("isLogin",false);
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String target;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            target="http://10.0.2.2/develop/Travel/Read_youji.php";
            youji_list=new ArrayList<>();
        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream input=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(input));
                String tmp;
                StringBuilder builder=new StringBuilder();
                while ((tmp=bufferedReader.readLine())!=null){
                    builder.append(tmp+"\n");
                }
                bufferedReader.close();
                input.close();
                httpURLConnection.disconnect();
                Log.i("TAG",builder.toString().trim());
                return builder.toString().trim();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                JSONArray jsonArray=json.getJSONArray("response");
                int count=0;
                //System.out.println("Size:"+jsonArray.length());
                String userid,title,upload_date,visitor_num,userAvatar,content,place;
                while (count<jsonArray.length()){
                    JSONObject obj=jsonArray.getJSONObject(count);
                    userid=obj.getString("userid");
                    userAvatar=obj.getString("user_avatar");
                    title=obj.getString("title");
                    upload_date=obj.getString("upload_date");
                    visitor_num=obj.getString("visitor_num");
                    content=obj.getString("content");
                    place=obj.getString("place");
                    Youji youji=new Youji(userid,userAvatar,title,place,content,upload_date,visitor_num);
                    youji_list.add(youji);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            LeftFragment leftFragment=new LeftFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("youji_list", (ArrayList<? extends Parcelable>) youji_list);
            if(!UserID.equals(""))
                bundle.putString("userID",UserID);
            else
                bundle.putString("userID","");
            leftFragment.setArguments(bundle);
            transaction.add(R.id.fm_lay,leftFragment).commit();
        }
    }
}
