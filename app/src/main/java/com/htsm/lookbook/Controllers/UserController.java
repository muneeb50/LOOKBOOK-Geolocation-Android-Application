package com.htsm.lookbook.Controllers;

import android.support.annotation.NonNull;

import com.firebase.geofire.GeoFire;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.htsm.lookbook.Models.User;

public class UserController {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public UserController() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public interface OnTaskCompletedListener {
        void onTaskSuccessful();
        void onTaskFailed(Exception ex);
    }

    public void signInUser(String email, String password, final OnTaskCompletedListener listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    listener.onTaskSuccessful();
                } else {
                    listener.onTaskFailed(task.getException());
                }
            }
        });
    }

    public void signUpUser(final User user, final OnTaskCompletedListener listener) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    mDatabase.getReference("users").child(user.getNumber()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            DatabaseReference geoDataLoc = null;
                            GeoFire geoFire;
                            if(task.isSuccessful()) {
                                geoDataLoc = mDatabase.getReference("geoLocation");
                                geoFire = new GeoFire(geoDataLoc);
                                geoFire.setLocation(user.getNumber(), user.getLocation(), new GeoFire.CompletionListener() {
                                    @Override
                                    public void onComplete(String key, DatabaseError error) {
                                        listener.onTaskSuccessful();
                                    }
                                });
                            } else {
                                mAuth.getCurrentUser().delete();
                                mAuth.signOut();
                                listener.onTaskFailed(task.getException());
                            }
                        }
                    });
                } else {
                    listener.onTaskFailed(task.getException());
                }
            }
        });
    }
}
