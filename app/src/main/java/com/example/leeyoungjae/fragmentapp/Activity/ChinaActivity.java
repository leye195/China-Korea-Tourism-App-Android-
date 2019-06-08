package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Adapter.ChinaPagerAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.KoreaPagerAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.RecommendCityAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.CityList;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.CityVisitCountUpdate;
import com.example.leeyoungjae.fragmentapp.Service.CityService;
import com.example.leeyoungjae.fragmentapp.View.RecommendGridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChinaActivity extends AppCompatActivity {
    private ViewPager ch_viewpager;
    private ChinaPagerAdapter chPagerAdapter;
    private GridView ch_gridview;
    private GridView recommend_grid;
    private SimpleAdapter chGridAdapter;
    private Button btn1,btn2,btn3,btn4;
    private SearchView searchView;

    private List<City>cityList;
    private List<Map<String,Object>>dataList;
    private List<ImageView> imgList;
    private int imgIds[];
    private String imgUrl[]={"http://10.0.2.2/develop/Travel/city_img/ch/北京.jpeg","http://10.0.2.2/develop/Travel/city_img/ch/上海.jpeg","http://10.0.2.2/develop/Travel/city_img/ch/香港.jpeg",
            "http://10.0.2.2/develop/Travel/city_img/ch/重庆.png","http://10.0.2.2/develop/Travel/city_img/ch/厦门.jpeg","http://10.0.2.2/develop/Travel/city_img/ch/西安.jpeg"};
    private int preposition=0;
    private int curitem;
    private String userid="";

    private ScheduledExecutorService scheduledExecutorService;
    private Retrofit retrofit;
    private CityService cityService;

    private void initView(){
        cityList=new ArrayList<>();
        imgList=new ArrayList<>();
        imgIds=new int[]{
                R.drawable.ch_img1,
                R.drawable.ch_img2,
                R.drawable.ch_img3,
                R.drawable.ch_img4,
                R.drawable.ch_img5
        };
        for(int i=0;i<imgIds.length;i++){
            ImageView img=new ImageView(this);
            img.setBackgroundResource(imgIds[i]);
            imgList.add(img);
        }
        int icons[]={R.drawable.g_beijing,R.drawable.g_shanghai,R.drawable.g_hongkong,R.drawable.g_chongqing,R.drawable.g_xiamen,R.drawable.g_xian};
        String name[]=getResources().getStringArray(R.array.ch_main_cities);
        dataList=new ArrayList<Map<String, Object>>();
        for(int i=0;i<icons.length;i++){
            Map<String,Object>mp=new HashMap<String,Object>();
            mp.put("img",icons[i]);
            mp.put("text",name[i]);
            dataList.add(mp);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_china);
        userid=getIntent().getStringExtra("userID");
        ch_viewpager=findViewById(R.id.ch_imgs);
        ch_gridview=findViewById(R.id.main_cities);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        initView();
        chPagerAdapter=new ChinaPagerAdapter(imgList);
        ch_viewpager.setAdapter(chPagerAdapter);
        ch_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                preposition=i;
                curitem=i;
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        String[] from={"img","text"};
        int[]to={R.id.city_img,R.id.city_text};
        chGridAdapter=new SimpleAdapter(this,dataList,R.layout.citygrid,from,to);
        ch_gridview.setAdapter(chGridAdapter);
        ch_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos=position;
                Response.Listener<String>responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json=new JSONObject(response);
                            boolean success=json.getBoolean("visit_success");
                            System.out.println(success);
                            if(success){
                                Intent intent=new Intent(ChinaActivity.this,DetailcActivity.class);
                                intent.putExtra("city",dataList.get(pos).get("text").toString());
                                intent.putExtra("from","ch");
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(intent,202);
                            }else{
                                Toast.makeText(ChinaActivity.this,"访问失败",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                CityVisitCountUpdate visitcountRequest=new CityVisitCountUpdate(dataList.get(pos).get("text").toString(),"中国",userid,"city",responseListener);
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(visitcountRequest);
            }
        });
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(ChinaActivity.this,SearchResultActivity.class);
                intent.putExtra("query",s);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cityService=retrofit.create(CityService.class);
        if(!userid.equals("")){
            getRecommendList(userid);
        }else{
            recommend_grid=findViewById(R.id.recommend_cities);
            TextView empty=findViewById(R.id.empty_text);
            empty.setVisibility(View.VISIBLE);
            recommend_grid.setEmptyView(empty);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ChinaActivity.ViewPagerTask(),2,5,TimeUnit.SECONDS);
    }
    public void onIntro(View view) {
        Intent intent=new Intent(ChinaActivity.this,CHIntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onCity(View view) {
        Intent intent=new Intent(ChinaActivity.this,CHCityActivity.class);
        intent.putExtra("userID",userid);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,202);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onSight(View view) {
        Intent intent=new Intent(ChinaActivity.this,CHSightActivity.class);
        intent.putExtra("from","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onFood(View view) {
        Intent intent=new Intent(ChinaActivity.this,CHFoodActivity.class);
        intent.putExtra("from","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
    public void getRecommendList(final String uid){
        String country="中国";
        Call<CityList> call=cityService.getCityRecommend(country,uid);
        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, retrofit2.Response<CityList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        cityList=response.body().getResult();
                        recommend_grid=findViewById(R.id.recommend_cities);
                        RecommendCityAdapter adapter=new RecommendCityAdapter(ChinaActivity.this,cityList,uid);
                        TextView empty=findViewById(R.id.empty_text);
                        setGrid(recommend_grid);
                        recommend_grid.setAdapter(adapter);
                        recommend_grid.setEmptyView(empty);
                    }
                }
            }
            @Override
            public void onFailure(Call<CityList> call, Throwable t) {

            }
        });
    }
    public void setGrid(GridView gridView){
        int size = cityList.size();
        int length = 120;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(10); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
    }
    private class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            curitem=(curitem+1)%imgIds.length;
            handler.obtainMessage().sendToTarget();
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            ch_viewpager.setCurrentItem(curitem);
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
    }
}
