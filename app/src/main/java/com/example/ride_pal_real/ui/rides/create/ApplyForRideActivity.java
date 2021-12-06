package com.example.ride_pal_real.ui.rides.create;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.logout.AccountSettings;
import com.example.ride_pal_real.ui.ride_applicants.Application;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ApplyForRideActivity extends AppCompatActivity {


    CheckBox yes, no;
    Button apply, back;
    EditText prefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_ride);

        back = (Button) findViewById(R.id.afr_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplyForRideActivity.this, AccountInfoActivity.class));
            }
        });

        yes = findViewById(R.id.afr_yes);
        no = findViewById(R.id.afr_no);

        apply = (Button) findViewById(R.id.afr_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyForRide();
            }
        });
    }

    public void applyForRide() {

        yes = findViewById(R.id.afr_yes);
        no = findViewById(R.id.afr_no);


        Application application = new Application();

        if (validYesOrNo(yes, no)) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    Bundle recdData = getIntent().getExtras();

                    prefrences = (EditText) findViewById(R.id.afr_preferences);
                    application.setCanDrive(yes.isChecked());
                    application.setId(userId);
                    application.setName(userProfile.getFullName());
                    application.setPhonenumber(userProfile.getPhoneNumber());
                    application.setPrefrences(prefrences.getText().toString());

                    FirebaseDatabase.getInstance().getReference("Rides")
                            .child(recdData.getString("RideName"))
                            .child("applications")
                            .child(application.makeTitle())
                            .setValue(application).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ApplyForRideActivity.this, "Applied for Ride!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ApplyForRideActivity.this, AccountInfoActivity.class));
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }


    }

    public boolean validYesOrNo(CheckBox yes, CheckBox no){
        if (yes.isChecked() && no.isChecked()){
            Toast.makeText(ApplyForRideActivity.this, "Can Not Select Yes and No", Toast.LENGTH_LONG).show();

            return false;
        }
        else if (!yes.isChecked() && !no.isChecked()){
            Toast.makeText(ApplyForRideActivity.this, "Must Select Either Yes Or No", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }
}
