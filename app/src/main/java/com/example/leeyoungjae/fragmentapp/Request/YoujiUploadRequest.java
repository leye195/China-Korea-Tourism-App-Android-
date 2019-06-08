package com.example.leeyoungjae.fragmentapp.Request;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class YoujiUploadRequest extends StringRequest {
    final static private  String URL="http://10.0.2.2/develop/Travel/Create_youji.php";
    private Map<String,String> parameters;

    public YoujiUploadRequest(String userID, String place, String title, String content,String upload,String visitor,Bitmap bitmap,String sub_imgs,Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("userId",userID);
        parameters.put("place",place);
        parameters.put("title",title);
        parameters.put("content",content);
        parameters.put("upload",upload);
        parameters.put("visitor",visitor);
        parameters.put("img",imgToString(bitmap));
        parameters.put("sub_imgs",sub_imgs);
        Log.i("TAG",parameters.toString());
        //System.out.println("::::"+parameters.toString());
    }

    //Change bitmap to String
    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[]imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    @Override
    protected Map<String, String> getParams()throws AuthFailureError {
        return parameters;
    }
}
