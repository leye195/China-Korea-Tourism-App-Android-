package com.example.leeyoungjae.fragmentapp.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.leeyoungjae.fragmentapp.Adapter.SearchResultAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.Food;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
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

public class SearchResultActivity extends AppCompatActivity {

    private String query;
    private SearchView searchView;
    private ListView result_list;
    private List<Object> obj_list;
    private SearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        query=getIntent().getStringExtra("query");
        searchView=findViewById(R.id.search_view);
        BackgroundTask task=new BackgroundTask();
        task.execute(query);
        result_list=findViewById(R.id.result_list);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                BackgroundTask t=new BackgroundTask();
                t.execute(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
    class BackgroundTask extends AsyncTask<String,Void,String>{
        String target=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            obj_list=new ArrayList<>();
        }
        @Override
        protected String doInBackground(String... strings) {
            target="http://10.0.2.2/develop/Travel/search.php?key="+strings[0];
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
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                JSONArray jsonArray=json.getJSONArray("response");
                System.out.println(jsonArray.length());
                //jsonArray.get(0);
                for(int i=0;i<3;i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    System.out.println(object);
                    int cnt=0;
                    if(i==0){
                        JSONArray cityArray=object.getJSONArray("city");
                        String city_name,country,content,imgUrl;
                        System.out.println(":"+cityArray.length());
                        while(cnt<cityArray.length()){
                            JSONObject obj=cityArray.getJSONObject(cnt);
                            city_name=obj.getString("city_name");
                            country=obj.getString("country");
                            content=obj.getString("content");
                            imgUrl=obj.getString("imgUrl");
                            City c=new City(city_name,country,content,imgUrl);
                            Boolean flag=true;
                            for(int j=0;j<obj_list.size();j++){
                                if(obj_list.get(j) instanceof City && ((City) obj_list.get(j)).getCity_name().equals(c.getCity_name())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if(flag==true)
                                obj_list.add(c);
                            cnt++;
                        }
                    }else if(i==1){
                        JSONArray sightArray=object.getJSONArray("sight");
                        String sight_name,country,imgUrl,city,address,summary,phone,ticket,traffic,open_time,time;
                        while(cnt<sightArray.length()){
                            JSONObject obj=sightArray.getJSONObject(cnt);
                            sight_name=obj.getString("sight_name");
                            country=obj.getString("country");
                            city=obj.getString("city");
                            imgUrl=obj.getString("imgUrl");
                            address=obj.getString("address");
                            summary=obj.getString("summary");
                            phone=obj.getString("phone");
                            ticket=obj.getString("ticket");
                            traffic=obj.getString("traffic");
                            open_time=obj.getString("open_time");
                            time=obj.getString("time");
                            Sight ss=new Sight(sight_name,city,country,imgUrl,time,address,summary,phone,traffic,ticket,open_time);
                            Boolean flag=true;
                            for(int j=0;j<obj_list.size();j++){
                                if(obj_list.get(j) instanceof Sight && ((Sight) obj_list.get(j)).getSight_name().equals(ss.getSight_name())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if(flag==true)
                                obj_list.add(ss);
                            cnt++;
                        }
                    }else if(i==2){
                        JSONArray foodArray=object.getJSONArray("food");
                        String food_name,country,imgUrl,city,address,cost,time,phone,summary;
                        while(cnt<foodArray.length()){
                            JSONObject obj=foodArray.getJSONObject(cnt);
                            food_name=obj.getString("name");
                            country=obj.getString("country");
                            city=obj.getString("city");
                            address=obj.getString("address");
                            imgUrl=obj.getString("imgUrl");
                            cost=obj.getString("cost");
                            time=obj.getString("time");
                            phone=obj.getString("phone");
                            summary=obj.getString("summary");
                            Food f=new Food(food_name,city,country,imgUrl,summary,time,cost,address,phone);
                            Boolean flag=true;
                            for(int j=0;j<obj_list.size();j++){
                                if(obj_list.get(j) instanceof Food && ((Food) obj_list.get(j)).getFood_name().equals(f.getFood_name())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if(flag==true)
                                obj_list.add(f);
                            cnt++;
                        }
                    }
                }
                result_list=findViewById(R.id.result_list);
                View empty=findViewById(R.id.empty);
                result_list.setEmptyView(empty);
                adapter=new SearchResultAdapter(getApplicationContext(),obj_list,SearchResultActivity.this);
                result_list.setAdapter(adapter);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
