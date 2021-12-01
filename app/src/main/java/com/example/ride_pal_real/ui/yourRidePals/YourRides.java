package com.example.ride_pal_real.ui.yourRidePals;

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

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.rides.create.Rides;
import com.example.ride_pal_real.ui.rides.ridedis.RideDiscription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class YourRides extends Fragment {

    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<Rides> dataRides = new ArrayList<Rides>();

    FirebaseUser user;
    String userID;

    ListView listView;
    DatabaseReference referenceUser;
    DatabaseReference referenceRide;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.fragment_your_rides, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseDatabase.getInstance().getReference().child("YourRides")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Rides ride = new Rides();

                            ride.setDestination(snapshot.child("destination").getValue().toString());
                            ride.setName(snapshot.child("name").getValue().toString());
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

                            data.add(ride.toStringList());
                            dataRides.add(ride);


                        }


                        listView = (ListView) view.findViewById(R.id.listviewYR);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Rides r = dataRides.get(position);

                                Intent i = new Intent(getActivity() , YourRideView.class);
                                i.putExtra("name", r.getName());
                                i.putExtra("destination", r.getDestination());
                                i.putExtra("time", r.getTime());
                                i.putExtra("name", r.getName());
                                i.putExtra("monday", r.isMonday());
                                i.putExtra("tuesday", r.isTuesday());
                                i.putExtra("wednesday", r.isWednesday());
                                i.putExtra("thursday", r.isThursday());
                                i.putExtra("friday", r.isFriday());
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


