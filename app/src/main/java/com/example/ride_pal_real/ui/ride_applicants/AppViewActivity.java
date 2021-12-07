package com.example.ride_pal_real.ui.ride_applicants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.yourRidePals.YourRideView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name, pref, canDrive, message;
    Button back, accept, decline;
    String ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_view);

        name = (TextView) findViewById(R.id.av_name);
        pref = (TextView) findViewById(R.id.av_preff);
        canDrive = (TextView) findViewById(R.id.av_can_drive);
        message = (TextView) findViewById(R.id.av_message);

        back = (Button) findViewById(R.id.av_back);
        back.setOnClickListener(this);

        Bundle recdData = getIntent().getExtras();
        ref = recdData.getString("ref");

        Application application = new Application(recdData.getString("name"),
                recdData.getString("pref"),
                recdData.getString("id"),
                recdData.getString("phonenumber"),
                recdData.getString("canDrive"));



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.av_back:
                goBack();

                break;

            case R.id.av_accept:

                break;

            case R.id.av_decline:

                break;

            case R.id.av_message:

                break;

        }
    }

    private void goBack(){
        Intent i = new Intent(AppViewActivity.this, RideApplicantsActivity.class);
        i.putExtra("ref", ref);
        startActivity(i);
    }
}