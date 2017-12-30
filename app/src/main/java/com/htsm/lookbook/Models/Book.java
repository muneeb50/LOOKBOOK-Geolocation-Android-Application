package com.htsm.lookbook.Models;

public class Book
{
    private String User;
    private String Name;
    private String Author;
    private int Edition;

    public Book() {
    }

    public Book(String user, String name, String author, int edition) {
        User = user;
        Name = name;
        Author = author;
        Edition = edition;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getEdition() {
        return Edition;
    }

    public void setEdition(int edition) {
        Edition = edition;
    }
}
