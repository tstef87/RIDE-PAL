package com.example.ride_pal_real.ui.ride_applicants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RideApplicantsActivity extends AppCompatActivity {


    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<Application> dataApp = new ArrayList<Application>();
    ListView listView;
    FloatingActionButton refresh, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_applicants);

        Bundle recdData = getIntent().getExtras();
        String ref = recdData.getString("ref");

        refresh = (FloatingActionButton) findViewById(R.id.ra_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RideApplicantsActivity.this, RideApplicantsActivity.class);
                i.putExtra("ref", ref);
                startActivity(i);
            }
        });

        back = (FloatingActionButton) findViewById(R.id.ra_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RideApplicantsActivity.this, AccountInfoActivity.class));
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();



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
                            application.setCanDrive(dataSnapshot.child("canDrive").getValue().toString());
                            application.setAddress(dataSnapshot.child("address").getValue().toString());

                            dataApp.add(application);
                            data.add(application.makeTitle());

                        }


                        listView = (ListView) findViewById(R.id.ya_list_view);
                        adapter = new ArrayAdapter<String>(RideApplicantsActivity.this, android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        TextView emptyText = (TextView) findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Application application = dataApp.get(position);

                                Intent i = new Intent(RideApplicantsActivity.this , AppViewActivity.class);
                                i.putExtra("name", application.getName());
                                i.putExtra("canDrive", application.getCanDrive());
                                i.putExtra("pref", application.getPreferences());
                                i.putExtra("id", application.getId());
                                i.putExtra("phonenumber", application.getPhonenumber());
                                i.putExtra("ref", ref);
                                i.putExtra("address", application.getAddress());

                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}