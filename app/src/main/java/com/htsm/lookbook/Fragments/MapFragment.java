package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.R;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private SupportMapFragment mSupportMapFragment;
    private UserController mUserController;

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
        LatLng location = new LatLng(mUserController.getUserLatitute(), mUserController.getUserLongitude());
        googleMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }
}
