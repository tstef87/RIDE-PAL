package com.example.ride_pal_real;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText fname, lname, em, pw;
    private ProgressBar pb;
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

        pb = findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account_head:
                startActivity(new Intent(this, SignInPage.class));
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

        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailaddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(firstname, lastname, emailaddress, password);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this,"user has been registered", Toast.LENGTH_LONG).show();

                                pb.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(RegisterUser.this, "no", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                startActivity(new Intent(RegisterUser.this, AccountInfoActivity.class));

            }
        });




    }


}