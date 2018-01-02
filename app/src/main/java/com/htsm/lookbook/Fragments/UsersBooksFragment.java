package com.htsm.lookbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsm.lookbook.Activities.AddBookActivity;
import com.htsm.lookbook.Activities.ViewSomeBookActivity;
import com.htsm.lookbook.Controllers.BooksController;
import com.htsm.lookbook.Models.Book;

/**
 * Created by imhashir on 12/30/17.
 */

public class UsersBooksFragment extends BooksListFragment {

    private BooksController mBooksController;

    private static final String TAG = "UsersBooksFragment";

    public static UsersBooksFragment newInstance(String key) {
        Bundle args = new Bundle();
        args.putString(KEY_USER_ID, key);
        UsersBooksFragment fragment = new UsersBooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUserId = getArguments().getString(KEY_USER_ID);
        mBooksController = new BooksController();
        mBooksController.getUserBooks(mUserId, new BooksController.OnBookRetrievedListener() {
            @Override
            public void onBookRetrieved(Book book, String bookId) {
                addBookToList(book, bookId);
            }

            @Override
            public void onTaskFailed(Exception ex) {
                Log.wtf(TAG, ex.toString());
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onBookClicked(Book book, String bookId)
    {
        Intent i = ViewSomeBookActivity.newIntent(getActivity(), book);
        startActivity(i);
    }
}
