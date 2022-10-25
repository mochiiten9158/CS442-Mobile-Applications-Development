package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WeekWeatherAdapter extends RecyclerView.Adapter <WeekWeatherViewHolder>{

    private static final String TAG = "DailyAdapter";
    private final List <Daily> weatherweekList;
    private final WeekWeather ww;

    WeekWeatherAdapter(List<Daily> wList, WeekWeather weekw) {
        this.weatherweekList = wList;
        ww = weekw;
    }

    @NonNull
    @Override
    public WeekWeatherViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weeklyweatherrowlist, parent, false);
        return new WeekWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekWeatherViewHolder holder, int position) {
        Daily weekweather = weatherweekList.get(position);

        holder.dateandtime.setText(weekweather.getDat());
        holder.minandmax.setText(weekweather.gettMax() + "°F / " + weekweather.gettMin() + "°F");
        holder.descriptionday.setText(weekweather.getWeatherDes());
        holder.precipitationday.setText("Precipitation:" + weekweather.getPrecip() +"%");
        holder.uviday.setText("UV index: " + weekweather.getUvidx());
        holder.morningt.setText(weatherweekList.get(0).getHourly().get(0).getTemp() + "°F");
        holder.afternoont.setText(weatherweekList.get(0).getHourly().get(13).getTemp() + "°F");
        holder.eveningt.setText(weatherweekList.get(0).getHourly().get(17).getTemp() + "°F");
        holder.nightt.setText(weatherweekList.get(0).getHourly().get(23).getTemp() + "°F");
        holder.image_view.setImageResource(getIcon(weekweather.getIconDaily()));
    }

    @Override
    public int getItemCount() {
        return weatherweekList.size();
    }

    private int getIcon(String icon) {
        icon = icon.replace("-", "_"); // Replace all dashes with underscores
        int iconID = ww.getResources().getIdentifier(icon, "drawable", ww.getPackageName());
        if (iconID == 0) {
            Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON " + icon);
        }
        return iconID;
    }
}