package com.example.churdlab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.prefs.PreferenceChangeEvent;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class MainActivity extends AppCompatActivity implements Observer {

    private static final Context SETTINGS = null;
    static TextView mTv;
    private Observable myObservable;
    private static Boolean militaryHourFormat = false;
    private static Boolean smoothHand = false;
    private static String clockType = "";
    private static String intervalSpeed = "0";
    private static Boolean isPaused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.digitalTextView);
        myObservable = Analog.AnalogObserver.getInstance();
        myObservable.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onPause(){
        super.onPause();
        isPaused = true;
    }
    @Override
    public void onStop(){
        super.onStop();
        isPaused = true;
    }
    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(myPreferences.contains("24hour_checkbox")){
            militaryHourFormat = myPreferences.getBoolean("24hour_checkbox", false);
        }
        if(myPreferences.contains("Smooth_checkbox")){
            smoothHand = myPreferences.getBoolean("Smooth_checkbox", true);
        }
        if(myPreferences.contains("ClockFaceNumerals")){
            clockType = myPreferences.getString("ClockFaceNumerals", "");
        }
        if(myPreferences.contains("updateInterval")){
            intervalSpeed = myPreferences.getString("ClockFaceNumerals", "0");
        }

    }
    @Override
    public void update(Observable o, Object arg) {
        Analog.AnalogObserver myObserver = (Analog.AnalogObserver) o;
        mTv.setText(myObserver.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Toast.makeText(this, "Lab 7, Spring 2020, Chaz C Hurd", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
 }
    public boolean getHourFormat()
    {
        return this.militaryHourFormat;
    }
    public boolean getSmoothHand(){
        return this.smoothHand;
    }
    public String getClockType(){ return this.clockType; }
    public String getIntervalSpeed(){
        return this.intervalSpeed;
    }
    public boolean getPaused(){ return isPaused;}
}
