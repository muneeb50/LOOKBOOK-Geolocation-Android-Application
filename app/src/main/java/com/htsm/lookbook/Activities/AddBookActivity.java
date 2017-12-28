package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.AddBookFragment;

public class AddBookActivity extends SingleFragmentActivity
{
    public static Intent newIntent(Context context)
    {
        return new Intent(context, AddBookActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return AddBookFragment.newInstance();
    }
}
