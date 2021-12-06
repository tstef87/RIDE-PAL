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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        FirebaseDatabase.getInstance().getReference().child("Rides")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Rides ride = new Rides();

                            ride.setDestination(snapshot.child("destination").getValue().toString());
                            ride.setParty1name(snapshot.child("party1name").getValue().toString());
                            ride.setParty2name("-");
                            ride.setParty1phonenumber(snapshot.child("party1phonenumber").getValue().toString());
                            ride.setParty2phonenumber("-");
                            ride.setTime(snapshot.child("time").getValue().toString());

                            if(snapshot.child("monday").getValue().toString().equals("true")){
                                ride.setMonday(true);
                            }else{
                                ride.setMonday(false);
                            }

                            if(snapshot.child("tuesday").getValue().toString().equals("true")){
                                ride.setTuesday(true);
                            }else{
                                ride.setTuesday(false);
                            }

                            if(snapshot.child("wednesday").getValue().toString().equals("true")){
                                ride.setWednesday(true);
                            }else{
                                ride.setWednesday(false);
                            }

                            if(snapshot.child("thursday").getValue().toString().equals("true")){
                                ride.setThursday(true);
                            }else{
                                ride.setThursday(false);
                            }

                            if(snapshot.child("friday").getValue().toString().equals("true")){
                                ride.setFriday(true);
                            }else{
                                ride.setFriday(false);
                            }
                            ride.setParty1id(snapshot.child("party1id").getValue().toString());
                            ride.setParty2id(snapshot.child("party2id").getValue().toString());


                            data.add(ride.toStringList());
                            dataRides.add(ride);
                        }

                        listView = (ListView) view.findViewById(R.id.listview);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);


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


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToNewActivity(AccountInfoActivity.class);
            }
        });

        createNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToNewActivity(CreateRide.class);

            }
        });

        return view;

    }

    @Override
    public void onStart(){
        super.onStart();

    }


    private void moveToNewActivity (Class c){

        Intent i = new Intent(getActivity(), c);
        startActivity(i);
        ((Activity)getActivity()).overridePendingTransition(0,0);
    }

}