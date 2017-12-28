package com.htsm.lookbook.Models;

import com.firebase.geofire.GeoLocation;

public class User {

    private String mName;
    private String mEmail;
    private String mNumber;
    private GeoLocation mLocation;

    public User() { }

    public User(String name, String email, String number, GeoLocation location) {
        mName = name;
        mEmail = email;
        mNumber = number;
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public GeoLocation getLocation() {
        return mLocation;
    }

    public void setLocation(GeoLocation location) {
        mLocation = location;
    }
}
