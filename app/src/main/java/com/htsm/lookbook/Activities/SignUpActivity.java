package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {

    private static final String KEY_IS_UPDATE = "SignUpActivity.isUpdate";
    private boolean mIsUpdate;

    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance(mIsUpdate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsUpdate = getIntent().getExtras().getBoolean(KEY_IS_UPDATE);
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context context, boolean isUpdate) {
        Intent i = new Intent(context, SignUpActivity.class);
        i.putExtra(KEY_IS_UPDATE, isUpdate);
        return i;
    }
}
