package com.htsm.lookbook.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.BookDetailsFragment;
import com.htsm.lookbook.Models.Book;

/**
 * Created by saboor on 1/1/2018.
 */

public class BookDetailsActivity extends SingleFragmentActivity
{
    public static Intent newIntent(Context context, Book book)
    {
        Intent i = new Intent(context, BookDetailsActivity.class);
        if(book != null)
        {
            i.putExtra("KEY_BOOK_OBJ", book);
        }
        return i;
    }

    @Override
    public Fragment createFragment()
    {
        if(getIntent().getExtras() != null)
        {
            Book book = (Book) getIntent().getExtras().getSerializable("KEY_BOOK_OBJ");
            return BookDetailsFragment.newInstance(book);
        }
        return null;
    }
}
