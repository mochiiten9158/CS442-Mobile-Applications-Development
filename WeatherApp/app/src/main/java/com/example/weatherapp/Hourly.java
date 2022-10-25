package com.example.weatherapp;

public class Hourly {
    private String dateTime;
    private long dd;
    private int temp;
    private String condition;
    private String icon;

    Hourly(String d, long dayepoch, String t, String c, String i){
        dateTime = d;
        dd = dayepoch;
        temp = (int) Math.round(Double.parseDouble(t));
        condition = c;
        icon = i;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setTemp(int temp) {
        this.temp = temp;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public void setDd(Long d){
        this.dd = d;
    }

    public String getDateTime() {
        return dateTime;
    }
    public int getTemp() {
        return temp;
    }
    public String getCondition() {
        return condition;
    }
    public String getIcon() {
        return icon;
    }
    public long getDd(){
        return dd;
    }
}