package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.SignInFragment;

public class SignInActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return SignInFragment.newInstance();
    }
}
