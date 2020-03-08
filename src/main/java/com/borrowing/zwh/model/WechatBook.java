package com.borrowing.zwh.model;

import com.borrowing.zwh.entity.Book;

public class WechatBook {
    private String name;
    private String ct;
    private int kj;
    private int gc;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getGc() {
        return gc;
    }

    public void setGc(int gc) {
        this.gc = gc;
    }

    public int getKj() {
        return kj;
    }

    public void setKj(int kj) {
        this.kj = kj;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
