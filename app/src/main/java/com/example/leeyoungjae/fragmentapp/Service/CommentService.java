package com.example.leeyoungjae.fragmentapp.Service;

import com.example.leeyoungjae.fragmentapp.Bean.CommentList;
import com.example.leeyoungjae.fragmentapp.Bean.comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentService {
    @GET("Read_comment_list.php")
    Call<CommentList>getComments(@Query("post_id")String post_id);

    @FormUrlEncoded
    @POST("Create_comment.php")
    Call<comment>createComment(@FieldMap Map<String,String> params);
}
