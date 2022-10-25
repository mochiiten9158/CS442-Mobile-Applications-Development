/* Shambhawi Sharma
*  A20459117
*  10/25/2022*/

package com.example.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String location = "Chicago, IL";
    private boolean fahrenheit = true;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView; // Layout's recyclerview
    private WeatherAdapter wAdapter; // Data to recyclerview adapter
    private SwipeRefreshLayout swiperRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(location);
        checkNet(null);

        recyclerView = findViewById(R.id.recycler);
        wAdapter = new WeatherAdapter(get48Hours(WeatherDownloader.getWeatherDays()), this);
        recyclerView.setAdapter(wAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void checkNet(View v) {
        TextView dateAndTime = findViewById(R.id.dateTime);
        if (hasNetworkConnection()){
            doDownload(location);
        }
        else
            dateAndTime.setText(R.string.no_net);
    }

    private void doRefresh(){
        if(hasNetworkConnection()){
            doDownload(location);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
        swiperRefresh.setRefreshing(false);
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void doDownload(String city) {
        if (city == null) {
            return;
        } else {
            city = city.replace(", ", ",");
            WeatherDownloader.downloadWeather(this, city, fahrenheit);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toggleUnits) {
            if (fahrenheit) {
                fahrenheit = false;
                item.setIcon(R.drawable.units_c);
            } else {
                fahrenheit = true;
                item.setIcon(R.drawable.units_f);
            }
            doDownload(getSupportActionBar().getTitle().toString());
            return true;
        } else if (item.getItemId() == R.id.showDaily) {
            Intent intent = new Intent(this, WeekWeather.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.changeLocation) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            EditText loc = new EditText(this);
            loc.setInputType(InputType.TYPE_CLASS_TEXT);
            loc.setGravity(Gravity.CENTER_HORIZONTAL);
            builder.setView(loc);

            builder.setPositiveButton("OK", (dialog, id) -> doDownload(loc.getText().toString()));

            builder.setNegativeButton("Cancel", (dialog, id) -> doDownload(null));

            builder.setMessage("For US locations, enter as 'City', or 'City,State' \n \n For international locations enter as 'City,Country'");
            builder.setTitle("Enter a location:");

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void updateData(Weather weather) {
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }

        getSupportActionBar().setTitle(weather.getLoc());
        saveLocation();

        TextView dateAndTime = findViewById(R.id.dateTime);
        dateAndTime.setText(weather.getDateAndTime());

        TextView temperature = findViewById(R.id.temperature_today);
        temperature.setText(weather.getTemperature() + "°" + (fahrenheit ? "F" : "C"));

        TextView feels = findViewById(R.id.feelsLike);
        feels.setText("Feels like " + weather.getFeels() + "°" + (fahrenheit ? "F" : "C"));

        TextView weatherAndClouds = findViewById(R.id.description);
        weatherAndClouds.setText(weather.getWeatherAndClouds() + " (" + weather.getCloudCover() + "% clouds)");

        TextView wind = findViewById(R.id.wind);
        wind.setText("Winds: " + getDirection(Double.parseDouble(weather.getWindDir())) + " at " + weather.getWindSpeed() + (fahrenheit ? " mph gusting to " : " kmph gusting to ") + weather.getWindg() + (fahrenheit ? " mph" : " kmph"));

        TextView hum = findViewById(R.id.humidity);
        hum.setText("Humidity: " + weather.getHum() + "%");

        TextView uv = findViewById(R.id.uvindex);
        uv.setText("UV Index: " + weather.getUv());

        TextView visible = findViewById(R.id.visib);
        visible.setText("Visibility: " + weather.getVisible() + (fahrenheit ? "mi" : "km"));

        TextView mornT = findViewById(R.id.tempM);
        if (fahrenheit == false){
            mornT.setText(ftoc(weather.getMornT()) + "°" + (fahrenheit ? "F" : "C"));
        } else
            mornT.setText(weather.getMornT() + "°" + (fahrenheit ? "F" : "C"));

        TextView afterT = findViewById(R.id.tempA);
        if (fahrenheit == false){
            afterT.setText(ftoc(weather.getAfterT()) + "°" + (fahrenheit ? "F" : "C"));
        } else
            afterT.setText(weather.getAfterT() + "°" + (fahrenheit ? "F" : "C"));

        TextView evenT = findViewById(R.id.tempE);
        if (fahrenheit == false){
            evenT.setText(ftoc(weather.getEvenT()) + "°" + (fahrenheit ? "F" : "C"));
        } else
            evenT.setText(weather.getEvenT() + "°" + (fahrenheit ? "F" : "C"));

        TextView nightT = findViewById(R.id.tempN);
        if (fahrenheit == false){
            nightT.setText(ftoc(weather.getNightT()) + "°" + (fahrenheit ? "F" : "C"));
        } else
            nightT.setText(weather.getNightT() + "°" + (fahrenheit ? "F" : "C"));

        TextView rise = findViewById(R.id.sunrise);
        rise.setText("Sunrise: " + weather.getRise());

        TextView set = findViewById(R.id.sunset);
        set.setText("Sunset: " + weather.getSet());

        ImageView i = findViewById(R.id.icon);
        i.setImageResource(getIconstring(weather.getIcon()));
    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }

    int getIconstring(String icon) {
        icon = icon.replace("-", "_"); // Replace all dashes with underscores
        int iconID = this.getResources().getIdentifier(icon, "drawable", this.getPackageName());
        if (iconID == 0) {
            Log.d(TAG, "parseCurrentRecord: CANNOT FIND ICON " + icon);
        }
        return iconID;
    }

    public boolean isFahrenheit() {
        return fahrenheit;
    }

    public int ftoc(int t) {
        double f = (double) t;
        double c = (((f - 32) * 5) / 9);
        int cel = (int) c;
        return cel;
    }

    public ArrayList<Hourly> get48Hours(ArrayList<Daily> days) {
        ArrayList<Hourly> hours = new ArrayList<>();
        if (days.size() > 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 24; j++) {
                    if (i == 0) {
                        if (Integer.parseInt(days.get(i).getHourly().get(j).getDateTime()) > (int) WeatherDownloader.getDateTimeEpoch()) {
                            hours.add(days.get(i).getHourly().get(j));
                        }
                    } else {
                        hours.add(days.get(i).getHourly().get(j));
                    }
                }
            }
            return hours;
        } else {
            return null;
        }
    }

    public void loadJSON() {
        try {
            InputStream inputs = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(inputs, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = stdin.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            if (jsonObject != null){
                location = jsonObject.getString("location");
                fahrenheit = jsonObject.getBoolean("units");
            } else {
                location = "Chicago, IL";
                fahrenheit = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLocation() {
        String output = toJSON(location, fahrenheit);
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("Location.json", Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(output);
            printWriter.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toJSON(String location, Boolean f) {
        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsw = new JsonWriter(sw);
            jsw.setIndent(" ");

            jsw.beginObject();
            if (location != null) {
                jsw.name("location").value(location);
                jsw.name("units").value(f);
            } else {
                jsw.name("location").value("Chicago, IL");
                jsw.name("units").value(true);
            }
            jsw.endObject();
            jsw.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onStop(){
        saveLocation();
        super.onStop();
    }
}