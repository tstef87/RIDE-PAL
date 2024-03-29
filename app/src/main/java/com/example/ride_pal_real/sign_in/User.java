package com.example.ride_pal_real.sign_in;

import com.example.ride_pal_real.ui.rides.create.Rides;

import java.util.Locale;

public class User {


    //class for user
    public String firstname, lastname, email, phoneNumber, major;

    public User(){


    }

    public User(String firstname, String lastname, String email, String phoneNumber, String major){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {


        this.major = major;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public String getFullName(){

        String s = "";
        s += getFirstname().substring(0,1).toUpperCase(Locale.ROOT) + "" + getFirstname().substring(1).toLowerCase();
        s += " " + getLastname().substring(0,1).toUpperCase(Locale.ROOT) + "" + getLastname().substring(1).toLowerCase();
        return s;
    }




}
