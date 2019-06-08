package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class CommentList {
    @SerializedName("response")
    private ArrayList<comment>result;

    public CommentList(ArrayList<comment> result) {
        this.result = result;
    }

    public ArrayList<comment> getResult() {
        return result;
    }

    public void setResult(ArrayList<comment> result) {
        this.result = result;
    }
}
