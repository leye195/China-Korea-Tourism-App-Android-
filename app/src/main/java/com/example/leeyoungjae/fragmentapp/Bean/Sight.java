package com.example.leeyoungjae.fragmentapp.Bean;

import com.google.gson.annotations.SerializedName;

public class Sight {
    @SerializedName("sight_name")
    private String sight_name;
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
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("traffic")
    private String traffic;
    @SerializedName("ticket")
    private String ticket;
    @SerializedName("open_time")
    private String open_time;
    private double lgn;
    private double lat;

    public Sight(String sight_name, String city, String country, String imgUrl, String time, String address, String summary, String phone,String traffic,String ticket,String open_time) {
        this.sight_name = sight_name;
        this.city = city;
        this.country = country;
        this.imgUrl = imgUrl;
        this.time = time;
        this.address = address;
        this.summary = summary;
        this.phone = phone;
        this.summary=summary;
        this.traffic=traffic;
        this.ticket=ticket;
        this.open_time=open_time;
    }
    public Sight(String sight_name, String city, String country, String imgUrl,String address,double lgn,double lat) {
        this.sight_name = sight_name;
        this.city = city;
        this.country = country;
        this.imgUrl = imgUrl;
        this.address = address;
        this.lgn=lgn;
        this.lat=lat;
    }
    public Sight(String sight_name, String city, String country, String imgUrl,String address) {
        this.sight_name = sight_name;
        this.city = city;
        this.country = country;
        this.imgUrl = imgUrl;
        this.address = address;
    }
    public Sight(String sight_name,String city, String country,String address) {
        this.sight_name=sight_name;
        this.city = city;
        this.country = country;
        this.address=address;
    }
    public Sight(String sight_name,String city, String country) {
        this.sight_name=sight_name;
        this.city = city;
        this.country = country;
    }

    public Sight(String country, String imgUrl) {
        this.country = country;
        this.imgUrl = imgUrl;
    }

    public String getSight_name() {
        return sight_name;
    }

    public void setSight_name(String sight_name) {
        this.sight_name = sight_name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public double getLgn() {
        return lgn;
    }

    public void setLgn(double lgn) {
        this.lgn = lgn;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
