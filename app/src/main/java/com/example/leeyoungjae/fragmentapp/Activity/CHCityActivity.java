package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Adapter.CityRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.CityList;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.CityVisitCountUpdate;
import com.example.leeyoungjae.fragmentapp.Service.CityService;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CHCityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private SearchView mSearchView;
    private ProgressBar progressBar;

    private List<City> city_list;
    private List<City> savelist;
    private String userID;
    public int cnt=0;
    protected static final int GUI_STOP_NOTIFIER = 0x108;
    protected static final int GUI_THREADING_NOTIFIER = 0x10;

    private Retrofit retrofit;
    private CityService cityService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chcity);
        city_list=new ArrayList<>();
        savelist=new ArrayList<>();
        recyclerView=findViewById(R.id.city_list);
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cityService=retrofit.create(CityService.class);
        mSearchView=findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(CHCityActivity.this,SearchResultActivity.class);
                intent.putExtra("query",query);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        getCityList("");
        progressBar=findViewById(R.id.progress_b);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try{
                        cnt=(i+1)*20;
                        Thread.sleep(700);
                        if(i==4){
                            Message m=new Message();
                            m.what=GUI_STOP_NOTIFIER;
                            myMessageHandler.sendMessage(m);
                            break;
                        }
                        else{
                            Message m=new Message();
                            m.what=GUI_THREADING_NOTIFIER;
                            myMessageHandler.sendMessage(m);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void getCityList(String city){
        String country="中国";
        Call<CityList> call=cityService.ReadCityList(country,city);
        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, retrofit2.Response<CityList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        city_list=response.body().getResult();
                        recyclerView.setHasFixedSize(true);
                        manager=new LinearLayoutManager(CHCityActivity.this,LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(CHCityActivity.this,DividerItemDecoration.VERTICAL));
                        CityRecyclerAdapter cityRecyclerAdapter=new CityRecyclerAdapter(CHCityActivity.this,city_list,savelist);
                        cityRecyclerAdapter.setItemClickListener(new CityRecyclerAdapter.cityItemClickListener() {
                            @Override
                            public void onClick(View v, final int position) {
                                String userID=getIntent().getStringExtra("userID");
                                if(!userID.equals("")){
                                    Response.Listener<String>responseListener=new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject json=new JSONObject(response);
                                                boolean success=json.getBoolean("visit_success");
                                                System.out.println(success);
                                                if(success){
                                                    Intent intent=new Intent(CHCityActivity.this,DetailcActivity.class);
                                                    intent.putExtra("city",city_list.get(position).getCity_name());
                                                    intent.putExtra("from","ch");
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(CHCityActivity.this,"访问失败",Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    CityVisitCountUpdate visitcountRequest=new CityVisitCountUpdate(city_list.get(position).getCity_name(),city_list.get(position).getCountry(),userID,"city",responseListener);
                                    RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                    queue.add(visitcountRequest);
                                }else{
                                    Intent intent=new Intent(CHCityActivity.this,DetailcActivity.class);
                                    intent.putExtra("city",city_list.get(position).getCity_name());
                                    intent.putExtra("from","ch");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        });
                        recyclerView.setAdapter(cityRecyclerAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<CityList> call, Throwable t) {

            }
        });
    }
    Handler myMessageHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case GUI_STOP_NOTIFIER:
                    progressBar.setVisibility(View.GONE);
                    Thread.currentThread().interrupt();
                    break;
                case GUI_THREADING_NOTIFIER:
                    if(!Thread.currentThread().isInterrupted()){
                        progressBar.setProgress(cnt);
                    }
                    break;
            }
        }
    };

    public void OnBack(View view) {
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
