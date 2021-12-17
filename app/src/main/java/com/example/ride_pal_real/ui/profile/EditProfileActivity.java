package com.example.ride_pal_real.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        String ref = recdData.getString("ref");
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(ref);
        databaseReferenceRides = firebaseDatabase.getInstance().getReference()
                .child("Rides");
        databaseReferenceYourRides = FirebaseDatabase.getInstance().getReference()
                .child("YourRides").child(ref);
        setEditText();



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
                Toast.makeText(EditProfileActivity.this , "yayaya", Toast.LENGTH_SHORT).show();
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
                    email.setText(user.getEmail());
                    phonenumber.setText(user.getPhoneNumber());
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
        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);


                user.setEmail(email.getText().toString());
                //user.setPhoneNumber(phonenumber.getText().toString());
                user.setFirstname(fname.getText().toString());
                user.setLastname(lname.getText().toString());

                if(!checkPhoneNumber(user.getPhoneNumber())){
                    phonenumber.setError("Phone Number Must be Formatted Like- (555)-555-5555");
                    phonenumber.requestFocus();
                    return;
                }

                databaseReferenceUsers.setValue(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceRides.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
        }
    }


}