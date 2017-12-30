package com.htsm.lookbook.Controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.htsm.lookbook.Models.Book;

/**
 * Created by saboor on 12/28/2017.
 */

public class BooksController {

    private OnBookRetrievedListener mOnBookRetrievedListener;
    private OnTaskCompletedListener mOnTaskCompletedListener;
    private FirebaseDatabase mDatabase;

    private static final String TAG = "BooksController";

    public interface OnTaskCompletedListener {
        void onTaskSuccessful();
        void onTaskFailed(Exception ex);
    }

    public interface OnBookRetrievedListener {
        void onBookRetrieved(Book book);
        void onTaskFailed(Exception ex);
    }

    public BooksController() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void addBook(String bookName, String bookAuthor, int bookEdition, OnTaskCompletedListener listener) {
        mOnTaskCompletedListener = listener;
        DatabaseReference databaseReference = mDatabase.getReference().child("Books");
        Book book = new Book(bookName,bookEdition,bookAuthor,FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.push().setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()) {
                    mOnTaskCompletedListener.onTaskSuccessful();
                }
                else {
                    mOnTaskCompletedListener.onTaskFailed(task.getException());
                }
            }
        });
    }

    public void getUserBooks(String key, OnBookRetrievedListener listener) {
        mOnBookRetrievedListener = listener;
        mDatabase.getReference("Books").orderByChild("userId").equalTo(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book book = dataSnapshot.getValue(Book.class);
                mOnBookRetrievedListener.onBookRetrieved(book);
                Log.i(TAG, book.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
