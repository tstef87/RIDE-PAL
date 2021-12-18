package com.example.ride_pal_real.ui.rides.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.map.MapsActivity;
import com.example.ride_pal_real.ui.map.MapsFragment;
import com.example.ride_pal_real.ui.profile.ProfileActivity;
import com.example.ride_pal_real.ui.yourRidePals.YourRideView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RideDiscription extends AppCompatActivity {

    TextView nameTV, timeTV, destinationTV, majorTV, vom;
    Button back, accept;
    User userProfile;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    String party2name, party2phonenumber, party2address, party2major, address;
    ImageView profilePic;
    Bundle recdData;




    //when you click on a ride in the list fragment it will display this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_discription);

        recdData = getIntent().getExtras();

        String name = recdData.getString("party1name");
        String des = recdData.getString("destination");
        String time = recdData.getString("time");
        String m = recdData.getString("party1major");
        address = recdData.getString("party1address");


        nameTV = findViewById(R.id.dis_username_tv);
        timeTV = findViewById(R.id.dis_time_tv);
        destinationTV = findViewById(R.id.dis_destination_tv);
        majorTV = findViewById(R.id.dis_major);
        profilePic = findViewById(R.id.iv_cp);
        vom = findViewById(R.id.vom);

        setPP();

        nameTV.setText(name);
        timeTV.setText(des);
        destinationTV.setText(time);
        majorTV.setText(m);

        vom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

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
                party2name = user.getFullName();
                party2phonenumber = user.getPhoneNumber();
                party2major = user.getMajor();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MapsFragment mf = new MapsFragment();

        //apply for ride button
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
                        "-",
                        recdData.getString("party1phonenumber"),
                        "-",
                        recdData.getString("party1address"),
                        "-",
                        recdData.getString("party1major"),
                        "-");

                if(!userId.equals(ride.getParty1id())) {

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

    //sets profile picture
    private void setPP(){

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        ImageView img = (ImageView) findViewById(R.id.iv_cp);

        storageRef.child("profilePics/"+recdData.getString("party1id")+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {

                            Picasso.with(RideDiscription.this).load(uri).into(img);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //opens the map
    private void openMap(){

        String url = "https://www.google.com/maps/dir/?api=1&destination="+address;

        Uri gmmIntentUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }



}

