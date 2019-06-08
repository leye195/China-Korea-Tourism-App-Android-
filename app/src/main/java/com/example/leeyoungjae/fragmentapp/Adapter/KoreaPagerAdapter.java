package com.example.leeyoungjae.fragmentapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class KoreaPagerAdapter extends PagerAdapter {
    private List<ImageView> img;
    public KoreaPagerAdapter(List<ImageView>img){
        this.img=img;
    }

    @Override
    public int getCount() {
        return img.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(img.get(position));
        return img.get(position);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(img.get(position));
    }
}
