package com.example.leeyoungjae.fragmentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Activity.ChinaActivity;
import com.example.leeyoungjae.fragmentapp.Activity.DetailcActivity;
import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Request.CityVisitCountUpdate;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class RecommendCityAdapter extends BaseAdapter {
    private List<City> citylist;
    private String userid;
    private Context context;
    private LayoutInflater layoutInflater;
    public RecommendCityAdapter(Context context,List<City>cities,String userid){
        this.citylist=cities;
        this.userid=userid;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return citylist.size();
    }

    @Override
    public Object getItem(int position) {
        return citylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.citygrid,null);
            holder=new ViewHolder();
            String picurl=citylist.get(position).getImgurl().toString();
            holder.linearLayout=convertView.findViewById(R.id.recommend_citem);
            holder.img=convertView.findViewById(R.id.city_img);
            holder.txt=convertView.findViewById(R.id.city_text);
            //GetImageByUrl getImageByUrl=new GetImageByUrl();
            //getImageByUrl.setImage(holder.img,picurl);
            Picasso.with(convertView.getContext())
                    .load(picurl)
                    .into(holder.img);
            holder.txt.setText(citylist.get(position).getCity_name());
            final View finalConvertView = convertView;
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Response.Listener<String>responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject json=new JSONObject(response);
                                boolean success=json.getBoolean("visit_success");
                                System.out.println(success);
                                if(success){
                                    Intent intent=new Intent(finalConvertView.getContext(),DetailcActivity.class);
                                    intent.putExtra("city",citylist.get(position).getCity_name());
                                    intent.putExtra("imgUrl",citylist.get(position).getImgurl());
                                    intent.putExtra("country",citylist.get(position).getCountry());
                                    intent.putExtra("lat",citylist.get(position).getLgx());
                                    intent.putExtra("lgn",citylist.get(position).getLgn());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    if(citylist.get(position).getCountry().equals("韩国"))
                                        intent.putExtra("from","kr");
                                    else if(citylist.get(position).getCountry().equals("中国"))
                                        intent.putExtra("from","ch");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    finalConvertView.getContext().startActivity(intent);
                                }else{
                                    Toast.makeText(finalConvertView.getContext(),"访问失败",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    CityVisitCountUpdate visitcountRequest=new CityVisitCountUpdate(citylist.get(position).getCity_name(),citylist.get(position).getCountry(),userid,"city",responseListener);
                    RequestQueue queue= Volley.newRequestQueue(context);
                    queue.add(visitcountRequest);

                    Intent intent=new Intent(finalConvertView.getContext(),DetailcActivity.class);
                    intent.putExtra("city",citylist.get(position).getCity_name());
                    intent.putExtra("imgUrl",citylist.get(position).getImgurl());
                    intent.putExtra("country",citylist.get(position).getCountry());
                    //intent.putExtra("lat",citylist.get(position).getLgx());
                    //intent.putExtra("lgn",citylist.get(position).getLgn());
                    if(citylist.get(position).getCountry().equals("韩国"))
                        intent.putExtra("from","kr");
                    else if(citylist.get(position).getCountry().equals("中国"))
                        intent.putExtra("from","ch");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finalConvertView.getContext().startActivity(intent);
                }
            });
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout linearLayout;
        ImageView img;
        TextView txt;
    }
}
