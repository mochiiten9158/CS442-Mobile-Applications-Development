package com.example.weatherapp;

public class Weather {

    private String loc;
    private String dateAndTime;
    private int temperature;
    private int feels;
    private String weatherAndClouds;
    private int cloudCover;
    private String windg;
    private String windSpeed;
    private String windDir;
    private int hum;
    private int uv;
    private int visible;
    private int mornT;
    private int afterT;
    private int evenT;
    private int nightT;
    private String rise;
    private String set;
    private String icon;

    Weather(String l, String dt, String t, String f, String wc, String cc, String wg, String ws, String wd, String h, String u, String v, String mt, String at, String et, String nt, String sr, String ss, String i){
        loc = l;
        dateAndTime = dt;
        temperature = (int) Math.round(Double.parseDouble(t));
        feels = (int) Math.round(Double.parseDouble(f));
        weatherAndClouds = wc;
        cloudCover = (int) Math.round(Double.parseDouble(cc));
        windg = wg;
        windSpeed = ws;
        windDir = wd;
        hum = (int) Math.round(Double.parseDouble(h));
        uv = (int) Math.round(Double.parseDouble(u));
        visible = (int) Math.round(Double.parseDouble(v));
        mornT = (int) Math.round(Double.parseDouble(mt));
        afterT = (int) Math.round(Double.parseDouble(at));
        evenT = (int) Math.round(Double.parseDouble(et));
        nightT = (int) Math.round(Double.parseDouble(nt));
        rise = sr;
        set = ss;
        icon = i;
    }

    public int getEvenT() {
        return evenT;
    }
    public int getFeels() {
        return feels;
    }
    public int getTemperature() {
        return temperature;
    }
    public int getHum() {
        return hum;
    }
    public int getAfterT() {
        return afterT;
    }
    public int getUv() {
        return uv;
    }
    public String getDateAndTime() {
        return dateAndTime;
    }
    public String getWeatherAndClouds() {
        return weatherAndClouds;
    }
    public String getLoc() {
        return loc;
    }
    public int getMornT() {
        return mornT;
    }
    public int getNightT() {
        return nightT;
    }
    public int getVisible() {
        return visible;
    }
    public String getRise() {
        return rise;
    }
    public String getSet() {
        return set;
    }
    public String getIcon() {
        return icon;
    }
    public String getWindDir() {
        return windDir;
    }
    public String getWindg() {
        return windg;
    }
    public String getWindSpeed() {
        return windSpeed;
    }
    public int getCloudCover() {
        return cloudCover;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }
    public void setWindg(String windg) {
        this.windg = windg;
    }
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
    public void setAfterT(int afterT) {
        this.afterT = afterT;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
    public void setEvenT(int evenT) {
        this.evenT = evenT;
    }
    public void setFeels(int feels) {
        this.feels = feels;
    }
    public void setHum(int hum) {
        this.hum = hum;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    public void setMornT(int mornT) {
        this.mornT = mornT;
    }
    public void setNightT(int nightT) {
        this.nightT = nightT;
    }
    public void setSet(String set) {
        this.set = set;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public void setUv(int uv) {
        this.uv = uv;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    public void setWeatherAndClouds(String weatherAndClouds) {
        this.weatherAndClouds = weatherAndClouds;
    }
    public void setRise(String rise) {
        this.rise = rise;
    }
    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }
}