package com.example.leeyoungjae.fragmentapp.Bean;

public class comment {
    private String postid;
    private String commentid;
    private String userid;
    private String content;
    private String datetime;

    public comment(String postid,String commentid,String userid, String content,String datetime) {
        this.postid = postid;
        this.commentid = commentid;
        this.userid = userid;
        this.content = content;
        this.datetime = datetime;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
