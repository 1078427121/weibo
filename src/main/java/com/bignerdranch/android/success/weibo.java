package com.bignerdranch.android.success;

import java.util.Date;

public class weibo {
    private int weiboId;
    private String userId;
    private String nickName;
    private String content;
    private String time;
    private int dznum;
    private int plnum;
    public weibo(){
    }
    public weibo(int weiboId,String userId,String content,String time,
                 int dznum,int plnum){
        this.weiboId=weiboId;
        this.userId=userId;
        this.content=content;
        this.time=time;
        this.dznum=dznum;
        this.plnum=plnum;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getDznum() {
        return dznum;
    }

    public void setDznum(int dznum) {
        this.dznum = dznum;
    }

    public int getPlnum() {
        return plnum;
    }

    public void setPlnum(int plnum) {
        this.plnum = plnum;
    }
}