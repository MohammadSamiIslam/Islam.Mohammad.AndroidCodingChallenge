package com.example.msi.islammohammadandroidcodingchallenge;

/**
 * Created by 23311 on 10/08/2017.
 */

public class BookInfo {

    private String title;
    private String bookImage;

    public BookInfo(String title, String bookImage) {
        this.title = title;
        this.bookImage = bookImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

}
