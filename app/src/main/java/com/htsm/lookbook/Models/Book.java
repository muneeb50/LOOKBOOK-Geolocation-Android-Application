package com.htsm.lookbook.Models;

import com.google.firebase.database.PropertyName;

/**
 * Created by saboo on 12/28/2017.
 */

public class Book
{
    private String mBookName;
    private int mBookEdition;
    private String mBookAuthor;
    private String mUserId;

    public Book(String mBookName, int mBookEdition, String mBookAuthor, String mUserId) {
        this.mBookName = mBookName;
        this.mBookEdition = mBookEdition;
        this.mBookAuthor = mBookAuthor;
        this.mUserId = mUserId;
    }

    @PropertyName("User")
    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    @PropertyName("Name")
    public String getmBookName() {
        return mBookName;
    }

    public void setmBookName(String mBookName) {
        this.mBookName = mBookName;
    }

    @PropertyName("Edition")
    public int getmBookEdition() {
        return mBookEdition;
    }

    public void setmBookEdition(int mBookEdition) {
        this.mBookEdition = mBookEdition;
    }

    @PropertyName("Author")
    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public void setmBookAuthor(String mBookAuthor) {
        this.mBookAuthor = mBookAuthor;
    }
}
