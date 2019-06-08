package com.example.leeyoungjae.fragmentapp.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateCheckRequest extends StringRequest {
    private HashMap<String,String>parameters;
    public ValidateCheckRequest(String url,Response.Listener<String> listener) {
        super(Method.GET, url, listener, null);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
