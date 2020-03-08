package com.borrowing.zwh.model;

import com.borrowing.zwh.entity.Book;
import com.borrowing.zwh.entity.Borrowing;
import com.borrowing.zwh.entity.Stu;

public class lent {
    private Borrowing borrowing;
    private Stu stu;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Stu getStu() {
        return stu;
    }

    public void setStu(Stu stu) {
        this.stu = stu;
    }

    public Borrowing getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Borrowing borrowing) {
        this.borrowing = borrowing;
    }
}
