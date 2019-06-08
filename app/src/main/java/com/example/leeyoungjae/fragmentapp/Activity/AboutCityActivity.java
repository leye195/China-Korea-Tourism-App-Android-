package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Adapter.CityRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.Food;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
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

public class AboutCityActivity extends AppCompatActivity {
    private TextView name;
    private ImageView img;
    private SearchView searchView;

    private String name_str;
    private String img_str;
    private double lgn,lat;

    private List<Sight> s_list;
    private List<Food> f_list;
    private String flag="";
    private String from="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_city);

        from=getIntent().getStringExtra("from");
        name_str=getIntent().getStringExtra("city");
        img_str=getIntent().getStringExtra("imgUrl");
        lgn=getIntent().getDoubleExtra("lgn",0);
        lat=getIntent().getDoubleExtra("lat",0);
        name=findViewById(R.id.name);
        img=findViewById(R.id.img);
        searchView=findViewById(R.id.search_view);
        Toast.makeText(this,name_str,Toast.LENGTH_SHORT).show();
        name.setText(name_str);
        GetImageByUrl getImageByUrl=new GetImageByUrl();
        getImageByUrl.setImage(img,img_str);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(AboutCityActivity.this,SearchResultActivity.class);
                intent.putExtra("query",s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    public void onMap(View view) {
        Toast.makeText(AboutCityActivity.this,"Map",Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(AboutCityActivity.this,MapActivity.class);
        if(getIntent().getStringExtra("from").equals("ch")) {
            /*Intent intent = new Intent(AboutCityActivity.this, MapActivity.class);
            intent.putExtra("from", "ch");
            intent.putExtra("city", getIntent().getStringExtra("city"));
            intent.putExtra("lgn", lgn);
            intent.putExtra("lat", lat);
            startActivity(intent);*/
        }
        else if(getIntent().getStringExtra("from").equals("kr")){
            /*Intent intent = new Intent(AboutCityActivity.this, MapActivity.class);
            intent.putExtra("from", "kr");
            intent.putExtra("city", getIntent().getStringExtra("city"));
            intent.putExtra("lgn", lgn);
            intent.putExtra("lat", lat);
            startActivity(intent);*/
        }
    }

    public void onFood(View view) {
        Toast.makeText(AboutCityActivity.this,"Food",Toast.LENGTH_SHORT).show();
        Intent intent;
        if(from.equals("kr")){
            intent=new Intent(AboutCityActivity.this,KRFoodActivity.class);
            intent.putExtra("from",getIntent().getStringExtra("city"));
            startActivity(intent);
        }else if(from.equals("ch")){
            intent=new Intent(AboutCityActivity.this,CHFoodActivity.class);
            intent.putExtra("from",getIntent().getStringExtra("city"));
            startActivity(intent);
        }

    }

    public void onSight(View view) {
        Toast.makeText(AboutCityActivity.this,"Sight",Toast.LENGTH_SHORT).show();
        Intent intent;
        if(from.equals("kr")){
            intent=new Intent(AboutCityActivity.this,KRSightActivity.class);
            intent.putExtra("from",getIntent().getStringExtra("city"));
            startActivity(intent);
        }else if(from.equals("ch")){
            intent=new Intent(AboutCityActivity.this,CHSightActivity.class);
            intent.putExtra("from",getIntent().getStringExtra("city"));
            startActivity(intent);
        }
    }

    public void onCity(View view) {
        Toast.makeText(AboutCityActivity.this,"About City",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(AboutCityActivity.this,DetailcActivity.class);
        intent.putExtra("city",name_str);
        intent.putExtra("from",from);
        startActivity(intent);
    }

    public void onBack(View view) {
        super.onBackPressed();
    }
}
