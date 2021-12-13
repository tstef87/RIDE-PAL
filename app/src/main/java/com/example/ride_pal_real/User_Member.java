package com.example.ride_pal_real;

public class User_Member {
    public User_Member(String name, String pro, String bio, String ph, String ad, String email) {
        this.name = name;
        this.pro = pro;
        this.bio = bio;
        this.ph = ph;
        this.ad = ad;
        this.email = email;
    }

    String name;
    String pro;
    String bio;

    String ph;
    String ad;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User_Member(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

}
