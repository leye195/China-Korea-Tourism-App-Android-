package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityList {
    @SerializedName("response")
    private ArrayList<City>result;
    public CityList(ArrayList<City>result){
        this.result=result;
    }

    public ArrayList<City> getResult() {
        return result;
    }

    public void setResult(ArrayList<City> result) {
        this.result = result;
    }
}
