package com.htsm.lookbook.Models;

/**
 * Created by saboo on 12/28/2017.
 */

public class Book {
    private String mBookName;
    private int mBookEdition;
    private String mBookAuthor;
    private String mUserId;

    public Book() {}

    public Book(String mBookName, int mBookEdition, String mBookAuthor, String mUserId) {
        this.mBookName = mBookName;
        this.mBookEdition = mBookEdition;
        this.mBookAuthor = mBookAuthor;
        this.mUserId = mUserId;
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String bookName) {
        mBookName = bookName;
    }

    public int getBookEdition() {
        return mBookEdition;
    }

    public void setBookEdition(int bookEdition) {
        mBookEdition = bookEdition;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        mBookAuthor = bookAuthor;
    }

    public String getUserId() {
        return mUserId;
    }
}
