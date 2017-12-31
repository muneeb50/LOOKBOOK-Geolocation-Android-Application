package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.SphericalUtil;
import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.Models.User;
import com.htsm.lookbook.R;

import java.util.HashMap;

public class MapFragment extends BaseFragment
        implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnMarkerClickListener {

    private UserController mUserController;
    private GoogleMap mGoogleMap;
    private Marker mUserMarker;

    private BottomSheetBehavior mBottomSheetBehavior;
    private SupportMapFragment mSupportMapFragment;
    private ProgressBar mProgressBar;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mUserNumber;
    private ImageView mBackButton;

    //search bar
    private CardView mCardView;
    private SearchView mSearchView;
    private ImageView mMenuButton;

    private LatLng mLocation;
    private HashMap<Marker, String> mUserLocations;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserController = new UserController(getActivity());
        mUserLocations = new HashMap<>();
    }

    @Override
    public boolean onBackPressed() {
        if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            return super.onBackPressed();
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mBottomSheetBehavior = BottomSheetBehavior.from(v.findViewById(R.id.id_bottom_sheet));
        mProgressBar = v.findViewById(R.id.id_progress_bar);
        mUserName = v.findViewById(R.id.id_user_name);
        mUserEmail = v.findViewById(R.id.id_user_email);
        mUserNumber = v.findViewById(R.id.id_user_number);
        mBackButton = v.findViewById(R.id.id_back_button);

        //Search bar
        mCardView = v.findViewById(R.id.id_cardView);
        mSearchView = v.findViewById(R.id.id_searchViewQuery);
        mMenuButton = v.findViewById(R.id.id_btn_menu);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
        mUserMarker = googleMap.addMarker(new MarkerOptions().position(mLocation).title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 15));
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraMoveCanceledListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        getUserNearBy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(mUserMarker))
            return false;
        String key = mUserLocations.get(marker);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mProgressBar.setVisibility(View.VISIBLE);
        mUserController.getUserInfo(key, new UserController.OnUserDetailsFetchedListener() {
            @Override
            public void onUserFetched(String key, User user) {
                mUserName.setText(user.getName());
                mUserEmail.setText(user.getEmail());
                mUserNumber.setText(user.getNumber());
                mProgressBar.setVisibility(View.GONE);

                UsersBooksFragment usersBooksFragment = UsersBooksFragment.newInstance(key);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.id_books_list_fragment, usersBooksFragment)
                        .commit();
            }

            @Override
            public void onFailed(Exception ex) {
                Toast.makeText(getActivity(), "Error fetching Info", Toast.LENGTH_LONG).show();
            }
        });
        return true;
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
        Marker marker = mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(location)
                        .title(name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user)));
        mUserLocations.put(marker, userKey);
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
