package com.ingreatsol.calibrador.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class LocationViewModel extends AndroidViewModel implements LocationListener {

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    private String provider;

    private final MutableLiveData<Location> location; // location

    // The minimum distance to change Updates in meters
    private float minDistanceChangeForUpdates = 0.5f; // 10 meters

    // The minimum time between updates in milliseconds
    private long minTimeBetweenUpdates = 500; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;


    public LocationViewModel(@NonNull Application application) {
        super(application);
        this.locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        this.location = new MutableLiveData<>();
        this.checkProviderLocation();
    }

    public LiveData<Location> getLocation() {
        return location;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @SuppressLint("MissingPermission")
    public void checkProviderLocation(){
        if (this.verifyNetworkStatusDisabled()) {
            this.showSettingsAlert();
            return;
        }

        if (this.isNetworkEnabled){
            this.provider = LocationManager.NETWORK_PROVIDER;
            location.setValue(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            if (location.getValue() != null)
                return;
        }

        if (this.isGPSEnabled){
            this.provider = LocationManager.GPS_PROVIDER;
            location.setValue(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplication());

        // Setting Dialog Title
        alertDialog.setTitle("Configuración de GPS");

        // Setting Dialog Message
        alertDialog.setMessage("GPS no está habilitado. Quieres ir al menu de ajustes?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings
                        .ACTION_LOCATION_SOURCE_SETTINGS);
                getApplication().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void startUsingGPS() {
        if (this.verifyNetworkStatusDisabled()) {
            this.showSettingsAlert();
            return;
        }

        this.canGetLocation = true;

        if (this.isNetworkEnabled){
            this.provider = LocationManager.NETWORK_PROVIDER;
            this.requestLocationUpdatesEnable();
        }

        if (this.isGPSEnabled){
            this.provider = LocationManager.GPS_PROVIDER;
            this.requestLocationUpdatesEnable();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdatesEnable(){
        this.locationManager.requestLocationUpdates(
                this.provider,
                this.minTimeBetweenUpdates,
                this.minDistanceChangeForUpdates,
                this);

        location.setValue(locationManager.getLastKnownLocation(this.provider));
    }

    private boolean verifyNetworkStatusDisabled() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return !isGPSEnabled && !isNetworkEnabled;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationViewModel.this);
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location.setValue(this.locationManager.getLastKnownLocation(this.provider));
        Log.d("lc", "longitude: " + location.getLongitude() + "latitude: " + location.getLatitude());
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }
}
