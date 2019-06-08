package com.example.leeyoungjae.fragmentapp.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeletePostRequest extends StringRequest {

    final static private String URL="http://10.0.2.2/develop/Travel/DeletePost.php";
    private Map<String,String> parameters;
    public DeletePostRequest(String post_id,String userid,String title,Response.Listener<String>listener) {
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("postId",post_id);
        parameters.put("userId",userid);
        parameters.put("title",title);
        //parameters.put("upload",upload);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
