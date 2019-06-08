package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Adapter.SightRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.SightList;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
import com.example.leeyoungjae.fragmentapp.Service.SightService;

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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KRSightActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private Spinner spinner;
    private ProgressBar progressBar;
    private SearchView searchView;
    private List<Sight> sight_list;
    private String from;
    public int cnt=0;
    protected static final int GUI_STOP_NOTIFIER = 0x108;
    protected static final int GUI_THREADING_NOTIFIER = 0x10;

    private Retrofit retrofit;
    private SightService sightService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krsight);
        from=getIntent().getStringExtra("from");
        spinner = findViewById(R.id.select_city);
        recyclerView = findViewById(R.id.sight_list);
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sightService=retrofit.create(SightService.class);
        if(from.equals("1")) {
            Resources res = getResources();
            String[] city = res.getStringArray(R.array.kr_cities);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = spinner.getItemAtPosition(position).toString();
                    Toast.makeText(KRSightActivity.this, text, Toast.LENGTH_SHORT).show();
                    getSightList(text);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        else{
            TextView head_name=findViewById(R.id.head_text);
            head_name.setText(from+"景点");
            spinner.setVisibility(View.GONE);
            getSightList(from);
        }
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(KRSightActivity.this,SearchResultActivity.class);
                intent.putExtra("query",s);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
    public void onBack(View view) {
        super.onBackPressed();
    }
    public void getSightList(String city){
        Call<SightList>call=sightService.getKrSightList(city);
        call.enqueue(new Callback<SightList>() {
            @Override
            public void onResponse(Call<SightList> call, Response<SightList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        sight_list=response.body().getResult();
                        if(sight_list.size()>0) {
                            recyclerView.setHasFixedSize(false);
                            manager = new LinearLayoutManager(KRSightActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.addItemDecoration(new DividerItemDecoration(KRSightActivity.this, DividerItemDecoration.VERTICAL));
                            SightRecyclerAdapter sightRecyclerAdapter = new SightRecyclerAdapter(KRSightActivity.this, sight_list);
                            sightRecyclerAdapter.setItemClickListener(new SightRecyclerAdapter.sightItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Toast.makeText(KRSightActivity.this, position + ":", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(KRSightActivity.this,DetailsActivity.class);
                                    intent.putExtra("from","sight");
                                    intent.putExtra("sight_name",sight_list.get(position).getSight_name());
                                    intent.putExtra("city",sight_list.get(position).getCity());
                                    intent.putExtra("country","韩国");
                                    intent.putExtra("imgUrl",sight_list.get(position).getImgUrl());
                                    intent.putExtra("address",sight_list.get(position).getAddress());
                                    intent.putExtra("traffic",sight_list.get(position).getTraffic());
                                    intent.putExtra("time",sight_list.get(position).getTime());
                                    intent.putExtra("ticket",sight_list.get(position).getTicket());
                                    intent.putExtra("open_time",sight_list.get(position).getOpen_time());
                                    intent.putExtra("summary",sight_list.get(position).getSummary());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(sightRecyclerAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else
                            recyclerView.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<SightList> call, Throwable t) {
            }
        });
    }
}
