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
import android.widget.TextView;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.sign_in.User;
import com.example.ride_pal_real.ui.rides.create.Rides;
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

    //displays a list of your rides
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

                        //making the list of your rides
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Rides ride = snapshot.getValue(Rides.class);

                            data.add(ride.toStringListYR(userId));
                            dataRides.add(ride);


                        }


                        listView = (ListView) view.findViewById(R.id.listviewYR);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView.setAdapter(adapter);


                        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
                        listView.setEmptyView(emptyText);

                        //makes items in list clickable
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Rides r = dataRides.get(position);
                                String user = r.getParty1name();

                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        User u = snapshot.getValue(User.class);

                                        Intent i = new Intent(getActivity() , YourRideView.class);
                                        i.putExtra("party1name", r.getParty1name());
                                        i.putExtra("party2name", r.getParty2name());
                                        i.putExtra("destination", r.getDestination());
                                        i.putExtra("time", r.getTime());
                                        i.putExtra("monday", r.isMonday());
                                        i.putExtra("tuesday", r.isTuesday());
                                        i.putExtra("wednesday", r.isWednesday());
                                        i.putExtra("thursday", r.isThursday());
                                        i.putExtra("friday", r.isFriday());
                                        i.putExtra("party1id", r.getParty1id());
                                        i.putExtra("party2id", r.getParty2id());
                                        i.putExtra("party1phonenumber", r.getParty1phonenumber());
                                        i.putExtra("party2phonenumber", r.getParty2phonenumber());
                                        i.putExtra("party1address", r.getParty1address());
                                        i.putExtra("party2address", r.getParty2address());
                                        i.putExtra("party1major", r.getParty1major());
                                        i.putExtra("party2major", r.getParty2major());
                                        startActivity(i);



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


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


