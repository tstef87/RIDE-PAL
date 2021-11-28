package com.example.ride_pal_real.ui.rides.ridedis;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;

import android.os.Bundle;
import android.widget.TextView;

public class RideDiscription extends AppCompatActivity {

    private TextView nameTV, timeTV, destinationTV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_discription);

        Bundle recdData = getIntent().getExtras();
        String name = recdData.getString("name");
        String time = recdData.getString("destination");
        String des = recdData.getString("time");

        nameTV = findViewById(R.id.dis_username_tv);
        timeTV = findViewById(R.id.dis_time_tv);
        destinationTV = findViewById(R.id.dis_destination_tv);

        nameTV.setText(name);
        timeTV.setText(des);
        destinationTV.setText(time);



    }



}