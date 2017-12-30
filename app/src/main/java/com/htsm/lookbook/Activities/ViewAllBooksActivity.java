package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.ViewAllBooksFragment;

public class ViewAllBooksActivity  extends SingleFragmentActivity
{
    public static Intent newIntent(Context context)
    {
        return new Intent(context, ViewAllBooksActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return ViewAllBooksFragment.newInstance();
    }
}
