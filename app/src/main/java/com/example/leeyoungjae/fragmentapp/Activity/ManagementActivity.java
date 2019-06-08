package com.example.leeyoungjae.fragmentapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leeyoungjae.fragmentapp.Adapter.UserlistAdapter;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    private ListView listView;
    private List<User> userlist;
    private  List<User> savelist;
    private UserlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent=getIntent();
        listView=findViewById(R.id.listview);
        userlist=new ArrayList<>();
        savelist=new ArrayList<>();
        adapter=new UserlistAdapter(getApplicationContext(),userlist,savelist,this);
        listView.setAdapter(adapter);
        try{
            JSONObject json=new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray=json.getJSONArray("response");
            int count=0;
            String userid,userpw,gender;
            while (count<jsonArray.length()){
                JSONObject obj=jsonArray.getJSONObject(count);
                userid=obj.getString("userid");
                userpw=obj.getString("userpassword");
                gender=obj.getString("gender");
                //User user=new User(userid,userpw,gender);
                //if(!userid.equals("admin")){
                //    userlist.add(user);
                //    savelist.add(user);
               // }
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        final EditText searchTxt=findViewById(R.id.search);
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void searchUser(String search){
        userlist.clear();
        for(int i=0;i<savelist.size();i++){
            if(savelist.get(i).getUserID().contains(search)){
                userlist.add(savelist.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
