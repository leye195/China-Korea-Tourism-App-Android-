package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Adapter.YoujiRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.mypostsAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.postsAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
import com.example.leeyoungjae.fragmentapp.R;

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

public class MyYoujiActivity extends AppCompatActivity {
    private List<Youji> mylist;
    private  List<Youji> savelist;
    private ListView list;
    //private mypostsAdapter adapter;
    private  postsAdapter adapter;
    private TextView totalcnt;

    private TextView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_youji);
        mylist=new ArrayList<>();
        savelist=new ArrayList<>();
        //list=findViewById(R.id.my_youji);
        //adapter=new mypostsAdapter(getApplicationContext(),mylist,savelist,this);
        //list.setAdapter(adapter);
        BackgroundTask task=new BackgroundTask();
        task.execute(getIntent().getStringExtra("userId"));
    }

    public void onBack(View view) {
        super.onBackPressed();
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        String target;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mylist=new ArrayList<>();
            savelist=new ArrayList<>();
        }
        @Override
        protected String doInBackground(String... strings) {
            target="http://10.0.2.2/develop/Travel/Read_youji.php?userid="+strings[0];
            System.out.println(target);
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
                bufferedReader.close();//
                input.close();//
                httpURLConnection.disconnect();//
                Log.i("TAG",builder.toString().trim());
                return builder.toString().trim();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                JSONArray jsonArray=json.getJSONArray("response");
                System.out.println(jsonArray.toString());
                int count=0;
                String post_id,user_id,title,upload_date,visitor_num,userAvatar,content,place,imgUrl;
                while (count<jsonArray.length()){
                    JSONObject obj=jsonArray.getJSONObject(count);
                    post_id=obj.getString("postid");
                    user_id=obj.getString("userid");
                    title=obj.getString("title");
                    upload_date=obj.getString("upload_date");
                    imgUrl=obj.getString("imgUrl");
                    content=obj.getString("content");
                    place=obj.getString("place");
                    visitor_num=obj.getString("visitor_num");
                    Youji youji=new Youji(post_id,user_id,title,place,content,upload_date,visitor_num,imgUrl);
                    mylist.add(youji);
                    savelist.add(youji);
                    count++;
                }
                list=findViewById(R.id.my_youji);
                totalcnt=findViewById(R.id.total_cnt);
                View empty=findViewById(R.id.empty);

                list.setEmptyView(empty);
                adapter=new postsAdapter(getApplicationContext(),mylist,savelist,totalcnt,MyYoujiActivity.this);
                list.setAdapter(adapter);
                totalcnt.setText(mylist.size()+"篇游记");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
