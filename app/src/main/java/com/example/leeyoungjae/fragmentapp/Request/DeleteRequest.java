package com.example.leeyoungjae.fragmentapp.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {
    final static private String URL="http://10.0.2.2/develop/Travel/Delete.php";
    private Map<String,String> parameters;
    public DeleteRequest(String userid,Response.Listener<String>listener) {
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("userId",userid);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
