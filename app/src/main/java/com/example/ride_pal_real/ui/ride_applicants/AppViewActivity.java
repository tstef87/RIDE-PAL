package com.example.ride_pal_real.ui.ride_applicants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.rides.create.RideDiscription;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.example.ride_pal_real.ui.yourRidePals.YourRideView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name, pref, canDrive, message, major;
    Button back, accept, decline;
    String ref;
    Application application;
    Rides ride;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_view);

        name = (TextView) findViewById(R.id.av_name);
        pref = (TextView) findViewById(R.id.av_preff);
        canDrive = (TextView) findViewById(R.id.av_can_drive);
        major = (TextView) findViewById(R.id.av_major);
        message = (TextView) findViewById(R.id.av_message);


        message.setOnClickListener(this);

        back = (Button) findViewById(R.id.av_back);
        back.setOnClickListener(this);

        accept = (Button) findViewById(R.id.av_accept);
        accept.setOnClickListener(this);

        decline = (Button) findViewById(R.id.av_decline);
        decline.setOnClickListener(this);

        Bundle recdData = getIntent().getExtras();

        ref = recdData.getString("ref");


        application = new Application(recdData.getString("name"),
                recdData.getString("pref"),
                recdData.getString("id"),
                recdData.getString("phonenumber"),
                recdData.getString("canDrive"),
                recdData.getString("address"),
                recdData.getString("major"));


        name.setText(application.getName());
        canDrive.setText(canDrive(application.getCanDrive()));
        pref.setText(application.getPreferences());
        major.setText(application.getMajor());

        reference = FirebaseDatabase.getInstance().getReference("Rides");

        reference.child(ref).child("data").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ride = snapshot.getValue(Rides.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.av_back:
                goBack();

                break;

            case R.id.av_accept:
                accept();
                break;

            case R.id.av_decline:
                decline();
                break;

            case R.id.av_message:
                openMesseges();
                break;

        }
    }

    private String canDrive(String s){
        if(s.equals("true")){
            return "is able to drive";
        }
        else {
            return "is unable to drive";
        }
    }

    private void goBack() {
        Intent i = new Intent(AppViewActivity.this, RideApplicantsActivity.class);
        i.putExtra("ref", ref);
        startActivity(i);
    }


    private void decline() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Rides").child(ref).child("applications").child(application.makeTitle());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Intent i = new Intent(AppViewActivity.this, RideApplicantsActivity.class);
        i.putExtra("ref", ref);
        startActivity(i);

    }


    private void openMesseges() {
        String s = "";
        String number;
        number = application.getPhonenumber();

        s += number.substring(1, 4);
        s += number.substring(6, 9);
        s += number.substring(10);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", s, null)));
    }


    private void accept() {

        ride.setParty2name(application.getName());
        ride.setParty2phonenumber(application.getPhonenumber());
        ride.setParty2id(application.getId());
        ride.setParty2address(application.getAddress());
        ride.setParty2major(application.getMajor());


        FirebaseDatabase.getInstance().getReference("YourRides")
                .child(ride.getParty1id())
                .child(ride.makeTitle())
                .setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query ridesQuery = ref.child("Rides").child(ride.makeTitle());

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

                startActivity(new Intent(AppViewActivity.this, AccountInfoActivity.class));

            }
        });


        reference.child(ride.getParty2id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseDatabase.getInstance().getReference("YourRides")
                        .child(ride.getParty2id())
                        .child(ride.makeTitle())
                        .setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(AppViewActivity.this, AccountInfoActivity.class));

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}







