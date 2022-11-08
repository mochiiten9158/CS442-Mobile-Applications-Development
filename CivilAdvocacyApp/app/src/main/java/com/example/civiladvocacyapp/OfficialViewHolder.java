package com.example.civiladvocacyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OfficialViewHolder extends RecyclerView.ViewHolder {

    public TextView office;
    public TextView name;
    public ImageView i;

    public OfficialViewHolder(View view){
        super(view);
        office = view.findViewById(R.id.officePost);
        name = view.findViewById(R.id.nameParty);
        i = view.findViewById(R.id.official_image);
    }
}
