package com.example.leeyoungjae.fragmentapp.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class LoginRequest extends StringRequest {
    final static private String URL="http://10.0.2.2/develop/Travel/Login.php";
    private Map<String,String>parameters;
    public LoginRequest(String userid, String userpw, com.android.volley.Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("userId",userid);
        parameters.put("userpw",userpw);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
