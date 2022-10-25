package com.example.weatherapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherDownloader {

    private static final String TAG = "WeatherDownloader";
    public static MainActivity mainActivity;
    public static RequestQueue queue;
    public static Weather weatherObj;
    private static ArrayList<Daily> weatherDays = new ArrayList<>();
    static long d;


    private static final String WEATHERURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private static final String APIKEY = "DWYK6MVZ8KNDJFNWXE7W529JF";

    public static void downloadWeather(MainActivity mainActivityIn, String city, boolean fahrenheit) {

        mainActivity = mainActivityIn;

        queue = Volley.newRequestQueue(mainActivity);
        String weatherURL2 = WEATHERURL + city;
        Uri.Builder buildURL = Uri.parse(weatherURL2).buildUpon();
        buildURL.appendQueryParameter("unitGroup", (fahrenheit ? "us" : "metric"));
        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("key", APIKEY);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener = response -> parseJSON(response.toString());

        Response.ErrorListener error = error1 -> mainActivity.updateData(null);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);

        queue.add(jsonObjectRequest);
    }

    private static void parseJSON(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);
            String location = jObjMain.getString("address");

            // Current Conditions
            JSONObject curr = jObjMain.getJSONObject("currentConditions");
            long d = curr.getLong("datetimeEpoch");
            String dateAndTime = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault()).format(new Date((curr.getLong("datetimeEpoch")) * 1000));
            String temp = curr.getString("temp");
            String feelslike = curr.getString("feelslike");
            String humidity = curr.getString("humidity");
            String windgust = curr.getString("windgust");
            String windspeed = curr.getString("windspeed");
            String winddir = curr.getString("winddir");
            String visibility = curr.getString("visibility");
            String cloudcover = curr.getString("cloudcover");
            String uvindex = curr.getString("uvindex");
            String conditions = curr.getString("conditions");
            String sunrise = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date((curr.getLong("sunriseEpoch")) * 1000));
            String sunset = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date((curr.getLong("sunsetEpoch")) * 1000));
            String icon = curr.getString("icon");


            // 15-Day Forecast
            JSONArray days = jObjMain.getJSONArray("days");
            if (days != null) {
                for (int i = 0; i < days.length(); i++) {
                    JSONObject day = days.getJSONObject(i);
                    long dayd = day.getLong("datetimeEpoch");
                    String datetimeEpoch = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault()).format(new Date((day.getLong("datetimeEpoch")) * 1000));
                    String tempmax = day.getString("tempmax");
                    String tempmin = day.getString("tempmin");
                    String precipitation = day.getString("precipprob");
                    String uvidx = day.getString("uvindex");
                    String description = day.getString("description");
                    String iconDaily = day.getString("icon");

                    // hourly forecast
                    ArrayList<Hourly> weatherHours = new ArrayList<>();
                    if (i == 0) {
                        JSONArray hours = day.getJSONArray("hours");
                        if (hours != null) {
                            for (int j = 0; j < hours.length(); j++) {
                                JSONObject hour = hours.getJSONObject(j);
                                long dd = hour.getLong("datetimeEpoch");
                                String datetime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date((hour.getInt("datetimeEpoch")) * 1000));
                                String temperature = hour.getString("temp");
                                String weatherconditions = hour.getString("conditions");
                                String iconHourly = hour.getString("icon");
                                weatherHours.add(new Hourly(datetime, dd, temperature, weatherconditions, iconHourly));
                            }
                        }
                    }
                    weatherDays.add(new Daily(datetimeEpoch, dayd, tempmax, tempmin, precipitation, uvidx, description, iconDaily, weatherHours));
                }
            }
            String mornT = Integer.toString(weatherDays.get(0).getHourly().get(0).getTemp());
            String afterT = Integer.toString(weatherDays.get(0).getHourly().get(13).getTemp());
            String evenT = Integer.toString(weatherDays.get(0).getHourly().get(17).getTemp());
            String nightT = Integer.toString(weatherDays.get(0).getHourly().get(23).getTemp());

            weatherObj = new Weather(location, dateAndTime, temp, feelslike, conditions, cloudcover, windgust, windspeed, winddir, humidity, uvindex, visibility, mornT, afterT, evenT, nightT, sunrise, sunset, icon);
            mainActivity.updateData(weatherObj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getIcon(String icon) {
        icon = icon.replace("-", "_"); // Replace all dashes with underscores
        int iconID = mainActivity.getResources().getIdentifier(icon, "drawable", mainActivity.getPackageName());
        if (iconID == 0) {
            Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON " + icon);
        }
    }

    public static ArrayList<Daily> getWeatherDays() {
        return weatherDays;
    }

    public static long getDateTimeEpoch(){
        return d;
    }
}
