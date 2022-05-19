package org.loose.fis.sre.exceptions;

public class CartItemAlreadyExists extends Exception{
    private final String title;

    public CartItemAlreadyExists(String title) {
        super(String.format("A book with the title %s already exists in the cart!", title));
        this.title = title;
    }
}
