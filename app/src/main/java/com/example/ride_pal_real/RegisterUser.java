package com.example.ride_pal_real;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText fname, lname, em, pw;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner =  findViewById(R.id.create_account_head);
        banner.setOnClickListener(this);

        registerUser =  findViewById(R.id.create_button);
        registerUser.setOnClickListener(this);

        fname = findViewById(R.id.firstName);
        lname =  findViewById(R.id.lastName);
        em = findViewById(R.id.sjuEmail);
        pw =  findViewById(R.id.password);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account_head:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.create_button:
                register();
                break;
        }
    }

    private void register(){

        String firstname = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String emailaddress = em.getText().toString().trim();
        String password = pw.getText().toString().trim();

        if (firstname.isEmpty()){
            fname.setError("First Name can not be Empty");
            fname.requestFocus();
            return;
        }
        if (lastname.isEmpty()){
            lname.setError("Last Name can not be Empty");
            lname.requestFocus();
            return;
        }

        if (emailaddress.isEmpty()){
            em.setError("Email can not be Empty");
            em.requestFocus();
            return;
        }

        if (!emailaddress.substring(emailaddress.length() - 8, emailaddress.length()).equals("@sju.edu")){
            em.setError("must be SJU email");
            em.requestFocus();
            return;
        }

        if (password.length() < 6){
            pw.setError("password must be more then 6 characters");
            pw.requestFocus();
            return;
        }


        startActivity(new Intent(this, Main.class));
    }


}