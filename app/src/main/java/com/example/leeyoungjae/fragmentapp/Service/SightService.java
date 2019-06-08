package com.example.leeyoungjae.fragmentapp.Service;

import com.example.leeyoungjae.fragmentapp.Bean.SightList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SightService {
    @GET("Read_krsight_list.php")
    Call<SightList>getKrSightList(@Query("city")String city);

    @GET("Read_chsight_list.php")
    Call<SightList>getChSightList(@Query("city")String city);
}
