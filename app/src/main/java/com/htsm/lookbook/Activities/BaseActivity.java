package com.htsm.lookbook.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.htsm.lookbook.Fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    public void requestPermissions() {
        String[] reqPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.LOCATION_HARDWARE};
        List<String> pendingPermissions = new ArrayList<>();

        for(int i = 0; i < reqPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this,
                    reqPermissions[0])
                    != PackageManager.PERMISSION_GRANTED) {
                pendingPermissions.add(reqPermissions[i]);
            }
        }

        if (pendingPermissions.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    pendingPermissions.toArray(new String[pendingPermissions.size()]),
                    PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finishAffinity();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments.size() > 0) {
            if(fragments.get(0) instanceof BaseFragment) {
                if (((BaseFragment) fragments.get(0)).onBackPressed()) {
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
