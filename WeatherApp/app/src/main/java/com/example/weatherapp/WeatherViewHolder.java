package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WeatherViewHolder extends RecyclerView.ViewHolder {
    public TextView datetime;
    public TextView t;
    public TextView temperature_hourly;
    public TextView des;
    public ImageView image;

    public WeatherViewHolder(View v) {
        super(v);
        datetime = v.findViewById(R.id.dte);
        temperature_hourly = v.findViewById(R.id.temperature);
        des = v.findViewById(R.id.description_row);
        image = v.findViewById(R.id.icon_list_row);
    }
}