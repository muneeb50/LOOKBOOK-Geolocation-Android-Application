package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsm.lookbook.Models.Book;

/**
 * Created by imhashir on 12/30/17.
 */

public class UsersBooksFragment extends BooksListFragment {

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onBookClicked(Book book, String bookId) {
        //TODO: Start Book details activity
    }
}
