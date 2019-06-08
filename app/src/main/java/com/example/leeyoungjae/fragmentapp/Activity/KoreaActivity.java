package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import org.w3c.dom.Text;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KoreaActivity extends AppCompatActivity{
    private ViewPager kr_viewpager;
    private GridView kr_gridview;
    private GridView recommend_grid;
    private Button btn1,btn2,btn3,btn4;
    private SearchView searchView;

    private KoreaPagerAdapter krPagerAdapter;
    private SimpleAdapter krGridAdapter;

    private List<Map<String,Object>>dataList;
    private List<ImageView> imgList;
    private List<City> cityList;
    private int imgIds[];
    private String imgUrl[]={"http://10.0.2.2/develop/Travel/city_img/kor/首尔.jpeg","http://10.0.2.2/develop/Travel/city_img/kor/釜山.jpeg","http://10.0.2.2/develop/Travel/city_img/kor/西归浦.jpeg",
            "http://10.0.2.2/develop/Travel/city_img/kor/仁川.png","http://10.0.2.2/develop/Travel/city_img/kor/大邱.jpeg","http://10.0.2.2/develop/Travel/city_img/kor/庆州.jpeg"};
    private int preposition=0;
    private int curitem;
    private String userid="";
    private ScheduledExecutorService scheduledExecutorService;
    private Retrofit retrofit;
    private CityService cityService;

    private void initView(){
        imgList=new ArrayList<ImageView>();
        imgIds=new int[]{
                R.drawable.kr_img1,
                R.drawable.kr_img2,
                R.drawable.kr_img3,
                R.drawable.kr_img4,
                R.drawable.kr_img5
        };
        for(int i=0;i<imgIds.length;i++){
            ImageView img=new ImageView(this);
            img.setBackgroundResource(imgIds[i]);
            imgList.add(img);
        }
        int icons[]={R.drawable.g_seoul,R.drawable.g_busan,R.drawable.g_jeju,R.drawable.g_incheon,R.drawable.g_daegu,R.drawable.g_gyengju};
        String name[]=getResources().getStringArray(R.array.kr_main_cities);
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
        setContentView(R.layout.activity_korea);
        userid=getIntent().getStringExtra("userID");
        System.out.println("id in Korea "+userid);
        kr_viewpager=findViewById(R.id.kr_imgs);
        kr_gridview=findViewById(R.id.main_cities);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        initView();
        krPagerAdapter=new KoreaPagerAdapter(imgList);
        kr_viewpager.setAdapter(krPagerAdapter);
        kr_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        krGridAdapter=new SimpleAdapter(this,dataList,R.layout.citygrid,from,to);
        kr_gridview.setAdapter(krGridAdapter);
        kr_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                Toast.makeText(KoreaActivity.this,dataList.get(pos).get("text").toString(),Toast.LENGTH_SHORT).show();
                Response.Listener<String>responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json=new JSONObject(response);
                            boolean success=json.getBoolean("visit_success");
                            System.out.println(success);
                            if(success){
                                Intent intent=new Intent(KoreaActivity.this,DetailcActivity.class);
                                intent.putExtra("city",dataList.get(pos).get("text").toString());
                                intent.putExtra("from","kr");
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(intent,202);
                            }else{
                                Toast.makeText(KoreaActivity.this,"访问失败",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                CityVisitCountUpdate visitcountRequest=new CityVisitCountUpdate(dataList.get(pos).get("text").toString(),"韩国",userid,"city",responseListener);
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(visitcountRequest);
            }
        });
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(KoreaActivity.this,SearchResultActivity.class);
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
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),2,5,TimeUnit.SECONDS);
    }

    public void onIntro(View view) {
        Intent intent=new Intent(KoreaActivity.this,KRIntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onCity(View view) {
        Intent intent=new Intent(KoreaActivity.this,KRCityActivity.class);
        intent.putExtra("userID",userid);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,202);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onSight(View view) {
        Intent intent=new Intent(KoreaActivity.this,KRSightActivity.class);
        intent.putExtra("from","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void onFood(View view) {
        Intent intent=new Intent(KoreaActivity.this,KRFoodActivity.class);
        intent.putExtra("from","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void onBack(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==202){
            if(!userid.equals("")){
                getRecommendList(userid);
            }else{
                recommend_grid=findViewById(R.id.recommend_cities);
                TextView empty=findViewById(R.id.empty_text);
                empty.setVisibility(View.VISIBLE);
                recommend_grid.setEmptyView(empty);
            }
        }
    }
    public void getRecommendList(final String uid){
        String country="韩国";
        Call<CityList>call=cityService.getCityRecommend(country,uid);
        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, retrofit2.Response<CityList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        cityList=response.body().getResult();
                        recommend_grid=findViewById(R.id.recommend_cities);
                        RecommendCityAdapter adapter=new RecommendCityAdapter(KoreaActivity.this,cityList,uid);
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
            kr_viewpager.setCurrentItem(curitem);
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
    }
}
