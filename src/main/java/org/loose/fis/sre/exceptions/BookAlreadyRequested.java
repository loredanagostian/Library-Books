package org.loose.fis.sre.exceptions;

public class BookAlreadyRequested extends Exception{
    private String title;

    public BookAlreadyRequested(String title){
        super(String.format("You already rented %s.", title));
        this.title = title;
    }
}
