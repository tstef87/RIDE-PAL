package com.example.ride_pal_real.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.ride_applicants.Application;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.example.ride_pal_real.ui.yourRidePals.YourRideView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView imgFavorite;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceUsers;
    DatabaseReference databaseReferenceRides;
    DatabaseReference databaseReferenceYourRides;

    EditText fname, lname, major, email, phonenumber;
    Button update,save;
    String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle recdData = getIntent().getExtras();

        imgFavorite = (ImageView) findViewById(R.id.iv_cp);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        major  = (EditText) findViewById(R.id.up_major);
        email = (EditText) findViewById(R.id.email);
        phonenumber = (EditText) findViewById(R.id.ph);

        save=(Button)findViewById(R.id.btn_save);
        update=(Button) findViewById(R.id.btn_update);

        ref = recdData.getString("ref");
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(ref);
        databaseReferenceRides = firebaseDatabase.getInstance().getReference()
                .child("Rides");
        databaseReferenceYourRides = FirebaseDatabase.getInstance().getReference()
                .child("YourRides");

        setEditText();
        setPP();



        firebaseDatabase=FirebaseDatabase.getInstance();


        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileActivity.this,
                        "Select an Image",
                        Toast.LENGTH_LONG).show();
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Toast.makeText(EditProfileActivity.this , "updated!", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });



    }

    private void setEditText(){

        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user != null) {
                    fname.setText(user.getFirstname());
                    lname.setText(user.getLastname());
                    phonenumber.setText(user.getPhoneNumber());
                    major.setText(user.getMajor());
                }

                else{
                    Toast.makeText(EditProfileActivity.this, "User is Empty", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveData(){

        if(!checkPhoneNumber(phonenumber.getText().toString())){
            phonenumber.setError("Phone Number Must be Formatted Like- (555)-555-5555");
            phonenumber.requestFocus();
            return;
        }
        if(fname.getText().toString().isEmpty()){
            fname.setError("First Name Can Not be Empty");
            fname.requestFocus();
            return;
        }
        if(lname.getText().toString().isEmpty()){
            lname.setError("Last Name Can Not be Empty");
            lname.requestFocus();
            return;
        }
        if(major.getText().toString().isEmpty()){

            major.setError("Major Can Not be Empty");
            major.requestFocus();
            return;
        }

        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);


                //user.setEmail(email.getText().toString());
                user.setPhoneNumber(phonenumber.getText().toString());
                user.setFirstname(fname.getText().toString());
                user.setLastname(lname.getText().toString());
                user.setMajor(major.getText().toString());



                databaseReferenceUsers.setValue(user);


                databaseReferenceRides.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {




                            Rides ride = dataSnapshot.child("data").getValue(Rides.class);

                            if (ride.getParty1id().equals(ref)){

                                ride.setParty1phonenumber(phonenumber.getText().toString());
                                ride.setParty1name(user.getFullName());
                                ride.setParty1major(user.getMajor());
                                databaseReferenceRides.child(ride.makeTitle()).child("data").setValue(ride);



                            }

                            Application application = dataSnapshot.child("applications").child(ref).getValue(Application.class);
                            if(application != null) {

                                application.setName(user.getFullName());
                                application.setPhonenumber(phonenumber.getText().toString());
                                application.setMajor(user.getMajor());
                                databaseReferenceRides.child(ride.makeTitle()).child("applications").child(application.getId()).setValue(application);



                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReferenceYourRides.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(ref)) {
                            for (DataSnapshot dataSnapshot : snapshot.child(ref).getChildren()) {

                                Rides ride = dataSnapshot.getValue(Rides.class);

                                if(ride.getParty1id().equals(ref)) {
                                    ride.setParty1phonenumber(phonenumber.getText().toString());
                                    ride.setParty1name(user.getFullName());
                                    databaseReferenceYourRides.child(ref).child(ride.makeTitle()).setValue(ride);
                                    databaseReferenceYourRides.child(ride.getParty2id()).child(ride.makeTitle()).setValue(ride);
                                }
                                else if(ride.getParty2id().equals(ref)) {

                                    ride.setParty2phonenumber(phonenumber.getText().toString());
                                    ride.setParty2name(user.getFullName());
                                    databaseReferenceYourRides.child(ref).child(ride.makeTitle()).setValue(ride);
                                    databaseReferenceYourRides.child(ride.getParty1id()).child(ride.makeTitle()).setValue(ride);
                                }



                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private boolean checkPhoneNumber(String pn){

        if(pn.charAt(0) == '('){
            if(pn.charAt(4) == ')'){
                if(pn.charAt(5) == '-'){
                    if(pn.charAt(9) == '-'){
                        if(pn.length() == 14){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imgFavorite.setImageURI(imageUri);

            FirebaseStorage storageReference = FirebaseStorage.getInstance();
            String filename = ref+".jpg";
            storageReference.getReference("/profilePics/"+filename).putFile(imageUri);

        }
    }

    private void setPP(){


        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        ImageView img = (ImageView) findViewById(R.id.iv_cp);

        storageRef.child("profilePics/"+ref+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Picasso.with(EditProfileActivity.this).load(uri).into(img);
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });



    }





}