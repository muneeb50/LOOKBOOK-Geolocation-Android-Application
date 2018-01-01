package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsm.lookbook.Controllers.BooksController;
import com.htsm.lookbook.Models.Book;

/**
 * Created by imhashir on 1/1/18.
 */

public class SearchBookFragment extends BooksListFragment {

    private static final String KEY_BOOK_WORD = "SearchBookFragment.word";
    private static final String TAG = "SearchBookFragment";

    private String mSearchString;
    private BooksController mBooksController;

    public static SearchBookFragment newInstance(String word) {
        Bundle args = new Bundle();
        args.putString(KEY_BOOK_WORD, word);
        SearchBookFragment fragment = new SearchBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchString = getArguments().getString(KEY_BOOK_WORD);

        mBooksController = new BooksController();
        mBooksController.searchByBookName(mSearchString, new BooksController.OnBookRetrievedListener() {
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
    public void onBookClicked(Book book, String bookId) {
        //TODO: Open Book Details
    }
}
