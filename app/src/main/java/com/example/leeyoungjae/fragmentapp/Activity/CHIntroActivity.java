package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.leeyoungjae.fragmentapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CHIntroActivity extends AppCompatActivity {
    @BindView(R.id.open_hide)
    ImageView img1;
    @BindView(R.id.content_txt1)
    LinearLayout txt1;
    @BindView(R.id.open_hide2)
    ImageView img2;
    @BindView(R.id.content_txt2)
    LinearLayout txt2;
    @BindView(R.id.open_hide3)
    ImageView img3;
    @BindView(R.id.content_txt3)
    LinearLayout txt3;
    @BindView(R.id.open_hide4)
    ImageView img4;
    @BindView(R.id.content_txt4)
    LinearLayout txt4;
    @BindView(R.id.open_hide5)
    ImageView img5;
    @BindView(R.id.content_txt5)
    LinearLayout txt5;
    @BindView(R.id.open_hide6)
    ImageView img6;
    @BindView(R.id.content_txt6)
    LinearLayout txt6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chintro);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.open_hide,R.id.content_txt1})
    public void onOpen_Hide1(View view) {
        if(txt1.getVisibility()==View.GONE){
            img1.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt1.setVisibility(View.VISIBLE);
        }else{
            txt1.setVisibility(View.GONE);
            img1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    @OnClick({R.id.open_hide2,R.id.content_txt2})
    public void onOpen_Hide2(View view) {
        if(txt2.getVisibility()==View.GONE){
            img2.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt2.setVisibility(View.VISIBLE);
        }else{
            txt2.setVisibility(View.GONE);
            img2.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    @OnClick({R.id.open_hide3,R.id.content_txt3})
    public void onOpen_Hide3(View view) {
        if(txt3.getVisibility()==View.GONE){
            img3.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt3.setVisibility(View.VISIBLE);
        }else{
            txt3.setVisibility(View.GONE);
            img3.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    @OnClick({R.id.open_hide4,R.id.content_txt4})
    public void onOpen_Hide4(View view) {
        if(txt4.getVisibility()==View.GONE){
            img4.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt4.setVisibility(View.VISIBLE);
        }else{
            txt4.setVisibility(View.GONE);
            img4.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    @OnClick({R.id.open_hide5,R.id.content_txt5})
    public void onOpen_Hide5(View view) {
        if(txt5.getVisibility()==View.GONE){
            img5.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt5.setVisibility(View.VISIBLE);
        }else{
            txt5.setVisibility(View.GONE);
            img5.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    @OnClick({R.id.open_hide6,R.id.content_txt6})
    public void onOpen_Hide6(View view) {
        if(txt6.getVisibility()==View.GONE){
            img6.setImageResource(R.drawable.ic_keyboard_arrow_up_black_35dp);
            txt6.setVisibility(View.VISIBLE);
        }else{
            txt6.setVisibility(View.GONE);
            img6.setImageResource(R.drawable.ic_keyboard_arrow_down_black_35dp);
        }
    }
    public void onBack(View view) {
        super.onBackPressed();
    }
}
