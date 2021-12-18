package com.example.ride_pal_real.ui.rides.create;

import android.app.Activity;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.AccountInfoActivity;
import com.example.ride_pal_real.ui.map.MapsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class ListFragment extends Fragment {

    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<Rides> dataRides = new ArrayList<Rides>();

    ListView listView;
    SearchView searchView;


    //displays a list of all the rides posted
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        FirebaseDatabase.getInstance().getReference().child("Rides")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //adds all the rides to a list view
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Rides ride = snapshot.child("data").getValue(Rides.class);

                            data.add(ride.toStringList());
                            dataRides.add(ride);
                        }

                        listView = (ListView) view.findViewById(R.id.listview);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);

                        //makes the rides clickable
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Rides r = dataRides.get(position);

                                Intent i = new Intent(getActivity() , RideDiscription.class);

                                i.putExtra("destination", r.getDestination());
                                i.putExtra("time", r.getTime());
                                i.putExtra("monday", r.isMonday());
                                i.putExtra("tuesday", r.isTuesday());
                                i.putExtra("wednesday", r.isWednesday());
                                i.putExtra("thursday", r.isThursday());
                                i.putExtra("friday", r.isFriday());
                                i.putExtra("party1id", r.getParty1id());
                                i.putExtra("party2id", r.getParty2id());
                                i.putExtra("party1name", r.getParty1name());
                                i.putExtra("party2name", r.getParty2name());
                                i.putExtra("party1phonenumber", r.getParty1phonenumber());
                                i.putExtra("party2phonenumber", r.getParty2phonenumber());
                                i.putExtra("party1address", r.getParty1address());
                                i.putExtra("party2address", r.getParty2address());
                                i.putExtra("party1major", r.getParty1major());
                                i.putExtra("party2major", r.getParty2major());

                                startActivity(i);

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FloatingActionButton refresh = (FloatingActionButton) view.findViewById(R.id.refresh);
        FloatingActionButton createNewRide = (FloatingActionButton) view.findViewById(R.id.create_new_ride_fab);


        //refreshs the page
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToNewActivity(AccountInfoActivity.class);
            }
        });

        //button brings you to map activity and lets you pick your address
        createNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MapsActivity.class);
                i.putExtra("intent", "CreateRide");
                startActivity(i);

            }
        });

        return view;

    }

    @Override
    public void onStart(){
        super.onStart();

    }

    //allows you to move to a new activity
    private void moveToNewActivity (Class c){

        Intent i = new Intent(getActivity(), c);
        startActivity(i);
        ((Activity)getActivity()).overridePendingTransition(0,0);
    }

}