package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.squareup.picasso.Picasso;

public class DetailfActivity extends AppCompatActivity {
    private ImageView img;
    private TextView name_txt;
    private TextView summary_txt;
    private TextView traffic_txt;
    private TextView phone_txt;
    private TextView open_txt;
    private TextView address_txt;
    private TextView avg_cost_txt;

    private final String tmp="抱歉！暂无内容";
    private String imgUrl;
    private String city;
    private String name;
    private String summary;
    private String traffic;
    private String phone;
    private String open;
    private String address;
    private String avg_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailf);

        name_txt=findViewById(R.id.name);
        summary_txt=findViewById(R.id.summary);
        address_txt=findViewById(R.id.address);
        summary_txt=findViewById(R.id.summary);
        avg_cost_txt=findViewById(R.id.cost);
        traffic_txt=findViewById(R.id.traffic);
        phone_txt=findViewById(R.id.phone);
        open_txt=findViewById(R.id.open_time);

        if(getIntent().getStringExtra("imgUrl")!=null){
            imgUrl = getIntent().getStringExtra("imgUrl");
            img=findViewById(R.id.img);

            Picasso.with(getApplicationContext())
                    .load(imgUrl)
                    .into(img);
        }
        if(getIntent().getStringExtra("city")!=null)
            city=getIntent().getStringExtra("city");
        if(getIntent().getStringExtra("name")!=null){
            name=getIntent().getStringExtra("name");
        }
        name_txt.setText(name);
        if(getIntent().getStringExtra("summary")!=null){
            summary=getIntent().getStringExtra("summary");
            if(summary.equals(""))
                summary_txt.setText(tmp);
            else
                summary_txt.setText(summary);
        }else{
            summary_txt.setText(tmp);
        }
        if(getIntent().getStringExtra("address")!=null){
            address=getIntent().getStringExtra("address");
            if(address.equals(""))
                address_txt.setText(tmp);
            else
                address_txt.setText(address);
        }else{
            address_txt.setText(tmp);
        }
        if(getIntent().getStringExtra("phone")!=null){
            phone=getIntent().getStringExtra("phone");
            if(phone.equals(""))
                phone_txt.setText(tmp);
            else
                phone_txt.setText(phone);
        }else{
            phone_txt.setText(tmp);
        }
        if(getIntent().getStringExtra("cost")!=null){
            avg_cost=getIntent().getStringExtra("cost");
            if(avg_cost.equals(""))
                avg_cost_txt.setText(tmp);
            else
                avg_cost_txt.setText(avg_cost);
        }else{
            avg_cost_txt.setText(tmp);
        }
        if(getIntent().getStringExtra("open")!=null){
            open=getIntent().getStringExtra("open");
            if(open.equals(""))
                open_txt.setText(tmp);
            else
                open_txt.setText(open);
        }else{
            open_txt.setText(tmp);
        }
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
}
