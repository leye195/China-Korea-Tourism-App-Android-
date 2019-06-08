package com.example.leeyoungjae.fragmentapp.Service;

import com.example.leeyoungjae.fragmentapp.Bean.FoodList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodService {
    @GET("Read_food_list.php")
    Call<FoodList>getFoodList(@Query("city")String city,@Query("country")String country);
}
