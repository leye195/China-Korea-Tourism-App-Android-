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

import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.Sight;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SightRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Sight> data;
    private Context context;
    private Activity parentActivity;
    private sightItemClickListener sightItemClickListener;
    public SightRecyclerAdapter(Context context,List<Sight>data){
        this.data=data;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sight_list,viewGroup,false);
        return new SightRecyclerAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        SightRecyclerAdapter.MyViewHolder myViewHolder= (SightRecyclerAdapter.MyViewHolder) viewHolder;
        myViewHolder.sight_name.setText(data.get(i).getSight_name());
        myViewHolder.city.setText("位于：("+data.get(i).getCity()+")\n"+data.get(i).getAddress());
        String picurl=data.get(i).getImgUrl().toString();
        Picasso.with(context)
                .load(picurl)
                .into(myViewHolder.img);
        //GetImageByUrl getImageByUrl=new GetImageByUrl();
        //getImageByUrl.setImage(myViewHolder.img,picurl);
        if(sightItemClickListener!=null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sightItemClickListener.onClick(v,i);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView sight_name;
        TextView city;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sight_name=itemView.findViewById(R.id.name);
            city=itemView.findViewById(R.id.place);
            img=itemView.findViewById(R.id.sight_img);
        }
    }
    public interface sightItemClickListener{
        void onClick(View v,int position);
    }
    public void setItemClickListener(SightRecyclerAdapter.sightItemClickListener sightItemClickListener){
        this.sightItemClickListener=sightItemClickListener;
    }
}
