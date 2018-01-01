package com.htsm.lookbook.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.AddBookFragment;
import com.htsm.lookbook.Fragments.ViewSomeBookFragment;
import com.htsm.lookbook.Models.Book;

/**
 * Created by saboor on 1/1/2018.
 */

public class ViewSomeBookActivity extends SingleFragmentActivity
{
    public static Intent newIntent(Context context, Book book)
    {
        Intent i = new Intent(context, ViewSomeBookActivity.class);
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
            return ViewSomeBookFragment.newViewInstance(book);
        }
        return null;
    }
}
