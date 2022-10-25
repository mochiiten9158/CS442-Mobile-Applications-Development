package com.example.weatherapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeekWeather extends AppCompatActivity{
    private RecyclerView rv;
    private WeekWeatherAdapter wwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeklyweatherrecycler);

        rv = findViewById(R.id.recyclerweekly);
        wwa = new WeekWeatherAdapter(WeatherDownloader.getWeatherDays(),this);
        rv.setAdapter(wwa);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}