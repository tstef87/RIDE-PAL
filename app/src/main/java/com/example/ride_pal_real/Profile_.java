package com.example.ride_pal_real;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Profile_ extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView imgFavorite;
    EditText ed1,ed2,ed3,ed4,ed5,ed6;
    private Button update,save;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;


    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         imgFavorite = (ImageView) findViewById(R.id.iv_cp);
        ed1 = (EditText) findViewById(R.id.name);
        ed2 = (EditText) findViewById(R.id.profession);
        ed3 = (EditText) findViewById(R.id.bio);
        ed4 = (EditText) findViewById(R.id.email);
        ed5 = (EditText) findViewById(R.id.ph);
        ed6 = (EditText) findViewById(R.id.ad);
        save=(Button)findViewById(R.id.btn_save);
        update=(Button) findViewById(R.id.btn_update);
        databaseReference =FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseDatabase=FirebaseDatabase.getInstance();



        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_.this,
                        "Select an Image",
                        Toast.LENGTH_LONG).show();
                openGallery();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_.this,"  Data Updated",Toast.LENGTH_LONG);

                Intent i = new Intent(getApplicationContext(), Profile_.class);
                startActivity(i);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=ed1.getText().toString();
                String pro=ed2.getText().toString();
                String bio=ed3.getText().toString();
                String mail=ed4.getText().toString();
                String ph=ed5.getText().toString();
                String ad=ed6.getText().toString();
                Map<String,Object> hashMap=new HashMap<>();





                hashMap.put("Name",name);
                hashMap.put("Profession",pro);
                hashMap.put("Bio",bio);
                hashMap.put("Email",mail);
                hashMap.put("Phone",ph);
                hashMap.put("Address",ad);
                databaseReference.child("profile").push().setValue(hashMap).addOnCompleteListener(task ->
                        {
                            Toast.makeText(Profile_.this,"  Data Saved",Toast.LENGTH_LONG);
                            Intent i = new Intent(getApplicationContext(), show_profile.class);
                            startActivity(i);

                            if(task.isSuccessful()){
                                Toast.makeText(Profile_.this,"Data is inserted ",Toast.LENGTH_LONG);


                            }
                            else{
                                Toast.makeText(Profile_.this," Error",Toast.LENGTH_LONG);

                            }
                        }


                        );





            }
        });


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