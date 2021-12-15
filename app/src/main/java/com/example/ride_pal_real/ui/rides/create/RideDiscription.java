package com.example.ride_pal_real.ui.rides.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.map.MapsActivity;
import com.example.ride_pal_real.ui.map.MapsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RideDiscription extends AppCompatActivity {

    private TextView nameTV, timeTV, destinationTV;
    private Button back, accept;
    private User userProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String party2Name;
    private String party2phonenumber;
    private String party2address;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_discription);

        Bundle recdData = getIntent().getExtras();

        String name = recdData.getString("party1name");
        String des = recdData.getString("destination");
        String time = recdData.getString("time");


        nameTV = findViewById(R.id.dis_username_tv);
        timeTV = findViewById(R.id.dis_time_tv);
        destinationTV = findViewById(R.id.dis_destination_tv);

        nameTV.setText(name);
        timeTV.setText(des);
        destinationTV.setText(time);

        back = findViewById(R.id.ride_dis_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RideDiscription.this, AccountInfoActivity.class));

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                party2Name = user.getFullName();
                party2phonenumber = user.getPhoneNumber();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MapsFragment mf = new MapsFragment();



        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Rides ride = new Rides(recdData.getString("time"),
                        recdData.getString("destination"),
                        recdData.getBoolean("monday"),
                        recdData.getBoolean("tuesday"),
                        recdData.getBoolean("wednesday"),
                        recdData.getBoolean("thursday"),
                        recdData.getBoolean("friday"),
                        recdData.getString("party1id"),
                        recdData.getString("party2id"),
                        recdData.getString("party1name"),
                        party2Name,
                        recdData.getString("party1phonenumber"),
                        party2phonenumber,
                        recdData.getString("party1address"),
                        party2address);

                if(!userId.equals(ride.getParty1id())) {
                    //Intent i = new Intent(RideDiscription.this , ApplyForRideActivity.class);
                    Intent i = new Intent(RideDiscription.this, MapsActivity.class);
                    i.putExtra("intent", "ApplyForRideActivity");
                    i.putExtra("RideName", ride.makeTitle());
                    startActivity(i);


                }
                else{
                    Toast.makeText(RideDiscription.this, "This is your own listing, you can not apply for it!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }


}

