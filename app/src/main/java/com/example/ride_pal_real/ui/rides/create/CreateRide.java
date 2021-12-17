package com.example.ride_pal_real.ui.rides.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.map.MapsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class CreateRide extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    CheckBox mday, tuday, wday, thday, fday;
    TextView time, address;
    Button createRide, back;
    Spinner spinner;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private User userProfile;
    private String spinnerResult, party1name, party2name, party1phonenumber, party2phonenumber, party1major, party2major;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        mAuth = FirebaseAuth.getInstance();
        mday = findViewById(R.id.monday_cb);
        tuday = findViewById(R.id.tuesday_cb);
        wday = findViewById(R.id.wednesday_cb);
        thday = findViewById(R.id.thursday_cb);
        fday = findViewById(R.id.firday_cb);
        time = findViewById(R.id.editTextTime);



        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parking_lots, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        createRide = (Button) findViewById(R.id.create_ride_button);
        back = (Button) findViewById(R.id.back_button_create_ride);


        address = (TextView) findViewById(R.id.cr_address);
        Bundle recdData = getIntent().getExtras();
        address.setText(recdData.getString("addressString"));

        createRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRide();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateRide.this, AccountInfoActivity.class));
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        spinnerResult = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    private boolean tof(CheckBox x){

        return x.isChecked();
    }


    private void createRide(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Rides");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   userProfile = snapshot.getValue(User.class);
                   party1name = userProfile.getFullName();
                   party2name = "-";
                   party1phonenumber = userProfile.getPhoneNumber();
                   party2phonenumber = "-";
                   party1major = userProfile.getMajor();
                   party2major = "-";

                   String t = time.getText().toString().trim();
                   String dest = spinnerResult;
                   boolean tue = tof(tuday);
                   boolean mond = tof(mday);
                   boolean wed = tof(wday);
                   boolean thu = tof(thday);
                   boolean fri = tof(fday);


                   if (t.isEmpty()){
                       time.setError("time can not be empty");
                       time.requestFocus();
                       return;
                   }

                   if (!checkTime(t)){
                       time.setError("time must be formated like- 3:00pm");
                       time.requestFocus();
                       return;
                   }

                   if(!checkAtLeastOneDay(mond, tue, wed, thu, fri)){
                       time.setError("At least One Day Must be Selected");
                       time.requestFocus();
                       return;
                   }


                   Bundle recdData = getIntent().getExtras();


                   if (recdData == null) {
                       address.setError("Please Select an Address");
                       address.requestFocus();
                       return;
                   }

                   address.setText(recdData.getString("addressString"));
                   String party1address = recdData.getString("addressLngLat");

                   if (party1address.isEmpty()){
                       address.setError("Invalid Address");
                       address.requestFocus();
                       return;
                   }




                   Rides rides = new Rides(t, dest, mond, tue, wed, thu, fri, user.getUid(), "-",
                           party1name, party2name, party1phonenumber, party2phonenumber, party1address,
                           "-", party1major, party2major);

                   FirebaseDatabase.getInstance().getReference("Rides")
                           .child(rides.makeTitle()).child("data")
                           .setValue(rides).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {

                           startActivity(new Intent(CreateRide.this, AccountInfoActivity.class));
                       }
                   });




               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

    }
    private boolean checkTime(String time){
        if (time.length() > 3) {
            if (time.substring(time.length() - 2).toLowerCase(Locale.ROOT).equals("am") || time.substring(time.length() - 2, time.length()).toLowerCase(Locale.ROOT).equals("pm")) {
                if (Integer.parseInt(time.substring(time.length() - 4, time.length() - 2)) <= 60) {
                    if (time.charAt(time.length() - 5) == ':') {
                        if (Integer.parseInt(time.substring(0, time.length() - 5)) <= 12) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkAtLeastOneDay(boolean m, boolean t, boolean w, boolean th, boolean f){
        if(m || t || w || th || f){
            return true;
        }
        return false;
    }


}