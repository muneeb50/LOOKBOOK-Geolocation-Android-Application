package com.htsm.lookbook.Models;

public class Book
{
    private String bookName;
    private int bookEdition;
    private String bookAuthor;
    private String userId;

    public Book() {
    }

    public Book(String mBookName, int mBookEdition, String mBookAuthor, String mUserId) {
        this.bookName = mBookName;
        this.bookEdition = mBookEdition;
        this.bookAuthor = mBookAuthor;
        this.userId = mUserId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(int bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getUserId() {
        return userId;
    }
}
