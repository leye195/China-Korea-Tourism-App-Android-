package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodList {
    @SerializedName("response")
    private ArrayList<Food>result;

    public FoodList(ArrayList<Food> result) {
        this.result = result;
    }

    public ArrayList<Food> getResult() {
        return result;
    }

    public void setResult(ArrayList<Food> result) {
        this.result = result;
    }
}
