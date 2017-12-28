package com.htsm.lookbook.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.htsm.lookbook.Activities.HomeActivity;
import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.Helper.LocationHelper;
import com.htsm.lookbook.Models.User;
import com.htsm.lookbook.R;

public class SignUpFragment extends Fragment implements OnMapReadyCallback{

    private static final int PERMISSIONS_REQUEST_LOCATION = 3331;

    private static final String TAG = "SignUpFragment";

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private Marker mMarker;

    private EditText mNameInput;
    private EditText mPasswordInput;
    private EditText mEmailInput;
    private EditText mNumberInput;
    private Button mLocationButton;
    private Button mSignUpButton;
    private GeoLocation mLocation;
    private UserController mUserController;

    public static Fragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserController = new UserController(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mSignUpButton = v.findViewById(R.id.id_btn_signup);
        mNameInput = v.findViewById(R.id.id_input_name);
        mEmailInput = v.findViewById(R.id.id_input_email);
        mPasswordInput = v.findViewById(R.id.id_input_pass);
        mNumberInput = v.findViewById(R.id.id_input_num);
        mLocationButton = v.findViewById(R.id.id_btn_location);

        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermission();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(formIsValid()) {
                    User user = new User(
                            mNameInput.getText().toString(),
                            mEmailInput.getText().toString(),
                            mNumberInput.getText().toString(),
                            mLocation);
                    mUserController.signUpUser(user, mPasswordInput.getText().toString(), new UserController.OnTaskCompletedListener() {
                        @Override
                        public void onTaskSuccessful() {
                            startActivity(HomeActivity.newIntent(getActivity(), null));
                            getActivity().finish();
                            Log.i(TAG, "Account Created");
                        }

                        @Override
                        public void onTaskFailed(Exception ex) {
                            Snackbar.make(SignUpFragment.this.getView(), "Account Creation failed", Toast.LENGTH_LONG).show();
                            Log.wtf(TAG, ex.toString());
                        }
                    });
                } else {
                    Snackbar.make(SignUpFragment.this.getView(), "Form is not valid!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        mSupportMapFragment.getMapAsync(this);
    }

    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        } else {
            fetchLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        }
    }

    private void fetchLocation() {
        new LocationHelper(getActivity()).requestLocation(new LocationHelper.LocationEnableListener() {
            @Override
            public void onLocationAvailable(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                setLocationMarker(latLng);
                Log.i(TAG, "Location fetched!\nLat: " + location.getLatitude() + " Lon: " + location.getLongitude());
                Toast.makeText(getActivity(), "Location fetched!\nLat: " + location.getLatitude() + " Lon: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocationDisabled() {
                Log.wtf(TAG, "Location Disabled!");
            }
        });
    }

    public boolean formIsValid() {
        return mNameInput.getText().length() > 0 && mEmailInput.getText().length() > 0 &&
                mPasswordInput.getText().length() > 0 && mNumberInput.getText().length() > 0 &&
                mLocation != null;
    }

    public void setLocationMarker(LatLng location) {
        if(mMarker != null)
            mMarker.remove();
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(location));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        mLocation = new GeoLocation(location.latitude, location.longitude);
    }
}
