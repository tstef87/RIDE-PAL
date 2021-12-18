package com.example.ride_pal_real.sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInPage extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgetPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgetPassword = (TextView) findViewById(R.id.forgotPassword);
        forgetPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                //brings you to the create account page
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.signIn:
                //brings you to the app
                userLogin();
                break;

            case R.id.forgotPassword:
                //brings you to the forgot password page
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }
    }


    //checks to see if user is in the database and if they are authorised or not
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String pw = editTextPassword.getText().toString().trim();

        //conditions to see if EditText inputs are formatted correctly
        if(email.isEmpty()){
            editTextEmail.setError("Email can not be empty");
            editTextEmail.requestFocus();
            return;
        }

        if (!email.substring(email.length() - 8, email.length()).equals("@sju.edu")){
            editTextEmail.setError("must be SJU email");
            editTextEmail.requestFocus();
            return;
        }

        if (pw.length() < 6){
            editTextPassword.setError("password must be more then 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //signs the user in
        mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    if(user.isEmailVerified()) {
                        startActivity(new Intent(SignInPage.this, AccountInfoActivity.class));
                    }else{
                        //if user is not authenticated it sends then another emil
                        user.sendEmailVerification();
                        Toast.makeText(SignInPage.this, "check email for varification", Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

                //if user in not in database or email and password is wrong
                else{
                    Toast.makeText(SignInPage.this,"email or password in invalid", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}