package com.example.leeyoungjae.fragmentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.leeyoungjae.fragmentapp.Request.DeleteRequest;
import com.example.leeyoungjae.fragmentapp.R;
import com.example.leeyoungjae.fragmentapp.Bean.User;

import org.json.JSONObject;

import java.util.List;

public class UserlistAdapter extends BaseAdapter {
    private List<User> userList;
    private List<User> saveList;
    private Context context;
    private Activity parentActivity;

    public UserlistAdapter(Context context, List<User>userList, List<User>saveList,Activity parentActivity){
        this.userList=userList;
        this.saveList=saveList;
        this.context=context;
        this.parentActivity=parentActivity;
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=convertView.inflate(context,R.layout.user,null);
        final TextView userid=v.findViewById(R.id.user_id);
        TextView userpw=v.findViewById(R.id.user_pw);
        TextView gender=v.findViewById(R.id.gender);

        userid.setText(userList.get(position).getUserID());
        userpw.setText(userList.get(position).getUserPW());
        gender.setText(userList.get(position).getGender());

        v.setTag(userList.get(position).getUserID());

        Button deletebtn=v.findViewById(R.id.delete_btn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json=new JSONObject(response);
                            boolean success=json.getBoolean("success");
                            if(success){
                                userList.remove(position);
                                //saveList.remove(position);
                                for(int i=0;i<saveList.size();i++){
                                    if(saveList.get(position).getUserID().equals(userid.getText().toString())){
                                        saveList.remove(position);
                                        break;
                                    }
                                }
                                Toast.makeText(parentActivity,"删除成功",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest=new DeleteRequest(userid.getText().toString(),responseListener);
                RequestQueue queue=Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });
        return v;
    }
}
