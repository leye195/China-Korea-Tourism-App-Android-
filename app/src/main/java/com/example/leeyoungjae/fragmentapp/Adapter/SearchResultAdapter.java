package com.example.leeyoungjae.fragmentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeyoungjae.fragmentapp.Activity.AboutCityActivity;
import com.example.leeyoungjae.fragmentapp.Activity.DetailfActivity;
import com.example.leeyoungjae.fragmentapp.Activity.DetailsActivity;
import com.example.leeyoungjae.fragmentapp.Activity.KRCityActivity;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.Food;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
import com.example.leeyoungjae.fragmentapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResultAdapter extends BaseAdapter {
    private List<Object> uList;
    private List<Object> saveList;
    private Context context;
    private Activity parentActivity;
    public SearchResultAdapter(Context context, List<Object>uList,Activity parentActivity){
        this.uList=uList;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @Override
    public int getCount() {
        return uList.size();
    }

    @Override
    public Object getItem(int position) {
        return uList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=convertView.inflate(context,R.layout.searchresult_item,null);
        LinearLayout linearLayout=v.findViewById(R.id.searchitem);
        final TextView title=v.findViewById(R.id.name);
        final TextView category=v.findViewById(R.id.category);
        if(uList.get(position) instanceof City){
            title.setText(((City) uList.get(position)).getCity_name());
            category.setText(((City) uList.get(position)).getCountry());
        }else if(uList.get(position) instanceof Sight){
            title.setText(((Sight) uList.get(position)).getSight_name());
            category.setText(((Sight) uList.get(position)).getCountry()+"-"+((Sight) uList.get(position)).getCity());
        }else if(uList.get(position) instanceof Food){
            title.setText(((Food) uList.get(position)).getFood_name());
            category.setText(((Food) uList.get(position)).getCountry()+"-"+((Food) uList.get(position)).getCity());
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parentActivity,"Clicked",Toast.LENGTH_SHORT).show();
                if(uList.get(position) instanceof City){
                    Intent intent=new Intent(v.getContext(),AboutCityActivity.class);
                    if(((City) uList.get(position)).getCountry().equals("韩国")){
                        intent.putExtra("from","kr");
                        intent.putExtra("imgUrl",((City) uList.get(position)).getImgurl());
                        intent.putExtra("city",((City) uList.get(position)).getCity_name());
                        //intent.putExtra("content",((City) uList.get(position)).getContent());
                        intent.putExtra("lgn",((City) uList.get(position)).getLgn());
                        intent.putExtra("lat",((City) uList.get(position)).getLgx());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        v.getContext().startActivity(intent);
                    }else if(((City) uList.get(position)).getCountry().equals("中国")){
                        intent.putExtra("from","ch");
                        intent.putExtra("imgUrl",((City) uList.get(position)).getImgurl());
                        intent.putExtra("city",((City) uList.get(position)).getCity_name());
                        intent.putExtra("content",((City) uList.get(position)).getContent());
                        intent.putExtra("lgn",((City) uList.get(position)).getLgn());
                        intent.putExtra("lat",((City) uList.get(position)).getLgx());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        v.getContext().startActivity(intent);
                    }
                }else if(uList.get(position) instanceof Sight){
                    Intent intent=new Intent(v.getContext(),DetailsActivity.class);
                    intent.putExtra("city",((Sight) uList.get(position)).getCity());
                    intent.putExtra("imgUrl",((Sight) uList.get(position)).getImgUrl());
                    intent.putExtra("from","sight");
                    intent.putExtra("sight_name",((Sight) uList.get(position)).getSight_name());
                    intent.putExtra("summary",((Sight) uList.get(position)).getSummary());
                    intent.putExtra("time",((Sight) uList.get(position)).getTime());
                    intent.putExtra("address",((Sight) uList.get(position)).getAddress());
                    intent.putExtra("phone",((Sight) uList.get(position)).getPhone());
                    intent.putExtra("open_time",((Sight) uList.get(position)).getOpen_time());
                    intent.putExtra("traffic",((Sight) uList.get(position)).getTraffic());
                    intent.putExtra("ticket",((Sight) uList.get(position)).getTicket());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                }else if(uList.get(position) instanceof Food){
                    Intent intent=new Intent(v.getContext(),DetailfActivity.class);
                    intent.putExtra("imgUrl",((Food) uList.get(position)).getImgUrl());
                    intent.putExtra("city",((Food) uList.get(position)).getCity());
                    intent.putExtra("name",((Food) uList.get(position)).getFood_name());
                    intent.putExtra("summary",((Food) uList.get(position)).getSummary());
                    intent.putExtra("cost",((Food) uList.get(position)).getCost());
                    intent.putExtra("address",((Food) uList.get(position)).getAddress());
                    intent.putExtra("phone",((Food) uList.get(position)).getPhone());
                    intent.putExtra("open",((Food) uList.get(position)).getTime());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                }
                //Map<Character,Integer>mp=new HashMap<>();

            }
        });
        return v;
    }
}
