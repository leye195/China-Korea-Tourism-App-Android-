package com.example.leeyoungjae.fragmentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<City> data;
    private List<City> savelist;
    private Context context;
    private Activity parentActivity;
    private cityItemClickListener cityItemClickListener;
    public CityRecyclerAdapter(Context context,List<City>data,List<City>savelist){
        this.data=data;
        this.savelist=savelist;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_list,viewGroup,false);
        return new CityRecyclerAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        CityRecyclerAdapter.MyViewHolder myViewHolder= (CityRecyclerAdapter.MyViewHolder) viewHolder;
        myViewHolder.title.setText(data.get(i).getCity_name());
        myViewHolder.content.setText(data.get(i).getContent());
        //根据url获取图片
        String picurl=data.get(i).getImgurl().toString();
        Picasso.with(context)
                .load(picurl)
                .into(myViewHolder.img);
        //GetImageByUrl getImageByUrl=new GetImageByUrl();
        //getImageByUrl.setImage(myViewHolder.img,picurl);
        if(cityItemClickListener!=null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityItemClickListener.onClick(v,i);
                }
            });
        }
    }
    public void updateData(List<City>list){
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;
        TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_text);
            content=itemView.findViewById(R.id.content_text);
            img=itemView.findViewById(R.id.city_img);
        }
    }
    public interface cityItemClickListener{
        void onClick(View v,int position);
    }
    public void setItemClickListener(cityItemClickListener cityItemClickListener){
        this.cityItemClickListener=cityItemClickListener;
    }
}
