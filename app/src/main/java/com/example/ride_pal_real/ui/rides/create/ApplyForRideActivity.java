package com.example.ride_pal_real.ui.rides.create;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.logout.AccountSettings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ApplyForRideActivity extends AppCompatActivity {


    CheckBox yes, no;
    Button apply, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_ride);

        back = (Button) findViewById(R.id.afr_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplyForRideActivity.this, AccountInfoActivity.class));
            }
        });

        yes = findViewById(R.id.afr_yes);
        no = findViewById(R.id.afr_no);

        apply = (Button) findViewById(R.id.afr_apply);

    }

    public void applyForRide() {

        if (validYesOrNo(yes.isChecked(), no.isChecked())){

        }
    }

    public boolean validYesOrNo(boolean yes, boolean no){
        if (yes && no){
            return false;
        }
        else if (!yes && !no){
            return false;
        }
        else{
            return true;
        }
    }
}
