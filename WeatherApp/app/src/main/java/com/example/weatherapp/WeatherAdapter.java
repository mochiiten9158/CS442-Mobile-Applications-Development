package com.example.weatherapp;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter <WeatherViewHolder> {
    private List <Hourly> weatherhourList;
    private MainActivity mainActivity;

    public WeatherAdapter(List <Hourly> nl, MainActivity ma){
        this.weatherhourList = nl;
        mainActivity = ma;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherlistrow, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position){
        Hourly H = weatherhourList.get(position);

        holder.datetime.setText(H.getDateTime());
        holder.des.setText(H.getCondition());
        holder.temperature_hourly.setText(H.getTemp());
        holder.image.setImageResource(getIcon(H.getIcon()));
    }

    @Override
    public int getItemCount(){
        if (weatherhourList == null){
            return 0;
        }
        else{
            return weatherhourList.size();
        }
    }

    private int getIcon(String icon) {
        icon = icon.replace("-", "_"); // Replace all dashes with underscores
        int iconID = mainActivity.getResources().getIdentifier(icon, "drawable", mainActivity.getPackageName());
        if (iconID == 0) {
            Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON " + icon);
        }
        return iconID;
    }
}