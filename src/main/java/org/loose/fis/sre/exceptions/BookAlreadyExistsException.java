package org.loose.fis.sre.exceptions;

public class BookAlreadyExistsException extends Exception{
    private String title;
    private String author;

    public BookAlreadyExistsException(String title, String author){
        super(String.format("A book with the title %s and author %s already exists!", title, author));

        this.title = title;
        this.author = author;
    }
}
