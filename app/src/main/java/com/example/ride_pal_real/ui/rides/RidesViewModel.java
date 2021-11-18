package com.example.ride_pal_real.ui.rides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RidesViewModel extends ViewModel {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;


    private MutableLiveData<String> mText;

    public RidesViewModel() {
        mText = new MutableLiveData<>();



        mText.setValue("this is the Rides fragment");






    }

    public LiveData<String> getText() {
        return mText;
    }
}