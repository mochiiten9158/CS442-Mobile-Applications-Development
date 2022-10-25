package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WeekWeatherViewHolder extends RecyclerView.ViewHolder {
    public TextView dateandtime;
    public TextView minandmax;
    public TextView descriptionday;
    public TextView precipitationday;
    public TextView uviday;
    public TextView morningt;
    public TextView afternoont;
    public TextView eveningt;
    public TextView nightt;
    public ImageView image_view;

    public WeekWeatherViewHolder(View v) {
        super(v);
        dateandtime = v.findViewById(R.id.dat);
        minandmax = v.findViewById(R.id.tempMinandMax);
        descriptionday = v.findViewById(R.id.descriptionday);
        precipitationday = v.findViewById(R.id.precipitation);
        uviday = v.findViewById(R.id.UVI);
        morningt = v.findViewById(R.id.morningt);
        afternoont = v.findViewById(R.id.afternoont);
        eveningt = v.findViewById(R.id.eveningt);
        nightt = v.findViewById(R.id.nightt);
        image_view = v.findViewById(R.id.iconweather);
    }
}