package com.example.ride_pal_real.ui.ride_applicants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RideApplicantsActivity extends AppCompatActivity {


    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<Application> dataApp = new ArrayList<Application>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_applicants);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        Bundle recdData = getIntent().getExtras();

        String ref = recdData.getString("ref");

        FirebaseDatabase.getInstance().getReference().child("Rides").child(ref).child("applications")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Application application = new Application();
                            application.setPreferences(dataSnapshot.child("preferences").getValue().toString());
                            application.setPhonenumber(dataSnapshot.child("phonenumber").getValue().toString());
                            application.setId(dataSnapshot.child("id").getValue().toString());
                            application.setName(dataSnapshot.child("name").getValue().toString());
                            application.setCanDrive2(dataSnapshot.child("canDrive").getValue().toString());

                            dataApp.add(application);
                            data.add(application.makeTitle());

                        }


                        listView = (ListView) findViewById(R.id.ya_list_view);
                        adapter = new ArrayAdapter<String>(RideApplicantsActivity.this, android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        TextView emptyText = (TextView) findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}