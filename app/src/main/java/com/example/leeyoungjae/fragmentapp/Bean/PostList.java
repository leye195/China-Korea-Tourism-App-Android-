package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostList {
    @SerializedName("response")
    private ArrayList<Youji>result;

    public PostList(ArrayList<Youji> result) {
        this.result = result;
    }

    public ArrayList<Youji> getResult() {
        return result;
    }

    public void setResult(ArrayList<Youji> result) {
        this.result = result;
    }
}
