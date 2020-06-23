package com.example.churdlab7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;



public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String FIRST_USE = "FIRST_USE";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences preferences = getPreferenceScreen().getSharedPreferences();
        //first time using the app?


        //listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        // calling onSharedChanged to force a value to appear that is in a database
        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), "updateInterval");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        switch (key){
            case "updateInterval":
                int interval = Integer.parseInt(sharedPreferences.getString(key,"100"));
                if(interval <100) interval = 100;
                if(interval >5000) interval = 5000;
                sharedPreferences.edit().putString(key,Integer.toString(interval)).commit();
                pref.setSummary("Time interval = " + interval);
                ((EditTextPreference)pref).setText(Integer.toString(interval));
                break;
        }
    }
}