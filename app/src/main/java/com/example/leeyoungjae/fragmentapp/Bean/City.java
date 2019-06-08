package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city_id")
    private int cityid;
    @SerializedName("city_name")
    private String city_name;
    @SerializedName("country")
    private String country;
    @SerializedName("content")
    private String content;
    @SerializedName("imgUrl")
    private String imgurl;
    @SerializedName("lgn")
    private double lgn;
    @SerializedName("lat")
    private double lgx;

    public City(String city_name,String country,String content){
        this.city_name=city_name;
        this.country=country;
        this.content=content;
    }
    public City(String city_name,String country,String content,String imgurl){
        this.city_name=city_name;
        this.country=country;
        this.content=content;
        this.imgurl=imgurl;
    }
    public City(String city_name,String country,String content,String imgurl,double lgn,double lgx){
        this.city_name=city_name;
        this.country=country;
        this.content=content;
        this.imgurl=imgurl;
        this.lgn=lgn;
        this.lgx=lgx;
    }
    public City(int cityid,String city_name,String country,String content,String imgurl,double lgn,double lgx){
        this.cityid=cityid;
        this.city_name=city_name;
        this.country=country;
        this.content=content;
        this.imgurl=imgurl;
        this.lgn=lgn;
        this.lgx=lgx;
    }

    public int getCityid() { return cityid; }
    public void setCityid(int cityid) { this.cityid = cityid; }
    public String getCity_name() {
        return city_name;
    }
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImgurl() { return imgurl; }
    public void setImgurl(String imgurl) { this.imgurl = imgurl; }
    public double getLgn() {
        return lgn;
    }
    public void setLgn(double lgn) {
        this.lgn = lgn;
    }
    public double getLgx() {
        return lgx;
    }
    public void setLgx(double lgx) {
        this.lgx = lgx;
    }
}
