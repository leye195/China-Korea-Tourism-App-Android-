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
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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

import com.example.leeyoungjae.fragmentapp.Activity.SearchResultActivity;
import com.example.leeyoungjae.fragmentapp.Activity.WriteYoujiActivity;
import com.example.leeyoungjae.fragmentapp.Activity.YoujiDetailActivity;
import com.example.leeyoungjae.fragmentapp.Adapter.PagerAdapter;
import com.example.leeyoungjae.fragmentapp.Adapter.YoujiRecyclerAdapter;
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
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

public class PostFragment extends Fragment{

    public int cnt=0;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private List<Youji> youji_list;
    private String titles[]={"全部","推荐","韩国","中国"};
    private ArrayList<Fragment>fragments;
    /* 自定义Handler信息代码，用以作为标识事件处理 */
    protected static final int GUI_STOP_NOTIFIER = 0x108;
    protected static final int GUI_THREADING_NOTIFIER = 0x10;
    private AllFragment allFragment;
    private RecommendFragment recommendFragment;
    private KoreaFragment krFragment;
    private ChinaFragment chFragment;
    private PagerAdapter adapter;
    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_youji2, container, false);
        searchView=v.findViewById(R.id.search_view);
        tabLayout=v.findViewById(R.id.tablayout);
        viewPager=v.findViewById(R.id.viewpager);

        Bundle bundle= new Bundle();
        bundle=getArguments();
        if(bundle!=null){
          initFragment(bundle.getString("userID"));
          fragments=new ArrayList<>();
          fragments.add(allFragment);
          fragments.add(recommendFragment);
          fragments.add(krFragment);
          fragments.add(chFragment);

          adapter=new PagerAdapter(getChildFragmentManager(),fragments);
            //如果是fragment嵌套fragment，那么就需要利用getChildFragmentManager(),getChildFragmentManager()是在fragment 里面子容器的碎片管理。
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(1);
        }
        //initFragment(savedInstanceState.getString("userID"));
        for(int i=0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(titles[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                viewPager.setCurrentItem(pos);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final TabLayout.TabLayoutOnPageChangeListener listener=new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.addOnPageChangeListener(listener);

        tabLayout.setupWithViewPager(viewPager);
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

        return v;
    }
    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initFragment(String userid){
        Bundle bundle=new Bundle();
        bundle.putString("userID",userid);
        allFragment=(AllFragment)Fragment.instantiate(getContext(),AllFragment.class.getName(),bundle);
        recommendFragment=(RecommendFragment)Fragment.instantiate(getContext(),RecommendFragment.class.getName(),bundle);
        krFragment=(KoreaFragment)Fragment.instantiate(getContext(),KoreaFragment.class.getName(),bundle);
        chFragment=(ChinaFragment)Fragment.instantiate(getContext(),ChinaFragment.class.getName(),bundle);
    }
}
