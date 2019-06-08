package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leeyoungjae.fragmentapp.GetImageByUrl;
import com.example.leeyoungjae.fragmentapp.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name;
    private TextView summary_txt;
    private TextView time_txt;
    private TextView address_txt;
    private TextView phone_txt;
    private TextView traffic_txt;
    private TextView open_txt;
    private TextView ticket_txt;

    private String city="";
    private String country="";
    private String summary="";
    private String time="";
    private String address="";
    private String phone="";
    private String imgUrl;
    private String open_time="";
    private String traffic="";
    private String ticket="";
    final private String tmp="抱歉，暂时无内容。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        summary_txt=findViewById(R.id.summary);
        time_txt=findViewById(R.id.time);
        address_txt=findViewById(R.id.address);
        phone_txt=findViewById(R.id.phone);
        traffic_txt=findViewById(R.id.traffic);
        open_txt=findViewById(R.id.open_time);
        ticket_txt=findViewById(R.id.ticket);

        if(getIntent().getStringExtra("imgUrl")!=null) {
            imgUrl = getIntent().getStringExtra("imgUrl");
            img=findViewById(R.id.img);
            Picasso.with(getApplicationContext())
                    .load(imgUrl)
                    .into(img);
        }
        if(getIntent().getStringExtra("city")!=null)
            city=getIntent().getStringExtra("city");
        if(getIntent().getStringExtra("country")!=null)
            country=getIntent().getStringExtra("country");
        if(getIntent().getStringExtra("from").equals("sight")){
            name=findViewById(R.id.name);
            name.setText(getIntent().getStringExtra("sight_name"));
            //name.setText("("+city+")"+" "+getIntent().getStringExtra("sight_name"));
            if(getIntent().getStringExtra("summary")!=null) {
                summary = getIntent().getStringExtra("summary");
                if(summary.equals(""))
                    summary_txt.setText(tmp);
                else
                    summary_txt.setText(summary);
            }
            else
                summary_txt.setText(tmp);
            if(getIntent().getStringExtra("time")!=null) {
                time = getIntent().getStringExtra("time");
                if(time.equals(""))
                    time_txt.setText(tmp);
                else
                    time_txt.setText(time);
            }
            else
                time_txt.setText(tmp);
            if(getIntent().getStringExtra("address")!=null) {
                address = getIntent().getStringExtra("address");
                if(address.equals(""))
                    address_txt.setText(tmp);
                else
                    address_txt.setText(address);
            }
            else
                address_txt.setText(tmp);
            if(getIntent().getStringExtra("phone")!=null) {
                phone = getIntent().getStringExtra("phone");
                if(phone.equals(""))
                    phone_txt.setText(tmp);
                else
                    phone_txt.setText(phone);
            }
            else
                phone_txt.setText(tmp);
            if(getIntent().getStringExtra("open_time")!=null) {
                open_time = getIntent().getStringExtra("open_time");
                if(open_time.equals(""))
                    open_txt.setText(tmp);
                else
                    open_txt.setText(open_time);
            }
            else
                open_txt.setText(tmp);
            if(getIntent().getStringExtra("traffic")!=null) {
                traffic = getIntent().getStringExtra("traffic");
                if(traffic.equals(""))
                    traffic_txt.setText(tmp);
                else
                    traffic_txt.setText(traffic);
            }
            else
                traffic_txt.setText(tmp);
            if(getIntent().getStringExtra("ticket")!=null) {
                ticket = getIntent().getStringExtra("ticket");
                if(ticket.equals(""))
                    ticket_txt.setText(tmp);
                else
                    ticket_txt.setText(ticket);
            }
            else
                ticket_txt.setText(tmp);
        }
    }

    public void onBack(View view) {
        super.onBackPressed();
    }
}
