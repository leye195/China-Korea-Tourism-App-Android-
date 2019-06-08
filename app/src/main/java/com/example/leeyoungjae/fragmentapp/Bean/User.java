package com.example.leeyoungjae.fragmentapp.Bean;

public class User {
    private String userID;
    private String userPW;
    private String gender;
    private String userUrl;

    public User(String userID, String userPW, String gender, String userUrl){
        this.userID=userID;
        this.userPW=userPW;
        this.gender=gender;
        this.userUrl=userUrl;

    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getUserUrl() {
        return userUrl;
    }
    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
