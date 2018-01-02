package com.htsm.lookbook.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.Models.Book;
import com.htsm.lookbook.Models.User;
import com.htsm.lookbook.R;

/**
 * Created by sabooR on 1/1/2018.
 */

public class BookDetailsFragment extends Fragment
{
    private TextView mBookName;
    private TextView mBookAuthor;
    private TextView mBookEdition;

    private Button mEmailButton;
    private Button mPhoneButton;

    private static final String TAG = "BookDetailsFragment";

    private Book mBook;
    private User mUser;

    private UserController mUserController;

    public static BookDetailsFragment newInstance(Book book)
    {
        Bundle args = new Bundle();
        args.putSerializable("KEY_BOOK_OBJ", book);
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(args);
        Log.i(TAG ,book.getBookName());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        if(getArguments() != null)
            mBook = (Book) getArguments().getSerializable("KEY_BOOK_OBJ");

        mBookName = v.findViewById(R.id.bookName);
        mBookAuthor = v.findViewById(R.id.bookAuthor);
        mBookEdition = v.findViewById(R.id.bookEdition);
        mEmailButton = v.findViewById(R.id.id_email_btn);
        mPhoneButton = v.findViewById(R.id.id_contact_btn);

        mBookName.setText(mBook.getBookName());
        mBookAuthor.setText(mBook.getBookAuthor());
        mBookEdition.setText(mBook.getBookEdition() + "");

        mUserController = new UserController(getActivity());
        mUserController.getUserInfo(mBook.getUserId(), new UserController.OnUserDetailsFetchedListener() {
            @Override
            public void onUserFetched(String userId, User user) {
                mUser = user;
                mEmailButton.setVisibility(View.VISIBLE);
                mPhoneButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(Exception ex) {
                Log.wtf(TAG, ex.toString());
            }
        });

        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mUser.getNumber()));
                if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(BookDetailsFragment.this.getView(), "No Phone App Found!", Snackbar.LENGTH_LONG).show();
                    Log.wtf(TAG, "No Phone App Found!");
                }
            }
        });

        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUser.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, mBook.getBookName());
                if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(BookDetailsFragment.this.getView(), "No Email App Found!", Snackbar.LENGTH_LONG).show();
                    Log.wtf(TAG, "No Email App Found!");
                }
            }
        });

        return v;
    }
}
