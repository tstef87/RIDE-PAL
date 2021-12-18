package com.example.ride_pal_real.ui.yourRidePals;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.profile.ProfileActivity;
import com.example.ride_pal_real.ui.rides.create.Rides;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class YourRideView extends AppCompatActivity implements View.OnClickListener {

    Button back, delete;
    TextView name, time, days, major, message, getdirections, addtocalender;
    private Rides ride;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_ride_view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        Bundle recdData = getIntent().getExtras();
        ride = new Rides(recdData.getString("time"),
                recdData.getString("destination"),
                recdData.getBoolean("monday"),
                recdData.getBoolean("tuesday"),
                recdData.getBoolean("wednesday"),
                recdData.getBoolean("thursday"),
                recdData.getBoolean("friday"),
                recdData.getString("party1id"),
                recdData.getString("party2id"),
                recdData.getString("party1name"),
                recdData.getString("party2name"),
                recdData.getString("party1phonenumber"),
                recdData.getString("party2phonenumber"),
                recdData.getString("party1address"),
                recdData.getString("party2address"),
                recdData.getString("party1major"),
                recdData.getString("party2major"));

        name = (TextView) findViewById(R.id.yrp_name);
        time = (TextView) findViewById(R.id.yrp_time);
        days = (TextView) findViewById(R.id.yrp_days_of_week);
        major = (TextView) findViewById(R.id.yrp_major);

        if (userId.equals(ride.getParty1id())){
            name.setText(ride.getParty2name());
            major.setText(ride.getParty2major());
        }
        else{
            name.setText(ride.getParty1name());
            major.setText(ride.getParty2major());
        }

        time.setText(ride.getTime());
        days.setText(ride.makeDOW());

        delete = (Button) findViewById(R.id.yrp_delete_ride);
        delete.setOnClickListener(this);
        back = (Button) findViewById(R.id.yrp_back);
        back.setOnClickListener(this);


        message = (TextView) findViewById(R.id.yrp_message);
        message.setOnClickListener(this);

        getdirections = (TextView) findViewById(R.id.yrp_get_directions);
        getdirections.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yrp_delete_ride:

                deleteRide(ride);
                startActivity(new Intent(YourRideView.this, AccountInfoActivity.class));
                break;

            case R.id.yrp_back:
                startActivity(new Intent(YourRideView.this, AccountInfoActivity.class));
                break;

            case R.id.yrp_message:

                openMesseges(ride, userId);
                break;


            case R.id.yrp_get_directions:
                openMap(userId);

                break;


        }

    }



    private void deleteRide(Rides rides){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query party1 = ref.child("YourRides").child(rides.getParty1id()).child(rides.makeTitle());
        Query party2 = ref.child("YourRides").child(rides.getParty2id()).child(rides.makeTitle());


        party1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ridesSnapshot: dataSnapshot.getChildren()) {
                    ridesSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        party2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ridesSnapshot: dataSnapshot.getChildren()) {
                    ridesSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    private void openMesseges(Rides rides, String id){
        String s = "";
        String number;
        if(id.equals(rides.getParty1id())) {
            number = rides.getParty2phonenumber();
        }
        else{
            number = rides.getParty1phonenumber();
        }

        s += number.substring(1,4);
        s += number.substring(6,9);
        s += number.substring(10);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", s, null)));
    }

    private void openMap(String id){

        String url = ride.urlMaker(id);

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

    private void setPP() {

        ImageView img = (ImageView) findViewById(R.id.img);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.child("profilePics/" + userId + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Picasso.with(YourRideView.this).load(uri).into(img);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

