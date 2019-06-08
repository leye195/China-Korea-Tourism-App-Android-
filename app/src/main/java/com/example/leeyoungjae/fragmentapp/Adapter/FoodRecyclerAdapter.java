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

import com.example.leeyoungjae.fragmentapp.Bean.Food;
import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Food> data;
    private Context context;
    private Activity parentActivity;
    private foodItemClickListener foodItemClickListener;
    public FoodRecyclerAdapter(Context context,List<Food>data){
        this.data=data;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list,viewGroup,false);
        return new FoodRecyclerAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        FoodRecyclerAdapter.MyViewHolder myViewHolder= (FoodRecyclerAdapter.MyViewHolder) viewHolder;
        myViewHolder.place_name.setText(data.get(i).getFood_name());
        myViewHolder.city.setText(data.get(i).getCity());
        String picurl=data.get(i).getImgUrl().toString();
        Picasso.with(context)
                .load(picurl)
                .into(myViewHolder.img);
        //GetImageByUrl getImageByUrl=new GetImageByUrl();
        //getImageByUrl.setImage(myViewHolder.img,picurl);
        if(foodItemClickListener!=null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodItemClickListener.onClick(v,i);
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
        TextView place_name;
        TextView city;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            place_name=itemView.findViewById(R.id.name);
            city=itemView.findViewById(R.id.place);
            img=itemView.findViewById(R.id.food_img);

        }
    }
    public interface foodItemClickListener{
        void onClick(View v,int position);
    }
    public void setItemClickListener(foodItemClickListener foodItemClickListener){
        this.foodItemClickListener=foodItemClickListener;
    }
}
