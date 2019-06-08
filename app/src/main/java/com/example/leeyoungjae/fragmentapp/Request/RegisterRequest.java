package com.example.leeyoungjae.fragmentapp.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private  String URL="http://10.0.2.2/develop/Travel/Register.php";
    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String gender, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("userId",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("gender",gender);
        Log.i("TAG",parameters.toString());
    }

    @Override
    protected Map<String, String> getParams()throws AuthFailureError{
        return parameters;
    }
}
