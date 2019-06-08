package com.example.leeyoungjae.fragmentapp.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityVisitCountUpdate extends StringRequest {
    final static private  String URL="http://10.0.2.2/develop/Travel/Update_visit.php";
    private Map<String,String> parameters;
    public CityVisitCountUpdate(String cityname,String country,String userID, String from, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("cityname",cityname);
        parameters.put("country",country);
        parameters.put("userid",userID);
        parameters.put("from",from);
        Log.i("TAG",parameters.toString());
        //System.out.println("::::"+parameters.toString());
    }
    @Override
    protected Map<String, String> getParams()throws AuthFailureError {
        return parameters;
    }
}
