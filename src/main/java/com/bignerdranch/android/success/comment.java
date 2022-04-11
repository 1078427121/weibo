package com.bignerdranch.android.success;

public class comment {
    private int weiboId;
    private int userId;
    private String author;
    private String content;
    private String time;
    public comment(){

    }
    public comment(int weiboId,int userId,String content,String time){
        this.weiboId=weiboId;
        this.userId=userId;
        this.content=content;
        this.time=time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public int getUserID() {
        return userId;
    }

    public void setUserID(int userID) {
        this.userId = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}