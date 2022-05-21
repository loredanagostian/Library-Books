package org.loose.fis.sre.exceptions;

public class CartItemAlreadyExists extends Exception{
    private final String title;
    private final String author;

    public CartItemAlreadyExists(String title, String author) {
        super(String.format("A book with the title %s and author %s already exists in the cart!", title, author));

        this.title = title;
        this.author = author;
    }
}
