package com.example.ride_pal_real.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryViewModel extends ViewModel {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;


    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        mText.setValue(user.getEmail());






    }

    public LiveData<String> getText() {
        return mText;
    }
}