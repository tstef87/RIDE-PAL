package com.example.ride_pal_real.ui.yourRidePals;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.net.URL;

public class YourRideView extends AppCompatActivity implements View.OnClickListener {

    Button back, delete;
    TextView name, time, days, message, getdirections, addtocalender;
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
                recdData.getString("party2phonenumber"));

        name = (TextView) findViewById(R.id.yrp_name);
        time = (TextView) findViewById(R.id.yrp_time);
        days = (TextView) findViewById(R.id.yrp_days_of_week);

        if (userId.equals(ride.getParty1id())){
            name.setText(ride.getParty2name());
        }
        else{
            name.setText(ride.getParty1name());
        }

        time.setText(ride.getTime());
        days.setText(ride.makeDOW());

        delete = (Button) findViewById(R.id.yrp_delete_ride);
        delete.setOnClickListener(this);
        back = (Button) findViewById(R.id.yrp_back);
        back.setOnClickListener(this);

        name = (TextView) findViewById(R.id.yrp_name);
        time = (TextView) findViewById(R.id.yrp_time);
        days = (TextView) findViewById(R.id.yrp_days_of_week);

        message = (TextView) findViewById(R.id.yrp_message);
        message.setOnClickListener(this);

        getdirections = (TextView) findViewById(R.id.yrp_get_directions);
        getdirections.setOnClickListener(this);

        addtocalender = (TextView) findViewById(R.id.yrp_add_to_calendar);
        addtocalender.setClickable(true);


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

            case R.id.yrp_add_to_calendar:
                break;

            case R.id.yrp_get_directions:
                openMap();

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

    private void openMap(){
        //String url = "https://www.google.com/maps/dir/39.9998976,-75.235328/40.0017991,-75.2267659/5600+City+Ave,+Philadelphia,+PA+19131/@39.9982745,-75.2370576,16z/am=t/data=!3m1!4b1!4m11!4m10!1m1!4e1!1m0!1m5!1m1!1s0x89c6c74f28c1c21b:0xf933bbdca5b6d622!2m2!1d-75.2388361!2d39.9947363!3e2";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/39.9998976,-75.235328/40.0017991,-75.2267659/5600+City+Ave,+Philadelphia,+PA+19131/@39.9982745,-75.2370576,16z/am=t/data=!3m1!4b1!4m11!4m10!1m1!4e1!1m0!1m5!1m1!1s0x89c6c74f28c1c21b:0xf933bbdca5b6d622!2m2!1d-75.2388361!2d39.9947363!3e2"));
        startActivity(intent);
    }
}