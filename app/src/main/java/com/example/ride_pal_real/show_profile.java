package com.example.ride_pal_real;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class show_profile extends AppCompatActivity {


    TextView t1,t2,t3,t4,t5,t6;
    private StorageReference storageRefrence;
    Button clk;
    ProgressBar progressBar;
    Uri uri;
    UploadTask uploadTask;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    DocumentReference  documentReference;
    private static final int pkg_img=1;
    User_Member user_member;
    String currentuserid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        user_member=new User_Member();
        t1 = (TextView) findViewById(R.id.name);
        t2 = (TextView) findViewById(R.id.profession);
        t3 = (TextView) findViewById(R.id.bio);
        t4 = (TextView) findViewById(R.id.email);
        t5 = (TextView) findViewById(R.id.phone);
        t6 = (TextView) findViewById(R.id.ad);
        clk = (Button) findViewById(R.id.btn);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentuserid=firebaseUser.getUid();
        documentReference=firebaseFirestore.collection("user").document(currentuserid);
        storageRefrence=FirebaseStorage.getInstance().getReference("profile images");
        databaseReference=firebaseDatabase.getReference("All users");

        databaseReference2 =FirebaseDatabase.getInstance().getReference().child("Users").child("profile");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data=snapshot.child("Name").getValue().toString();
                    String data1=snapshot.child("Profession").getValue().toString();
                    String data2=snapshot.child("Bio").getValue().toString();
                    String data3=snapshot.child("Email").getValue().toString();
                    String data4=snapshot.child("Phone").getValue().toString();
                    String data5=snapshot.child("Address").getValue().toString();
                    t1.setText(data);
                    t2.setText(data1);
                    t3.setText(data2);
                    t4.setText(data3);
                    t5.setText(data4);
                    t6.setText(data5);


                }
                else{
                    Intent i = new Intent(getApplicationContext(), Profile_.class);
                    startActivity(i);

                    Toast.makeText(show_profile.this," No Data Found",Toast.LENGTH_LONG);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








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


        clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // Intent intt=new Intent(this,Profile_.class);
                Intent i = new Intent(v.getContext(), Profile_.class);
                startActivity(i);


            }
        });







    }
}