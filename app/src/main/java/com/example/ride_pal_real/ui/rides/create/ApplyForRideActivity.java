package com.example.ride_pal_real.ui.rides.create;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
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
    TextView address;

    //makes the apply for ride page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_ride);

        Bundle recdDate = getIntent().getExtras();

        address = (findViewById(R.id.afr_address));
        address.setText(recdDate.getString("addressString"));

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

    //puts the ride application into the database under the posted ride
    public void applyForRide() {

        yes = findViewById(R.id.afr_yes);
        no = findViewById(R.id.afr_no);
        address = findViewById(R.id.afr_address);


        Application application = new Application();

        if (validYesOrNo(yes, no)) {
            if (validAddress(address.getText().toString())) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);
                        Bundle recdData = getIntent().getExtras();

                        if(yes.isChecked()){
                            application.setCanDrive("yes");
                        }
                        else if(no.isChecked()){
                            application.setCanDrive("no");
                        }

                        prefrences = (EditText) findViewById(R.id.afr_preferences);
                        application.setId(userId);
                        application.setName(userProfile.getFullName());
                        application.setPhonenumber(userProfile.getPhoneNumber());
                        application.setPreferences(prefrences.getText().toString());
                        application.setAddress(recdData.getString("addressLngLat"));
                        application.setMajor(userProfile.getMajor());


                        String pref = prefrences.getText().toString();


                        if (pref.isEmpty()){
                            prefrences.setError("Must Not Be Empty");
                            prefrences.requestFocus();
                            return;
                        }



                        FirebaseDatabase.getInstance().getReference("Rides")
                                .child(recdData.getString("RideName"))
                                .child("applications")
                                .child(userId)
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


    }

    //checks if check box is check either yes or no not both or neither
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

    //chekcs if address is valid
    public boolean validAddress(String s){
        if (s  == null){
            Toast.makeText(ApplyForRideActivity.this, "Invalid Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
