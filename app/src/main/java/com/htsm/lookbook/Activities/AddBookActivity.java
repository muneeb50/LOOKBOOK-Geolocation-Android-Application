package com.htsm.lookbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.htsm.lookbook.Fragments.AddBookFragment;
import com.htsm.lookbook.Models.Book;

public class AddBookActivity extends SingleFragmentActivity
{
    private static final String KEY_BOOK_OBJ = "AddBookFragment.editObj";
    private static final String KEY_BOOK_ID = "AddBookFragment.editId";

    public static Intent newIntent(Context context, Book book, String bookId) {
        Intent i = new Intent(context, AddBookActivity.class);
        if(bookId != null) {
            i.putExtra(KEY_BOOK_ID, bookId);
            i.putExtra(KEY_BOOK_OBJ, book);
        }
        return i;
    }

    @Override
    public Fragment createFragment() {
        if(getIntent().getExtras() != null) {
            String bookId = getIntent().getExtras().getString(KEY_BOOK_ID);
            if(bookId != null) {
                Book book = (Book) getIntent().getExtras().getSerializable(KEY_BOOK_OBJ);
                return AddBookFragment.newEditInstance(book, bookId);
            }
        }
        return AddBookFragment.newAddInstance();
    }
}
