package com.example.ride_pal_real.ui.rides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.ride_pal_real.R;
import com.example.ride_pal_real.ui.rides.create.Rides;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Rides> {





    public ListAdapter(@NonNull Context context, ArrayList<Rides> ridesArrayList) {
        super(context, R.layout.list_item, ridesArrayList);
    }



    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        Rides ride = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        return super.getView(position, convertView, parent);


    }



}
