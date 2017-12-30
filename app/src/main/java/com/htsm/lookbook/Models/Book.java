package com.htsm.lookbook.Models;

public class Book
{
    private String mBookName;
    private int mBookEdition;
    private String mBookAuthor;
    private String mUserId;

    public Book() {}

    public Book(String bookName, int bookEdition, String bookAuthor, String userId) {
        mBookName = bookName;
        mBookEdition = bookEdition;
        mBookAuthor = bookAuthor;
        mUserId = userId;
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

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
