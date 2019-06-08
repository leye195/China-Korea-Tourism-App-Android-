package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("name")
    private String food_name;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("summary")
    private String summary;
    @SerializedName("time")
    private String time;
    @SerializedName("cost")
    private String cost;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;


    public Food(String food_name, String city, String country,String imgUrl,String summary,String time,String cost,String address,String phone) {
        this.food_name = food_name;
        this.city = city;
        this.country = country;
        this.imgUrl = imgUrl;
        this.summary=summary;
        this.cost=cost;
        this.time=time;
        this.address=address;
        this.phone=phone;
    }
    public Food(String food_name, String city, String country,String address,String imgUrl) {
        this.food_name = food_name;
        this.city = city;
        this.country = country;
        this.address=address;
        this.imgUrl=imgUrl;
    }
    public Food(String food_name, String city, String country,String address) {
        this.food_name = food_name;
        this.city = city;
        this.country = country;
        this.address=address;
    }
    public Food(String food_name, String city, String country) {
        this.food_name = food_name;
        this.city = city;
        this.country = country;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
