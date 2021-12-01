package com.example.ride_pal_real.ui.rides.ridedis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.map.MapsActivity;
import com.example.ride_pal_real.ui.rides.create.Rides;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RideDiscription extends AppCompatActivity {

    private TextView nameTV, timeTV, destinationTV;
    private Button back, accept;
    private User userProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_discription);

        Bundle recdData = getIntent().getExtras();

        String name = recdData.getString("name");
        String des = recdData.getString("des");
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

        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rides ride = new Rides(recdData.getString("name"),
                        recdData.getString("time"),
                        recdData.getString("destination"),
                        recdData.getBoolean("monday"),
                        recdData.getBoolean("tuesday"),
                        recdData.getBoolean("wednesday"),
                        recdData.getBoolean("thursday"),
                        recdData.getBoolean("friday"),
                        recdData.getString("party1id"),
                        recdData.getString("party2id"));


                addToYourRides(ride);
            }
        });
    }

    private void addToYourRides(Rides rides){



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();
        rides.setParty2id(userId);

        reference = FirebaseDatabase.getInstance().getReference("YourRides");

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FirebaseDatabase.getInstance().getReference("YourRides")
                        .child(rides.getParty1id())
                        .child(rides.getTime())
                        .setValue(rides).addOnSuccessListener(new OnSuccessListener<Void>() {


                    @Override
                    public void onSuccess(Void unused) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query ridesQuery = ref.child("Rides").child(rides.getTime());

                        ridesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ridesSnapshot : dataSnapshot.getChildren()) {
                                    ridesSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        startActivity(new Intent(RideDiscription.this, AccountInfoActivity.class));

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        reference.child(rides.getParty2id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseDatabase.getInstance().getReference("YourRides")
                        .child(rides.getParty2id())
                        .child(rides.getTime())
                        .setValue(rides).addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(RideDiscription.this, AccountInfoActivity.class));

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}