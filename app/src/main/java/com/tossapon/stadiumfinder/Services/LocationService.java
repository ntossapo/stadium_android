package com.tossapon.stadiumfinder.Services;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.App.LatLngModule;
import com.tossapon.stadiumfinder.App.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benvo_000 on 20/2/2559.
 */
public class LocationService extends Service implements LocationListener {

    private static final String TAG = "LocationService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private LocationManager locationManager;
    private String provider;
    private Location location;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            LatLngModule.newInstance(location.getLatitude(), location.getLongitude());
            JSONObject json = new JSONObject();
            try {
                json.put("user", AppUser.getInstance().getFacebook_id());
                json.put("lat", location.getLatitude());
                json.put("lng", location.getLongitude());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketIO.getInstance().emit("location", json.toString());
        }
        Log.d(TAG, "onHandleIntent: start");
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLngModule.newInstance(location.getLatitude(), location.getLongitude());
        JSONObject json = new JSONObject();
        try {
            json.put("user", AppUser.getInstance().getFacebook_id());
            json.put("lat", location.getLatitude());
            json.put("lng", location.getLongitude());
        } catch (JSONException e) {
            Log.d(TAG, "onLocationChanged: ");
        }
        SocketIO.getInstance().emit("location", json.toString());
        Log.d(TAG, location.getLatitude() + ","+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
