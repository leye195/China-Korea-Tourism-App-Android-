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
import com.example.leeyoungjae.fragmentapp.Bean.Youji;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YoujiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Youji>data;
    private Context context;
    private Activity parentActivity;
    private youjiItemClickListener youjiItemClickListener;
    public YoujiRecyclerAdapter(Context context,List<Youji>data){
        this.data=data;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youjicard,viewGroup,false);
        //View v=View.inflate(context,R.layout.youji_item,null);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder= (MyViewHolder) viewHolder;
        myViewHolder.user_id.setText(data.get(i).getUserID());
        myViewHolder.upload_date.setText(data.get(i).getUpload_date());
        myViewHolder.title.setText(data.get(i).getTitle());
        myViewHolder.content.setText(data.get(i).getContent());
        myViewHolder.visit_num.setText(data.get(i).getVisitor_num()+"人浏览");
        if(data.get(i).getImgUrl()!=null){ //当imgURl不为NULL时，显示用户上传的图片
            String picurl=data.get(i).getImgUrl().toString();

            Picasso.with(context)
                    .load(picurl)
                    .into(myViewHolder.img);
            //GetImageByUrl getImageByUrl = new GetImageByUrl();
            //getImageByUrl.setImage(myViewHolder.img, picurl);
        }
        else if(data.get(i).getImgUrl()==null){
            myViewHolder.img.setImageResource(R.drawable.ic_launcher_background);
        }
        if(youjiItemClickListener!=null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    youjiItemClickListener.onClick(v,i);
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
        TextView user_id;
        TextView upload_date;
        TextView title;
        TextView content;
        TextView visit_num;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id=itemView.findViewById(R.id.user_id);
            upload_date=itemView.findViewById(R.id.upload_date);
            title=itemView.findViewById(R.id.title_text);
            content=itemView.findViewById(R.id.content_text);
            visit_num=itemView.findViewById(R.id.visit_cnt);
            img=itemView.findViewById(R.id.head_img);
        }

    }
    public interface youjiItemClickListener{
        void onClick(View v,int position);
    }
    public void setItemClickListener(youjiItemClickListener youjiItemClickListener){
        this.youjiItemClickListener=youjiItemClickListener;
    }
}
