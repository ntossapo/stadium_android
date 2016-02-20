package com.tossapon.stadiumfinder.GroupActivity.SettingsActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Services.LocationService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tossapon Nuanchuay on 20/2/2559.
 */
public class SettingsActivity extends AppCompatActivity{

    private static final String TAG = "SettingsActivity";
    @Bind(R.id.activity_settings_location)
    Switch locatingSwitch;
    @Bind(R.id.activity_settings_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        toolbar.setTitle("ตั้งค่า");
        setSupportActionBar(toolbar);

        locatingSwitch.setChecked(isMyServiceRunning(LocationService.class));
        locatingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent i = new Intent(SettingsActivity.this, LocationService
                        .class);
                if(isChecked) {
                    Log.d(TAG, "onCheckedChanged: service start");
                    startService(i);
                }else{
                    stopService(i);
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
