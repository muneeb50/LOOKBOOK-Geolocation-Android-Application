package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.R;

/**
 * Created by saboor on 12/28/2017.
 */

public class AddBookFragment extends Fragment
{
    private static final String TAG = "AddBookFragment";

    private EditText mBookNameInput;
    private EditText mBookAuthorInput;
    private EditText mBookEditionInput;

    private Button mAddBookButton;

    private UserController mUserController;

    public static AddBookFragment newInstance() {
        return new AddBookFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserController = new UserController(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_book, container, false);

        mBookNameInput = v.findViewById(R.id.id_book_name);
        mBookAuthorInput = v.findViewById(R.id.id_book_author);
        mBookEditionInput = v.findViewById(R.id.id_book_edition);

        mAddBookButton = v.findViewById(R.id.id_btn_add_book);

        mAddBookButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mBookNameInput.getText().length() > 0 && mBookAuthorInput.getText().length() > 0 && mBookEditionInput.getText().length() > 0)
                {
                    mUserController.AddBook(mBookNameInput.getText().toString(), mBookAuthorInput.getText().toString(), Integer.parseInt(mBookEditionInput.getText().toString()), new UserController.OnTaskCompletedListener()
                    {
                        @Override
                        public void onTaskSuccessful()
                        {
                            Snackbar.make(AddBookFragment.this.getView(), "Book has been added", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onTaskFailed(Exception ex)
                        {
                            Snackbar.make(AddBookFragment.this.getView(), "There is some error adding the book", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Snackbar.make(AddBookFragment.this.getView(), "Fill out all the fields!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
