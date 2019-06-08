package com.example.leeyoungjae.fragmentapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Activity.ChinaActivity;
import com.example.leeyoungjae.fragmentapp.Activity.KoreaActivity;
import com.example.leeyoungjae.fragmentapp.Activity.SearchResultActivity;
import com.example.leeyoungjae.fragmentapp.Activity.YoujiDetailActivity;
import com.example.leeyoungjae.fragmentapp.Adapter.YoujiRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;

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

public class LeftFragment extends Fragment {
    private String userID="";
    private List<Youji>youji_list;
    private GridView youjigrid;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private View v;
    @Override
    public void onAttach(Context context) {
        Log.i("TAG", "Fragment -->onAttach");
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("TAG","Fragment -->onCreate");
        super.onCreate(savedInstanceState);
        //System.out.println("Bun:"+getArguments());
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("TAG","Fragment -->onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //savedInstanceState=getArguments();
        //if(savedInstanceState.size()==1) {
          //  userID=savedInstanceState.getString("userID","");
            //BackgroundTask task = new BackgroundTask();
            //task.execute();
        //}
        //else{
            /*youji_list=savedInstanceState.getParcelableArrayList("youji_list");
            recyclerView=getView().findViewById(R.id.youji_rec);
            recyclerView.setHasFixedSize(false);
            manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
            YoujiRecyclerAdapter youjiRecyclerAdapter=new YoujiRecyclerAdapter(getActivity(),youji_list);
            youjiRecyclerAdapter.setItemClickListener(new YoujiRecyclerAdapter.youjiItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    Toast.makeText(getActivity(),position+":",Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(youjiRecyclerAdapter);*/
        }
       // new BackgroundTask().execute();
    //}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("TAG","Fragment -->onCreateView");
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_left, container, false);
        Bundle bundle=new Bundle();
        bundle=getArguments();
        if(bundle!=null) {
            userID = bundle.getString("userID");
        }
        List<Map<String,Object>>mp=new ArrayList<>();
        final String[]n_list=new String[]{"韩国","中国"};
        final int[]h_list=new int[]{R.drawable.kor,R.drawable.ch};
        for(int i=0;i<h_list.length;i++){
            Map<String,Object>items=new HashMap<>();
            items.put("country",h_list[i]);
            items.put("country_name",n_list[i]);
            mp.add(items);
        }
        GridView gridView=root.findViewById(R.id.homegrid);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),mp,R.layout.homegrid_item,new String[]{"country","country_name"},new int[]{R.id.country_select,R.id.country_name});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent=new Intent(getContext(),KoreaActivity.class);
                        intent.putExtra("userID",userID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(getContext(),ChinaActivity.class);
                        intent.putExtra("userID",userID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        break;
                }
            }
        });
        SearchView searchView=root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(getContext(),SearchResultActivity.class);
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
        return root;
    }
    @Override
    public void onStart() {
        Log.i("TAG","Fragment -->onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("TAG","Fragment -->onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("TAG","Fragment -->onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("TAG","Fragment -->onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("TAG","Fragment -->onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("TAG","Fragment -->onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("TAG","Fragment -->onDetach");
        super.onDetach();
    }
    public void youjiClick(View v){
        int position=recyclerView.getChildAdapterPosition(v);
        Toast.makeText(getActivity(),position+"::",Toast.LENGTH_SHORT).show();
    }
    /*
    class BackgroundTask extends AsyncTask<Void,Void,String>{
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                JSONArray jsonArray=json.getJSONArray("response");
                int count=0;
                System.out.println("Size::"+jsonArray.length());
                String userid,title,upload_date,visitor_num,userAvatar,content,place,imgUrl,postid;
                while (count<jsonArray.length()){
                    JSONObject obj=jsonArray.getJSONObject(count);
                    userid=obj.getString("userid");
                    userAvatar=obj.getString("user_avatar");
                    title=obj.getString("title");
                    upload_date=obj.getString("upload_date");
                    visitor_num=obj.getString("visitor_num");
                    content=obj.getString("content");
                    place=obj.getString("place");
                    imgUrl=obj.getString("imgUrl");
                    postid=obj.getString("postid");
                    Youji youji;
                    if(imgUrl!=null) {
                        youji = new Youji(postid,userid, userAvatar, title, place, content, upload_date, visitor_num, imgUrl);
                    }
                    else {
                        youji = new Youji(postid,userid, userAvatar, title, place, content, upload_date, visitor_num,"");
                    }
                    youji_list.add(youji);
                    count++;
                }
                recyclerView=getView().findViewById(R.id.youji_rec);
                recyclerView.setHasFixedSize(false);

                manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

                YoujiRecyclerAdapter youjiRecyclerAdapter=new YoujiRecyclerAdapter(getActivity(),youji_list);
                youjiRecyclerAdapter.setItemClickListener(new YoujiRecyclerAdapter.youjiItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast.makeText(getActivity(),position+":",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),YoujiDetailActivity.class);
                        intent.putExtra("postid",youji_list.get(position).getPostid());
                        intent.putExtra("title",youji_list.get(position).getTitle());
                        intent.putExtra("content",youji_list.get(position).getContent());
                        intent.putExtra("imgUrl",youji_list.get(position).getImgUrl());
                        intent.putExtra("userid",userID);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(youjiRecyclerAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
