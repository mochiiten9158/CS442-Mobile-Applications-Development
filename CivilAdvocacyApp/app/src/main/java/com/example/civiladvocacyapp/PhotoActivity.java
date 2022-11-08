package com.example.civiladvocacyapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {
    private TextView location_d;
    private TextView office_d;
    private TextView name_d;
    private ImageView off_photo;
    private ImageView p_photo;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        location_d = findViewById(R.id.location_d);

        office_d = findViewById(R.id.office_d);
        name_d = findViewById(R.id.name_d);
        p_photo = findViewById(R.id.p_photo);
        off_photo = findViewById(R.id.off_photo);
        layout = findViewById(R.id.layout);
        int red = Color.parseColor("#FF0000");
        int blue = Color.parseColor("#0000FF");
        int black = Color.parseColor("#000000");

        Intent intent = getIntent();
        if(intent.hasExtra(Official.class.getName())){
            final Official official = (Official) intent.getSerializableExtra(Official.class.getName());
            location_d.setText(MainActivity.locationtodisplay);
            office_d.setText(official.getOffice());
            name_d.setText(official.getName());
            if(this.hasNetworkConnection()) {
                loadRemoteImage(official.getPhotoUrl(), off_photo);
            }else{
                off_photo.setImageResource(R.drawable.brokenimage);
            }
            if(official.getParty().equals("Republican Party")){
                p_photo.setImageResource(R.drawable.rep_logo);
                layout.setBackgroundColor(red);
            }
            else if(official.getParty().equals("Democratic Party")){
                p_photo.setImageResource(R.drawable.dem_logo);
                layout.setBackgroundColor(blue);
            }
            else{
                p_photo.setVisibility(View.GONE);
                layout.setBackgroundColor(black);
            }
        }
    }
    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void loadRemoteImage(String imageURL, ImageView image) {
        if(imageURL.equals("missing")){
            image.setImageResource(R.drawable.missing);
        }
        else {
            Picasso.get().load(imageURL)
                    .into(image);
        }
    }
}