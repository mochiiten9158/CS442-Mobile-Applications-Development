/* Shambhawi Sharma
*  A20459117
*  11/8/2021
* */

package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rView;
    private static OfficialAdapter oAdapter;
    private static ArrayList<Official> oList = new ArrayList<>();

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int LOCATION_REQUEST = 111;

    public static String locationZipCode = "000000";
    public static String locationdataforrotate = "";
    public static String locationtodisplay = "";

    private static TextView loc;
    public static String locations = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc = findViewById(R.id.locationID);

        if (locations.equals("")) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            determineLocation();
        } else {
            doDownload(locations);
        }

        rView = findViewById(R.id.recycler);
        oAdapter = new OfficialAdapter(oList, this);
        rView.setAdapter(oAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasNetworkConnection()) {
            if (locations.equals("Unspecified Location")) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);    //device location
                determineLocation();
            } else {
                doDownload(locations);
            }
        }
        else {
            loc.setText("No Data For Location");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Data cannot be accessed/loaded without an internet connection.");
            builder.setTitle("No Network Connection");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void doDownload(String location) {
        if (hasNetworkConnection()) {
            if (location.equals("") || location == null) {

            }
            else {
                locations = location;
                OfficialLoader.downloadOfficials(this, location);
            }
        }
        else {
            loc.setText("No Data For Location");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Data cannot be accessed/loaded without an internet connection.");
            builder.setTitle("No Network Connection");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public static void updateData(String l, ArrayList<Official> o) {
        loc.setText(l);
        oList.clear();
        oList.addAll(o);
        oAdapter.notifyItemRangeChanged(0, oList.size());
    }

    private void noInternetResponse(){
        ((TextView) findViewById(R.id.location)).setText("No Data For Location");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Data cannot be accessed/loaded without Internet connections.");
        builder.setTitle("No Network Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
        return;
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void determineLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        locations = getPlace(location);
                        loc.setText(locations);
                        doDownload(locations);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determineLocation();
                } else {
                    loc.setText(R.string.deniedText);
                }
            }
        }
    }

    private String getPlace(Location loc) {
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s, %s",
                    city, state));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getZipCode(Location loc) {
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String zipcode = addresses.get(0).getPostalCode();
            String line1 [] = addresses.get(0).getAddressLine(0).split(", ");
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s",
                    zipcode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getZipCodeLatLon(double lat,double lon) {
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            String zipcode = addresses.get(0).getPostalCode();

            sb.append(String.format(
                    Locale.getDefault(),
                    "%s",
                    zipcode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String doLocationName(String defaultLocation) {
        Geocoder gc = new Geocoder(this);
        try {
            List<Address> address =
                    gc.getFromLocationName(defaultLocation, 1);

            if (address == null || address.isEmpty()) {
                return null;
            }
            String cnty = address.get(0).getCountryCode();

            if (cnty == null) {
                return null;
            }
            Address addressItem = address.get(0);
            String fname;
            String aarea;
            if (cnty.equals("US")) {
                fname = addressItem.getFeatureName();
                aarea = addressItem.getAdminArea();
            } else {
                fname = addressItem.getLocality();
                if (fname == null)
                    fname = addressItem.getFeatureName();
                aarea = addressItem.getCountryName();
            }
            if (fname == null || fname.isEmpty()) return null;
            if (aarea == null || aarea.isEmpty()) return null;

            double longi = address.get(0).getLongitude();
            double lati = address.get(0).getLatitude();
            String rtimeLoc=getZipCodeLatLon(lati,longi);
            return rtimeLoc;
        } catch (IOException e) {
            return null;
        }
    }

    public void locationEntered(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String user_input = et.getText().toString();
                if(!hasNetworkConnection()){
                    Toast.makeText(getApplicationContext(), "Functionality not available without network", Toast.LENGTH_SHORT).show();
                    noInternetResponse();
                    return;
                }
                locationZipCode=doLocationName(user_input);
                if(locationZipCode == null){
                    Toast.makeText(getApplicationContext(), "Invalid Location Entered", Toast.LENGTH_SHORT).show();
                    return;
                }
                doDownload(locations);
            }
        });
        builder.setTitle("Enter Address");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.location){
            locationEntered();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int pos = rView.getChildLayoutPosition(view);
        Official official = oList.get(pos);
        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra(Official.class.getName(), official);
        startActivity(intent);
    }

    public void getLocationFromVolley(String locres) {
        if(locres == null || locres.equals("")){
            ((TextView) findViewById(R.id.location)).setText("No Data For Location");
        }
        else{
            locationtodisplay = locres;
            ((TextView) findViewById(R.id.location)).setText(locationtodisplay);
        }
    }

    public void downloadFailed(){
        Toast.makeText(this, "Error occured during downloading data from the Api", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("HISTORY", locationZipCode);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);
        locationdataforrotate = savedInstanceState.getString("HISTORY");
        doDownload(locationdataforrotate);
    }
}