package com.example.leeyoungjae.fragmentapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Activity.WriteYoujiActivity;
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
import java.util.List;

public class YoujiFragment extends Fragment implements View.OnClickListener {

    private String userID="";
    public int cnt=0;
    private String postid;

    private Button allbtn;
    private Button korbtn;
    private Button chbtn;
    private Button recombtn;
    private ProgressBar progressBar;
    private FloatingActionButton add_youji;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private SearchView searchView;

    private List<Youji> youji_list;
    /* 自定义Handler信息代码，用以作为标识事件处理 */
    protected static final int GUI_STOP_NOTIFIER = 0x108;
    protected static final int GUI_THREADING_NOTIFIER = 0x10;

    public YoujiFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_youji, container, false);
        allbtn=v.findViewById(R.id.onAll);
        allbtn.setOnClickListener(this);
        korbtn=v.findViewById(R.id.onKorea);
        korbtn.setOnClickListener(this);
        recombtn=v.findViewById(R.id.onRecommend);
        recombtn.setOnClickListener(this);
        chbtn=v.findViewById(R.id.onChina);
        chbtn.setOnClickListener(this);
        add_youji=v.findViewById(R.id.add_youji);
        add_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.equals("")){
                    Toast.makeText(getContext(),"请先登入",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), WriteYoujiActivity.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
            }
        });
        progressBar=v.findViewById(R.id.progress_b);
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
        swipeRefreshLayout=v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BackgroundTask task = new BackgroundTask();
                        task.execute("no");
                        System.out.println("User:"+userID);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2500);
            }
        });
        searchView.findViewById(R.id.search_view);
        //System.out.println("progess::"+progressBar.getProgress());
        savedInstanceState=getArguments();
        System.out.println(savedInstanceState);
        if(savedInstanceState.size()==1) {
            userID=savedInstanceState.getString("userID","");
            YoujiFragment.BackgroundTask task = new YoujiFragment.BackgroundTask();
            task.execute("no");
        }
        else{
            youji_list=savedInstanceState.getParcelableArrayList("youji_list");
            recyclerView=getView().findViewById(R.id.youji_list);
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
            recyclerView.setAdapter(youjiRecyclerAdapter);
        }
        //BackgroundTask task=new BackgroundTask();
        //task.execute("All");
        return v;
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
    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.onAll:
                Toast.makeText(getContext(),"All",Toast.LENGTH_SHORT).show();
                YoujiFragment.BackgroundTask task1 = new YoujiFragment.BackgroundTask();
                task1.execute("All");
                break;
            case R.id.onKorea:
                Toast.makeText(getContext(),"KOR",Toast.LENGTH_SHORT).show();
                YoujiFragment.BackgroundTask task2 = new YoujiFragment.BackgroundTask();
                task2.execute("kr");
                break;
            case R.id.onChina:
                Toast.makeText(getContext(),"CHI",Toast.LENGTH_SHORT).show();
                YoujiFragment.BackgroundTask task3 = new YoujiFragment.BackgroundTask();
                task3.execute("ch");
                break;
            case R.id.onRecommend:
                Toast.makeText(getContext(),"Recommend Youji",Toast.LENGTH_SHORT).show();
                YoujiFragment.BackgroundTask task4 = new YoujiFragment.BackgroundTask();
                task4.execute("recommendation");
                break;
            default:
                break;
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        String target;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            youji_list=new ArrayList<>();
        }
        @Override
        protected String doInBackground(String... strings) {
            if(strings[0].equals("All")||strings[0].equals("no")){
                target="http://10.0.2.2/develop/Travel/Read_youji.php";
                System.out.println(target);
            }
            else if(strings[0].equals("kr")){
                target="http://10.0.2.2/develop/Travel/Read_kr_youji.php";
            }else if(strings[0].equals("ch")){
                target="http://10.0.2.2/develop/Travel/Read_ch_youji.php";
            }else if(strings[0].equals("recommendation")){
                //target="http://10.0.2.2/develop/Travel/recommendation_youji.php";
            }
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
                String postid,userid,title,upload_date,visitor_num,userAvatar,content,place,imgUrl;
                while (count<jsonArray.length()){
                    JSONObject obj=jsonArray.getJSONObject(count);
                    postid=obj.getString("postid");
                    userid=obj.getString("userid");
                    //userAvatar=obj.getString("user_avatar");
                    title=obj.getString("title");
                    upload_date=obj.getString("upload_date");
                    visitor_num=obj.getString("visitor_num");
                    content=obj.getString("content");
                    place=obj.getString("place");
                    imgUrl=obj.getString("imgUrl");
                    Youji youji=new Youji(postid,userid,title,place,content,upload_date,visitor_num,imgUrl);
                    youji_list.add(youji);
                    count++;
                }
                recyclerView=getView().findViewById(R.id.youji_list);
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
                        intent.putExtra("title",youji_list.get(position).getTitle());
                        intent.putExtra("content",youji_list.get(position).getContent());
                        intent.putExtra("imgUrl",youji_list.get(position).getImgUrl());
                        intent.putExtra("userid",userID);
                        intent.putExtra("postid",youji_list.get(position).getPostid());
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(youjiRecyclerAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
