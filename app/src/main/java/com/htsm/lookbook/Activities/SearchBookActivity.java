package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.SearchBookFragment;

/**
 * Created by imhashir on 1/1/18.
 */

public class SearchBookActivity extends SingleFragmentActivity {

    private static final String EXTRA_BOOK_WORD = "SearchBookActivity.word";

    public static Intent newIntent(Context context, String word) {
        Intent i = new Intent(context, SearchBookActivity.class);
        i.putExtra(EXTRA_BOOK_WORD, word);
        return i;
    }

    @Override
    public Fragment createFragment() {
        String searchWord = getIntent().getExtras().getString(EXTRA_BOOK_WORD);
        return SearchBookFragment.newInstance(searchWord);
    }
}
