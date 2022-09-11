/* Name : Shambhawi Sharma
*  UID : A20459117
*  Date : 09/11/2022
*  CS442 Lab 1 TipSplit */

package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText ti; //total initial aka bill total with tax
    private EditText count; //total number of people
    float total; //common variable for all methods that calculates total bill with tip
    int t; //common variable to store tip percent
    private TextView tip; //will contain tip amount
    private TextView tf; //will contain total bill with tip
    private TextView finalSplit; //will contain per person amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ti = findViewById(R.id.Totali); //Total initial bill including tax excluding tip
        count = findViewById(R.id.totalPeople); //receives number of people who are splitting the bill
        tip = findViewById(R.id.tipAmount); //displays tip amount in the text view
        tf = findViewById(R.id.totalWithTip); //displays total final aka total with tip
        finalSplit = findViewById(R.id.totalPerPerson); //displays total per person
    }

    public void doTotal(View v){
        //get total bill with tax
        String tiStr = ti.getText().toString();
        //check if empty, if yes, then clear any radio buttons selected and return
        if(tiStr.isEmpty()) {
            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
            rg.clearCheck();
            return;
        }

        //convert initial bill to float from string
        float tiVal = Float.parseFloat(tiStr);
        //check if it is negative, if yes, then return
        if (tiVal < 0)
            return;

        //to find the tip percent using radio buttons
        String tipChoice;
        if(v.getId() == R.id.tip_twelve){
            t = 12;
        }else if(v.getId() == R.id.tip_fifteen){
            t = 15;
        }else if(v.getId() == R.id.tip_eighteen){
            t = 18;
        }else if(v.getId() == R.id.tip_twenty){
            t = 20;
        }

        //find total tip amount and display
        float tipAm = t * tiVal / 100;
        tip.setText("$" + String.format("%.2f", tipAm));

        //find total bill with tip and display
        total = tiVal + tipAm;
        tf.setText("$" + String.format("%.2f", total));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //method to save instance state to preserve values on rotation
        outState.putString("h1", tip.getText().toString());
        outState.putString("h2", tf.getText().toString());
        outState.putFloat("value", total);
        outState.putString("h3", finalSplit.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //method to restore previously saved instance states to preserve values on rotation
        super.onRestoreInstanceState(savedInstanceState);

        total = savedInstanceState.getFloat("value");
        String h3text = "";
        if(savedInstanceState.containsKey("h3"))
            h3text = savedInstanceState.getString("h3");
        finalSplit.setText(h3text);
        tip.setText(savedInstanceState.getString("h1"));
        tf.setText(savedInstanceState.getString("h2"));
    }

    public void doSplit(View totalPeople){
        //get total number of people that are splitting the bill
        String countStr = count.getText().toString();

        //check if empty, if yes, then return
        if(countStr.isEmpty())
            return;

        //convert the number of people to float so as to get a decimal number during calculation
        float countVal = Float.parseFloat(countStr);

        //check if negative, if yes, then return
        if (countVal <= 0 )
            return;

        //calculate total per person and display
        float amountPerPerson = total/countVal;
        finalSplit.setText("$" + String.format("%.2f",amountPerPerson));
    }

    public void doClear(View Totali){
        //method to clear all fields upon pressing the clear button
        ti.setText("");
        count.setText("");
        tf.setText("");
        tip.setText("");
        finalSplit.setText("");
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radioGroup);
        radiogroup.clearCheck();
    }
}