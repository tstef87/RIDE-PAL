package com.example.ride_pal_real.ui.ride_applicants;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RidesPostedListFragment extends Fragment {

    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<Rides> dataRides = new ArrayList<Rides>();
    ListView listView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rides_posted_list, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseDatabase.getInstance().getReference().child("Rides")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Rides ride = new Rides();

                            ride.setDestination(snapshot.child("data").child("destination").getValue().toString());
                            ride.setParty1name(snapshot.child("data").child("party1name").getValue().toString());
                            ride.setParty1address(snapshot.child("data").child("party1address").getValue().toString());
                            ride.setParty2address("-");
                            ride.setParty2name("-");
                            ride.setParty1phonenumber(snapshot.child("data").child("party1phonenumber").getValue().toString());
                            ride.setParty2phonenumber("-");
                            ride.setTime(snapshot.child("data").child("time").getValue().toString());

                            if (snapshot.child("data").child("monday").getValue().toString().equals("true")) {
                                ride.setMonday(true);
                            } else {
                                ride.setMonday(false);
                            }

                            if (snapshot.child("data").child("tuesday").getValue().toString().equals("true")) {
                                ride.setTuesday(true);
                            } else {
                                ride.setTuesday(false);
                            }

                            if (snapshot.child("data").child("wednesday").getValue().toString().equals("true")) {
                                ride.setWednesday(true);
                            } else {
                                ride.setWednesday(false);
                            }

                            if (snapshot.child("data").child("thursday").getValue().toString().equals("true")) {
                                ride.setThursday(true);
                            } else {
                                ride.setThursday(false);
                            }

                            if (snapshot.child("data").child("friday").getValue().toString().equals("true")) {
                                ride.setFriday(true);
                            } else {
                                ride.setFriday(false);
                            }
                            ride.setParty1id(snapshot.child("data").child("party1id").getValue().toString());
                            ride.setParty2id(snapshot.child("data").child("party2id").getValue().toString());
                            ride.setParty1address(snapshot.child("data").child("party1address").getValue().toString());
                            ride.setParty2address(snapshot.child("data").child("party2address").getValue().toString());


                            if(userId.equals(ride.getParty1id())) {
                                data.add(ride.toStringList());
                                dataRides.add(ride);
                            }
                        }

                        listView = (ListView) view.findViewById(R.id.listviewYRP);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Rides r = dataRides.get(position);
                                Intent i = new Intent(getActivity() , RideApplicantsActivity.class);
                                i.putExtra("ref", r.makeTitle());

                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }
}