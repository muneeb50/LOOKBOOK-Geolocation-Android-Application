package com.htsm.lookbook.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;

public class LocationHelper {

    private Context mContext;
    private LocationEnableListener mLocationEnableListener;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private AlertDialog.Builder mLoadingDialogBuilder;
    private AlertDialog mLoadingDialog;

    private static final String TAG = "LocationHelper";

    public interface LocationEnableListener {
        void onLocationAvailable(Location location);
        void onLocationDisabled();
    }

    public LocationHelper(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(LocationEnableListener listener) {
        mLocationEnableListener = listener;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
                    .setTitle("Enable Location")
                    .setMessage("Location is not enabled. Please turn on location.")
                    .setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent locIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(locIntent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mLocationEnableListener.onLocationDisabled();
                        }
                    });
            dialog.show();
        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
            mLoadingDialogBuilder = new AlertDialog.Builder(mContext)
                    .setTitle("Getting Location...")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mLocationManager.removeUpdates(mLocationListener);
                            dialogInterface.dismiss();
                        }
                    })
                    .setView(new ProgressBar(mContext));

            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null) {
                mLocationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mLocationEnableListener.onLocationAvailable(location);
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };
                locationManager.requestSingleUpdate(provider, mLocationListener, null);
                mLoadingDialog = mLoadingDialogBuilder.show();
                Log.i(TAG, "Requesting Location updates");
            } else {
                Log.wtf(TAG, "No provider found!");
            }
        }
    }
}
