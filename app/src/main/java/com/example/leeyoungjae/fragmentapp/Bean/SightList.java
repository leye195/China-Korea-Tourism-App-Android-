package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SightList {
    @SerializedName("response")
    private ArrayList<Sight>result;

    public SightList(ArrayList<Sight> result) {
        this.result = result;
    }

    public ArrayList<Sight> getResult() {
        return result;
    }

    public void setResult(ArrayList<Sight> result) {
        this.result = result;
    }
}
