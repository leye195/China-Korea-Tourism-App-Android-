package com.example.leeyoungjae.fragmentapp.Service;

import com.example.leeyoungjae.fragmentapp.Bean.City;
import com.example.leeyoungjae.fragmentapp.Bean.CityList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CityService {
    @GET("CityItemCF.php")
    Call<CityList>getCityRecommend(@Query("country")String country,@Query("userid")String userid);
    @GET("Read_city_list.php")
    Call<CityList>ReadCityList(@Query("country")String country, @Query("city")String city);
}
