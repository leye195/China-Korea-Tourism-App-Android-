package com.example.leeyoungjae.fragmentapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.leeyoungjae.fragmentapp.Fragment.AllFragment;
import com.example.leeyoungjae.fragmentapp.Fragment.ChinaFragment;
import com.example.leeyoungjae.fragmentapp.Fragment.KoreaFragment;
import com.example.leeyoungjae.fragmentapp.Fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_COUNT=4;
    private String[] tableTitle=new String[]{"全部","推荐","韩国","中国"};
    private Context mContext;
    private List<Fragment>fragments;
    public PagerAdapter(FragmentManager fm,List<Fragment>fragments){
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
    }
}
