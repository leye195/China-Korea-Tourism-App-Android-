package com.example.leeyoungjae.fragmentapp.Request;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentUploadRequest extends StringRequest {
    final static private  String URL="http://10.0.2.2/develop/Travel/Create_comment.php";
    private Map<String,String> parameters;
    public CommentUploadRequest(String userID, String postid, String content, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("postid",postid);
        parameters.put("userid",userID);
        parameters.put("content",content);
        Log.i("TAG",parameters.toString());
        //System.out.println("::::"+parameters.toString());
    }
    @Override
    protected Map<String, String> getParams()throws AuthFailureError {
        return parameters;
    }
}
