package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htsm.lookbook.Models.Book;
import com.htsm.lookbook.R;

/**
 * Created by sabooR on 1/1/2018.
 */

public class ViewSomeBookFragment extends Fragment
{
    private TextView mBookName;
    private TextView mBookAuthor;
    private TextView mBookEdition;

    private Book mBook;

    public static ViewSomeBookFragment newViewInstance(Book book)
    {
        Bundle args = new Bundle();
        args.putSerializable("KEY_BOOK_OBJ", book);
        ViewSomeBookFragment fragment = new ViewSomeBookFragment();
        fragment.setArguments(args);
        Log.i("bitt",book.getBookName());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_view_some_book, container, false);

        if(getArguments() != null)
            mBook = (Book) getArguments().getSerializable("KEY_BOOK_OBJ");

        mBookName = v.findViewById(R.id.bookName);
        mBookAuthor = v.findViewById(R.id.bookAuthor);
        mBookEdition = v.findViewById(R.id.bookEdition);

        mBookName.setText(mBook.getBookName());
        mBookAuthor.setText(mBook.getBookAuthor());
        mBookEdition.setText(mBook.getBookEdition() + "");

        return v;
    }
}
