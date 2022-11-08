package com.example.civiladvocacyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "About_Activity";

    private TextView titleView;
    private TextView copyrightView;
    private TextView versionView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        titleView = findViewById(R.id.titleID);
        copyrightView = findViewById(R.id.copyrightID);
        versionView = findViewById(R.id.versionID);

        titleView.setText("Know Your Government");
        copyrightView.setText("© 2022, Shambhawi Sharma");
        versionView.setText("Version 1.0");

        TextView googleApi = (TextView)findViewById(R.id.googleApi);
        SpannableString content = new SpannableString(googleApi.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        googleApi.setText(content);
    }

    public void doApiLink(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/civic-information/"));
        startActivity(intent);
    }
}
