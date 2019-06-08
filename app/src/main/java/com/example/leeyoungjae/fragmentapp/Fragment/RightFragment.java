package com.example.leeyoungjae.fragmentapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.leeyoungjae.fragmentapp.Adapter.gonglve_pager_Adapter;
import com.example.leeyoungjae.fragmentapp.R;

import java.util.ArrayList;
import java.util.List;


public class RightFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private ViewPager gonglv_pager;
    private List<ImageView>list;
    private int previousPosition = 0;//前一个被选中的position
    private boolean isStop = false;//线程是否停止
    private boolean flag=false;
    public void init(){
        gonglv_pager=getView().findViewById(R.id.gonglve_pager);
        initView();
    }
    public void initView(){
        initDate();
        gonglve_pager_Adapter adapter=new gonglve_pager_Adapter(list, getActivity());
        gonglv_pager.setAdapter(adapter);
        gonglv_pager.addOnPageChangeListener(this);
        setFirstLocation();
    }
    public void initDate(){
        list=new ArrayList<>();
        int[]imgs=getImg();
        for(int i=0;i<imgs.length;i++){
            ImageView iv=new ImageView(getActivity());
            iv.setBackgroundResource(imgs[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(iv);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // ButterKnife.
        System.out.println(getArguments());
        init();
        autoPlayView();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_right,container,false);
        Button btn1=v.findViewById(R.id.city_btn);
        Button btn2=v.findViewById(R.id.food_tr_btn);
        Button btn3=v.findViewById(R.id.info_btn);

        final ToggleButton toggleButton=v.findViewById(R.id.select_country);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked()){
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.china_flag));
                    flag=true;
                }
                else{
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.kr_flag));
                    flag=false;
                }
            }
        });
        return v;
        //return inflater.inflate(R.layout.fragment_right, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private int[]getImg(){
        return new int[]{R.drawable.gyeong,R.drawable.china,R.drawable.gyeong,R.drawable.china};
    }
    private void setFirstLocation(){
        int m=(Integer.MAX_VALUE/2)%list.size();
        int currentPosition=Integer.MAX_VALUE/2-m;
        gonglv_pager.setCurrentItem(currentPosition);
    }
    private void autoPlayView() {
        //自动播放图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gonglv_pager.setCurrentItem(gonglv_pager.getCurrentItem() + 1);
                        }
                    });
                    SystemClock.sleep(5500);
                }
            }
        }).start();
    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        int newPosition=i%list.size();
        previousPosition=newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop=true;
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.city_btn:
                if(flag==true){
                    Toast.makeText(getContext(),"Chcity",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Krcity",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.food_tr_btn:
                if(flag==true){
                    Toast.makeText(getContext(),"Chfoodtr",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Krfoodtr",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.info_btn:
                if(flag==true){
                    Toast.makeText(getContext(),"Chcity",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Krinfo",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
