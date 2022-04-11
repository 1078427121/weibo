package com.bignerdranch.android.success;

public class User {
    private String nickname;
    private String acount;
    private String password;
    private String sex;
    private String phonenumber;
    private String email;
    public User(){

    }
    public User(String nickname,String account,String password,
                String sex,String phonenumber,String email){
        this.nickname=nickname;
        this.acount=account;
        this.password=password;
        this.sex=sex;
        this.phonenumber=phonenumber;
        this.email=email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}