package org.loose.fis.sre.exceptions;

public class NoBookFound extends Exception{
    private String search;

    public NoBookFound(String search){
        super("No book found with this title!");
        this.search = search;
    }
}
