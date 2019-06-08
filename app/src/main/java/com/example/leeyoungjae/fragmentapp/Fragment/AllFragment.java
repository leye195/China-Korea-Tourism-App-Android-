package com.example.leeyoungjae.fragmentapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Activity.WriteYoujiActivity;
import com.example.leeyoungjae.fragmentapp.Activity.YoujiDetailActivity;
import com.example.leeyoungjae.fragmentapp.Adapter.YoujiRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.PostList;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.PostVisitCountUpdate;
import com.example.leeyoungjae.fragmentapp.Service.PostService;

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

public class AllFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private TextView empty;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Youji> youji_list;
    private String userID;

    private Retrofit retrofit;
    private PostService postService;
    public AllFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_all, container, false);
        userID=getArguments().getString("userID");
        System.out.println("UserID in Post: "+userID);
        youji_list=new ArrayList<>();
        recyclerView=v.findViewById(R.id.youji_list);
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/develop/Travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postService=retrofit.create(PostService.class);

        swipeRefreshLayout=v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllPost();
                        System.out.println("User:"+userID);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2500);
            }
        });
        FloatingActionButton add_youji=v.findViewById(R.id.add_youji);
        add_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.equals("")){
                    Toast.makeText(getContext(),"请先登入",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), WriteYoujiActivity.class);
                    intent.putExtra("userID",userID);
                    startActivityForResult(intent,202);
                }
            }
        });
        getAllPost();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==202){
            getAllPost();
        }
    }
    public void getAllPost(){
        Call<PostList> call=postService.getAllPostList();
        youji_list=new ArrayList<>();
        call.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, retrofit2.Response<PostList> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(response.code()==200){
                        youji_list=response.body().getResult();
                        recyclerView=getView().findViewById(R.id.youji_list);
                        empty=getView().findViewById(R.id.empty);
                        if(youji_list.size()==0){
                            recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }
                        else{
                            recyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                            recyclerView.setHasFixedSize(false);
                            manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                            YoujiRecyclerAdapter youjiRecyclerAdapter=new YoujiRecyclerAdapter(getActivity(),youji_list);
                            youjiRecyclerAdapter.setItemClickListener(new YoujiRecyclerAdapter.youjiItemClickListener() {
                                @Override
                                public void onClick(View v, final int position) {
                                    if(!userID.equals("")){
                                        Response.Listener<String>responseListener= new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try{
                                                    JSONObject json=new JSONObject(response);
                                                    boolean success=json.getBoolean("visit_success");
                                                    System.out.println(success);
                                                    if(success){
                                                        Intent intent=new Intent(getActivity(),YoujiDetailActivity.class);
                                                        intent.putExtra("title",youji_list.get(position).getTitle());
                                                        intent.putExtra("content",youji_list.get(position).getContent());
                                                        intent.putExtra("imgUrl",youji_list.get(position).getImgUrl());
                                                        intent.putExtra("userid",userID);
                                                        intent.putExtra("poster",youji_list.get(position).getUserID());
                                                        intent.putExtra("postid",youji_list.get(position).getPostid());
                                                        intent.putExtra("sub_imgs",youji_list.get(position).getSub_imgUrl());
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivityForResult(intent,202);
                                                    }else{
                                                        Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
                                                    }
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        PostVisitCountUpdate visitcountRequest=new PostVisitCountUpdate(userID,youji_list.get(position).getPostid().toString(),"post",responseListener);
                                        RequestQueue queue=Volley.newRequestQueue(getContext());
                                        queue.add(visitcountRequest);
                                    }else{
                                        Toast.makeText(getActivity(),position+":",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getActivity(),YoujiDetailActivity.class);
                                        intent.putExtra("title",youji_list.get(position).getTitle());
                                        intent.putExtra("content",youji_list.get(position).getContent());
                                        intent.putExtra("imgUrl",youji_list.get(position).getImgUrl());
                                        intent.putExtra("userid",userID);
                                        intent.putExtra("poster",youji_list.get(position).getUserID());
                                        intent.putExtra("postid",youji_list.get(position).getPostid());
                                        intent.putExtra("sub_imgs",youji_list.get(position).getSub_imgUrl());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(youjiRecyclerAdapter);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
            }
        });
    }
}
