package com.example.ride_pal_real.ui.rides;

import com.example.ride_pal_real.sign_in.User;
import com.google.firebase.auth.FirebaseUser;

public class Rides {

    public User user;
    public String dis, time, destination;
    public boolean monday, tuesday, wednesday, thursday, friday;

    public Rides(){


    }

    public Rides( String dis, String time, String destination, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday){
        this.dis = dis;
        this.time = time;
        this.destination = destination;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
}
