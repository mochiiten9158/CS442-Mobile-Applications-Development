package com.example.weatherapp;

import java.util.ArrayList;

public class Daily {

    private String date;
    private long dd;
    private int tmax;
    private int tmin;
    private int precip;
    private int uvidx;
    private String weatherdes;
    private String iconDaily;
    private ArrayList <Hourly> hourly = new ArrayList<>();

    Daily(String dt, long d, String max, String min, String p, String uv, String wd, String i, ArrayList <Hourly> h){
        date = dt;
        dd = d;
        tmax = (int) Math.round(Double.parseDouble(max));
        tmin = (int) Math.round(Double.parseDouble(min));
        precip = (int) Math.round(Double.parseDouble(p));
        uvidx = (int) Math.round(Double.parseDouble(uv));
        weatherdes = wd;
        iconDaily = i;
        hourly = h;
    }

    public void setDat(String dat) {
        this.date = dat;
    }
    public void settMax(int tMax) {
        this.tmax = tMax;
    }
    public void settMin(int tMin) {
        this.tmin = tMin;
    }
    public void setPrecip(int precip) {
        this.precip = precip;
    }
    public void setUvidx(int uvidx) {
        this.uvidx = uvidx;
    }
    public void setWeatherDes(String weatherDes) {
        this.weatherdes = weatherDes;
    }
    public void setIconDaily(String iconDaily) {
        this.iconDaily = iconDaily;
    }
    public void setHourly(ArrayList<Hourly> hourly) {
        this.hourly = hourly;
    }

    public String getDat() {
        return date;
    }
    public int gettMax() {
        return tmax;
    }
    public int gettMin() {
        return tmin;
    }
    public int getPrecip() {
        return precip;
    }
    public int getUvidx() {
        return uvidx;
    }
    public String getWeatherDes() {
        return weatherdes;
    }
    public String getIconDaily() {
        return iconDaily;
    }
    public ArrayList <Hourly> getHourly() {
        return hourly;
    }
}
