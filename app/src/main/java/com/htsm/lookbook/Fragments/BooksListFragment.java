package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htsm.lookbook.Controllers.BooksController;
import com.htsm.lookbook.Models.Book;
import com.htsm.lookbook.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BooksListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    protected String mUserId;
    private BooksController mBooksController;
    private BooksAdapter mBooksAdapter;

    protected static final String KEY_USER_ID = "BooksListFragment.mUserId";
    private static final String TAG = "BooksListFragment";

    public abstract void onBookClicked(Book book);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books_list, container, false);
        mRecyclerView = v.findViewById(R.id.id_books_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBooksAdapter = new BooksAdapter();
        mRecyclerView.setAdapter(mBooksAdapter);

        mUserId = getArguments().getString(KEY_USER_ID);
        mBooksController = new BooksController();
        mBooksController.getUserBooks(mUserId, new BooksController.OnBookRetrievedListener() {
            @Override
            public void onBookRetrieved(Book book) {
                mBooksAdapter.addBook(book);
                mBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTaskFailed(Exception ex) {
                Log.wtf(TAG, ex.toString());
            }
        });

        return v;
    }

    private class BooksAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> mBooks;

        public BooksAdapter() {
            mBooks = new ArrayList<>();
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.single_book_layout, parent, false);
            return new BookHolder(v);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            holder.bindView(mBooks.get(position));
        }

        public void addBook(Book book) {
            mBooks.add(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }

    private class BookHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mBookName;
        private TextView mAuthorName;

        private Book mBook;

        public BookHolder(View itemView) {
            super(itemView);
            mBookName = itemView.findViewById(R.id.id_book_name);
            mAuthorName = itemView.findViewById(R.id.id_author_name);
            itemView.setOnClickListener(this);
        }

        public void bindView(Book book) {
            mBook = book;
            mBookName.setText(mBook.getBookName());
            mAuthorName.setText(mBook.getBookAuthor());
        }

        @Override
        public void onClick(View view) {
            onBookClicked(mBook);
        }
    }
}