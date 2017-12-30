package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsm.lookbook.Models.Book;

public class ViewAllBooksFragment extends BooksListFragment {

    public static ViewAllBooksFragment newInstance(String key) {
        Bundle args = new Bundle();
        args.putString(KEY_USER_ID, key);
        ViewAllBooksFragment fragment = new ViewAllBooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBookClicked(Book book) {
        //TODO: Open Book Edit Activity
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUserId = getArguments().getString(KEY_USER_ID);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
