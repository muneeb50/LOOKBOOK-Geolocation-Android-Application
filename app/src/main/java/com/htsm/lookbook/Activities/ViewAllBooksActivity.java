package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.ViewAllBooksFragment;

public class ViewAllBooksActivity extends SingleFragmentActivity
{
    private static final String KEY_USER_ID = "ViewAllBooksActivity.userId";

    public static Intent newIntent(Context context, String userId) {
        Intent i = new Intent(context, ViewAllBooksActivity.class);
        i.putExtra(KEY_USER_ID, userId);
        return i;
    }

    @Override
    public Fragment createFragment() {
        String userId = getIntent().getExtras().getString(KEY_USER_ID);
        return ViewAllBooksFragment.newInstance(userId);
    }
}
