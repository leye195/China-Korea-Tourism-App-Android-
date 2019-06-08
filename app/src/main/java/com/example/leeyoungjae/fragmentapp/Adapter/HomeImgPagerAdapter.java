package com.example.leeyoungjae.fragmentapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class HomeImgPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private  Context context;
    private List<ImageView>list;
    private int cur,pos;
    public  HomeImgPagerAdapter(Context context, List<ImageView>list){
        this.context=context;
        this.list=list;
        this.cur=0;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(list.get(position));
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        cur=i%list.size();
        //if(cur)
        System.out.println("cur:"+i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
