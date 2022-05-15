package org.loose.fis.sre.exceptions;

public class BookHas0Stock extends Exception {

    public BookHas0Stock(String title, String author) {
        super(String.format("Book %s - %s is not available for selling.", title, author));
    }
}
