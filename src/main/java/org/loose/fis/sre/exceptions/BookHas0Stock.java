package org.loose.fis.sre.exceptions;

public class BookHas0Stock extends Exception {
    private final String title;
    private final String author;

    public BookHas0Stock(String title, String author) {
        super(String.format("Book %s - %s is not available for selling.", title, author));
        this.title = title;
        this.author = author;
    }
}
