package com.example.ride_pal_real.ui.logout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.SignInPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountSettings extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    private Button logout, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_page);

        logout = (Button) findViewById(R.id.signOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //logs the user out and brings them back to the sign in screen
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountSettings.this, SignInPage.class));
            }
        });

        back = (Button) findViewById(R.id.back);

        //bring user back to the home page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountSettings.this, AccountInfoActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();



    }
}