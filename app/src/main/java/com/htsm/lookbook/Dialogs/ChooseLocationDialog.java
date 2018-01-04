package com.htsm.lookbook.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.htsm.lookbook.R;

/**
 * Created by imhashir on 1/3/18.
 */

public class ChooseLocationDialog extends DialogFragment implements OnMapReadyCallback {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private Marker mMarker;

    public static final String LATITUDE = "ChooseLocationDialog.latitude";
    public static final String LONGITUDE = "ChooseLocationDialog.longitude";

    private GeoLocation mLocation;

    private static final String TAG = "ChooseLocationDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_location, null, false);
        mSupportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentByTag("tag_map_dialog");
        mSupportMapFragment.getMapAsync(this);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent result = new Intent();
                        result.putExtra(LATITUDE, mLocation.latitude);
                        result.putExtra(LONGITUDE, mLocation.longitude);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    }
                })
                .create();
    }

    public void removeFragments() {
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager()
                        .findFragmentByTag("tag_map_dialog"))
            .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        removeFragments();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(getArguments() != null) {
            double latitude = getArguments().getDouble(LATITUDE);
            double longitude = getArguments().getDouble(LONGITUDE);
            setLocationMarker(new LatLng(latitude, longitude));
        }

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLocation = new GeoLocation(latLng.latitude, latLng.longitude);
                setLocationMarker(latLng);
                Log.i(TAG, "Location Updated!\n" + latLng);
            }
        });
        Log.i(TAG, "Map Ready!");
    }

    public void setLocationMarker(LatLng location) {
        if(mMarker != null)
            mMarker.remove();
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(location));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        mLocation = new GeoLocation(location.latitude, location.longitude);
    }
}
