package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.htsm.lookbook.Controllers.BooksController;
import com.htsm.lookbook.Models.Book;
import com.htsm.lookbook.R;

/**
 * Created by saboor on 12/28/2017.
 */

public class AddBookFragment extends Fragment
{
    private static final String TAG = "AddBookFragment";
    private static final String KEY_BOOK_OBJ = "AddBookFragment.editObj";
    private static final String KEY_BOOK_ID = "AddBookFragment.editId";

    private EditText mBookNameInput;
    private EditText mBookAuthorInput;
    private EditText mBookEditionInput;

    private Button mAddBookButton;

    private BooksController mBooksController;
    private String mBookId;
    private Book mBook;

    public static AddBookFragment newEditInstance(Book book, String id) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_BOOK_OBJ, book);
        args.putString(KEY_BOOK_ID, id);
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddBookFragment newAddInstance() {
        return new AddBookFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksController = new BooksController();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_book, container, false);

        if(getArguments() != null)
            mBookId = getArguments().getString(KEY_BOOK_ID);

        mBookNameInput = v.findViewById(R.id.id_book_name);
        mBookAuthorInput = v.findViewById(R.id.id_book_author);
        mBookEditionInput = v.findViewById(R.id.id_book_edition);
        mAddBookButton = v.findViewById(R.id.id_btn_add_book);

        if(mBookId != null) {
            mBook = (Book) getArguments().getSerializable(KEY_BOOK_OBJ);
            getActivity().setTitle("Edit Book Info");
            mAddBookButton.setText("Update");
        }

        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBookNameInput.getText().length() > 0 && mBookAuthorInput.getText().length() > 0 && mBookEditionInput.getText().length() > 0) {
                    mBooksController.addUpadteBook(mBookNameInput.getText().toString(), mBookAuthorInput.getText().toString(), Integer.parseInt(mBookEditionInput.getText().toString()), mBookId, new BooksController.OnTaskCompletedListener() {
                        @Override
                        public void onTaskSuccessful() {
                            if(mBookId == null) {
                                mBookNameInput.setText("");
                                mBookAuthorInput.setText("");
                                mBookEditionInput.setText("");
                            }
                            Snackbar.make(AddBookFragment.this.getView(), mBookId == null ? "Book has been added" : "Book Info updated!", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onTaskFailed(Exception ex) {
                            Log.wtf(TAG, ex.toString());
                            Snackbar.make(AddBookFragment.this.getView(), "Error Occurred!", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    Snackbar.make(AddBookFragment.this.getView(), "Fill out all the fields!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mBook != null) {
            mBookNameInput.setText(mBook.getBookName());
            mBookAuthorInput.setText(mBook.getBookAuthor());
            mBookEditionInput.setText(mBook.getBookEdition() + "");
        }
    }
}
