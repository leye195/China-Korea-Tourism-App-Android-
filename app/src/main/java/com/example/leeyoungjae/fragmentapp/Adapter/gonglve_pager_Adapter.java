package com.example.leeyoungjae.fragmentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class gonglve_pager_Adapter extends PagerAdapter{
    private List<ImageView>img;
    private Context context;
    public gonglve_pager_Adapter(List<ImageView>img,Context context){
        this.img=img;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
        //return img!=null?img.size():0;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=img.get(position%img.size());
        if(imageView.getParent()!=null){
            ((ViewPager)imageView.getParent()).removeView(imageView);
        }
        ((ViewPager)container).addView((View)imageView,0);
        return imageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(img.get(position%img.size()));
    }
}
