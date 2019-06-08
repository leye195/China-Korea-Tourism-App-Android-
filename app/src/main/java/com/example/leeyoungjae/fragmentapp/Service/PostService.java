package com.example.leeyoungjae.fragmentapp.Service;

import com.example.leeyoungjae.fragmentapp.Bean.PostList;
import com.example.leeyoungjae.fragmentapp.Bean.Result;

import org.checkerframework.framework.qual.PolyAll;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostService {
    @GET("Read_youji.php")
    Call<PostList>getAllPostList();

    @GET("Read_kr_youji.php")
    Call<PostList>getKrPostList();

    @GET("Read_ch_youji.php")
    Call<PostList>getChPostList();

    @GET("Post_ItemCF.php")
    Call<PostList>getRecommendList(@Query("userid") String userid);

    @Multipart
    @POST("Create_youji.php")
    Call<Result>uploadPost(@PartMap Map<String, RequestBody> contents, @Part List<MultipartBody.Part> imgs);
}
