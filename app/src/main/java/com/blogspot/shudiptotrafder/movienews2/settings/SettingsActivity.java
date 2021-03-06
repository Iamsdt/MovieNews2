package com.blogspot.shudiptotrafder.movienews2.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import com.blogspot.shudiptotrafder.movienews2.R;
import com.blogspot.shudiptotrafder.movienews2.Utilities.Utility;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNightMode();

        setContentView(R.layout.activity_settings);

        //is switch preference changed then recreate activity
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                //Toast.makeText(SettingActivity.this, "change deced", Toast.LENGTH_SHORT).show();
                if (key.equals(getString(R.string.switchKey))) {
                    recreate();
                }
            }
        });

        if ( getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //buy calling android.R.id.home

        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
        }


        return true;
    }

    //set night mode
    private void setNightMode() {

        boolean isEnabled = Utility.getNightModeEnabled(this);

        if (isEnabled) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
