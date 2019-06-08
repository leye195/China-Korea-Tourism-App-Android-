package com.example.leeyoungjae.fragmentapp.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leeyoungjae.fragmentapp.Bean.CityList;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Service.CityService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailcActivity extends AppCompatActivity {
    private String name;
    private String content;
    private TextView cityname;
    private TextView about;
    private ImageView img;

    private Retrofit retrofit;
    private CityService cityService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailc);
        name=getIntent().getStringExtra("city");
        cityname=findViewById(R.id.name);
        cityname.setText(name);
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cityService=retrofit.create(CityService.class);
        if(getIntent().getStringExtra("from").equals("ch")){
            getCity("中国",name);
        }else if(getIntent().getStringExtra("from").equals("kr")){
            getCity("韩国",name);
        }
    }
    public void getCity(String country,String city){
        Call<CityList>call=cityService.ReadCityList(country,city);
        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        about=findViewById(R.id.intro);
                        about.setText(response.body().getResult().get(0).getContent().toString());
                        img=findViewById(R.id.img);
                        Picasso.with(getApplicationContext())
                                .load(response.body().getResult().get(0).getImgurl().toString())
                                .into(img);
                    }
                }
            }
            @Override
            public void onFailure(Call<CityList> call, Throwable t) {

            }
        });
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
    class BackgroundTask extends AsyncTask<String,Void,String>{
        String target;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            target="http://10.0.2.2/develop/Travel/Read_city_list.php?country="+strings[0]+"&city="+strings[1];
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                String country,content,imgUrl;
                JSONObject json=new JSONObject(s);
                JSONArray jsonArray=json.getJSONArray("response");
                JSONObject obj=jsonArray.getJSONObject(0);
                country=obj.getString("country");
                content=obj.getString("content");
                imgUrl=obj.getString("imgUrl");
                about=findViewById(R.id.intro);
                about.setText(content);
                img=findViewById(R.id.img);
                Picasso.with(getApplicationContext())
                        .load(imgUrl)
                        .into(img);
                //GetImageByUrl getImageByUrl=new GetImageByUrl();
                //getImageByUrl.setImage(img,imgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
