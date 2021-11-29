package com.example.ride_pal_real.sign_in;

import com.example.ride_pal_real.ui.rides.create.Rides;

import java.util.ArrayList;

public class User {


    public String firstname, lastname, email;
    public ArrayList<Rides> yourRideList;

    public User(){


    }

    public User(String firstname, String lastname, String email, ArrayList<Rides> yourRideList){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.yourRideList = yourRideList;
    }


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

    public ArrayList<Rides> getYourRideList() {
        return yourRideList;
    }

    public void addYourRideList(Rides ride) {
        this.yourRideList.add(ride);
    }
}
