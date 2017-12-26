package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance();
    }


    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, SignUpActivity.class);
        return i;
    }
}
