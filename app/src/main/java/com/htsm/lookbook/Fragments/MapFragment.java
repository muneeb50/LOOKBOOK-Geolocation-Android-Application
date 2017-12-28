package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.SphericalUtil;
import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.R;

public class MapFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveCanceledListener {

    private SupportMapFragment mSupportMapFragment;
    private UserController mUserController;
    private GoogleMap mGoogleMap;
    private LatLng mLocation;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserController = new UserController(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLocation = new LatLng(mUserController.getUserLatitude(), mUserController.getUserLongitude());
        googleMap.addMarker(new MarkerOptions().position(mLocation).title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 15));
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraMoveCanceledListener(this);
        getUserNearBy();
    }

    private void getUserNearBy() {
        mUserController.getUsersNearBy(new GeoLocation(mGoogleMap.getCameraPosition().target.latitude, mGoogleMap.getCameraPosition().target.longitude), getRadius(), new UserController.OnUserFetchedListener() {
            @Override
            public void onUserFetched(String userId, String userName, GeoLocation location) {
                LatLng latLng = new LatLng(location.latitude, location.longitude);
                putLocationMarker(userId, userName, latLng);
            }
        });
    }

    public void putLocationMarker(String userKey, String name, LatLng location) {
        mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(location)
                        .title(name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user)));
    }

    @Override
    public void onCameraMoveCanceled() {
        getUserNearBy();
    }

    public double getRadius() {
        VisibleRegion visibleRegion = mGoogleMap.getProjection().getVisibleRegion();
        double distance = SphericalUtil.computeDistanceBetween(
                visibleRegion.farLeft, mGoogleMap.getCameraPosition().target);
        return distance;
    }
}
