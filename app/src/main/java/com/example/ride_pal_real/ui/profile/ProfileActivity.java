package com.example.ride_pal_real.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.AccountInfoActivity;
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
//import com.google.firebase.storage.FileDownloadTask;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private StorageReference storageRefrence;

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    Button editProfile, back;
    TextView name, phonenumber, major, email;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.name);
        major = (TextView) findViewById(R.id.major);
        email = (TextView) findViewById(R.id.email);
        phonenumber = (TextView) findViewById(R.id.phone);

        editProfile = (Button) findViewById(R.id.edit_profile);
        back = (Button) findViewById(R.id.back);

        setTextViews();
        setProfilePicture();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, AccountInfoActivity.class);
                startActivity(i);


            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                i.putExtra("ref", userId);
                startActivity(i);
            }
        });

    }

    private void setTextViews(){

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                name.setText(user.getFullName());
                email.setText(user.getEmail());
                phonenumber.setText(user.getPhoneNumber());
                major.setText(user.getMajor());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setProfilePicture(){

        storageRefrence= FirebaseStorage.getInstance().getReference().child("picture/profile_pic.png");
        try {
            final File lofi= File.createTempFile("profile_pic","png");
            storageRefrence.getFile(lofi)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap btm= BitmapFactory.decodeFile(lofi.getAbsolutePath());
                            ImageView image = (ImageView) findViewById(R.id.img);


                            image.setImageBitmap(btm);

                            //((ImageView)findViewById(R.id.img)).setImageBitmap(btm);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}