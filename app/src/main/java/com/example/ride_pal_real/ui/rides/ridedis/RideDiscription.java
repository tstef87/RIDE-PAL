package com.example.ride_pal_real.ui.rides.ridedis;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.rides.create.Rides;

import android.os.Bundle;
import android.widget.TextView;

public class RideDiscription extends AppCompatActivity {

    private TextView name, time, destination;
    private static Rides rideVal;

    public static Rides getRideFromList(Rides ride) {

       rideVal = ride;
       return rideVal;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_discription);

        name = findViewById(R.id.dis_username_tv);
        time = findViewById(R.id.dis_time_tv);
        destination = findViewById(R.id.dis_destination_tv);

        destination.setText(rideVal.getDestination());
        time.setText(rideVal.getTime());
        name.setText(rideVal.getDis());




    }

}