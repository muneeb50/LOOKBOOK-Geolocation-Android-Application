package com.htsm.lookbook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.htsm.lookbook.Models.Book;
import com.htsm.lookbook.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllBooksFragment extends Fragment
{
    private DatabaseReference mDataBase;

    private ListView mBooksList;

    private ArrayList<String> BooksList = new ArrayList<>();

    public static ViewAllBooksFragment newInstance() {
        return new ViewAllBooksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_view_all_books, container, false);

        mBooksList = (ListView) v.findViewById(R.id.booksList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, BooksList);

        mBooksList.setAdapter(arrayAdapter);

        final List<Book> books = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Books")
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            Book book = snapshot.getValue(Book.class);

                            if(book.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            {
                                books.add(book);
                                BooksList.add(book.getName());
                            }
                        }
                        if(books.size() == 0)
                        {
                            BooksList.add("No Book Present!");
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        return v;
    }
}

