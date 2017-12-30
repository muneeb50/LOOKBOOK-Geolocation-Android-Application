package com.htsm.lookbook.Controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.htsm.lookbook.Models.Book;

/**
 * Created by saboor on 12/28/2017.
 */

public class BooksController {

    private OnTaskCompletedListener mOnTaskCompletedListener;
    private FirebaseDatabase mDatabase;

    public interface OnTaskCompletedListener {
        void onTaskSuccessful();
        void onTaskFailed(Exception ex);
    }

    public BooksController() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void addBook(String bookName, String bookAuthor, int bookEdition, OnTaskCompletedListener listener) {
        mOnTaskCompletedListener = listener;
        DatabaseReference databaseReference = mDatabase.getReference().child("Books");
        Book book = new Book(FirebaseAuth.getInstance().getCurrentUser().getUid(),bookName,bookAuthor,bookEdition);
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

}
