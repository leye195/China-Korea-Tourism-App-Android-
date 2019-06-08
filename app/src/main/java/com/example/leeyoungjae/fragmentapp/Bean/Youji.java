package com.example.leeyoungjae.fragmentapp.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Youji implements Parcelable {
    @SerializedName("postid")
    private String postid;
    @SerializedName("userid")
    private String userID;
    private String UserAvatar;
    @SerializedName("title")
    private String title;
    @SerializedName("place")
    private String place;
    @SerializedName("content")
    private String content;
    @SerializedName("upload_date")
    private String upload_date;
    @SerializedName("visitor_num")
    private String visitor_num;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("sub_imgUrls")
    private String sub_imgUrl;

    public Youji(String postid, String userID, String title, String place, String content, String upload_date, String visitor_num, String imgUrl, String sub_imgUrl) {
        this.postid = postid;
        this.userID = userID;
        this.title = title;
        this.place = place;
        this.content = content;
        this.upload_date = upload_date;
        this.visitor_num = visitor_num;
        this.imgUrl = imgUrl;
        this.sub_imgUrl = sub_imgUrl;
    }

    public Youji(String postid, String userID, String title, String place, String content, String upload_date, String visitor_num, String imgUrl) {
        this.postid = postid;
        this.userID = userID;
        this.title = title;
        this.place = place;
        this.content = content;
        this.upload_date = upload_date;
        this.visitor_num = visitor_num;
        this.imgUrl = imgUrl;
    }

    public Youji(String userID, String UserAvatar, String title, String place, String content, String upload_date, String visitor_num){
        this.userID=userID;
        this.title=title;
        this.place=place;
        this.content=content;
        this.UserAvatar=UserAvatar;
        this.upload_date=upload_date;
        this.visitor_num=visitor_num;
    }
    public Youji(String userID,String title, String place, String content, String upload_date, String visitor_num){
        this.userID=userID;
        this.title=title;
        this.place=place;
        this.content=content;
        this.upload_date=upload_date;
        this.visitor_num=visitor_num;
    }
    public Youji(String postid,String userID,String title,String upload_date){
        this.postid=postid;
        this.userID=userID;
        this.title=title;
        this.upload_date=upload_date;
    }
    /*protected Youji(Parcel in){
        this.userID=in.readString();
        this.title=in.readString();
        this.place=in.readString();
        this.content=in.readString();
        this.UserAvatar=in.readString();
        this.upload_date=in.readString();
        this.visitor_num=in.readString();
    }*/

    public Youji() {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserAvatar() {
        return UserAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        UserAvatar = userAvatar;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUpload_date() {
        return upload_date;
    }
    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getVisitor_num() {
        return visitor_num;
    }
    public void setVisitor_num(String visitor_num) {
        this.visitor_num = visitor_num;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSub_imgUrl() {
        return sub_imgUrl;
    }

    public void setSub_imgUrl(String sub_imgUrl) {
        this.sub_imgUrl = sub_imgUrl;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeString(this.upload_date);
        dest.writeString(this.place);
        dest.writeString(this.visitor_num);
        dest.writeString(this.UserAvatar);
    }
    public static final Parcelable.Creator<Youji>CREATOR = new Creator<Youji>() {
        @Override
        public Youji createFromParcel(Parcel source) {
            Youji youji=new Youji();
            youji.userID=source.readString();
            youji.upload_date=source.readString();
            youji.place=source.readString();
            youji.content=source.readString();
            youji.title=source.readString();
            youji.visitor_num=source.readString();
            youji.UserAvatar=source.readString();
            return youji;
        }

        @Override
        public Youji[] newArray(int size) {
            return new Youji[size];
        }
    };
}
