package org.loose.fis.sre.exceptions;

public class CartItemAlreadyExists extends Exception{
    private final String title;

    public CartItemAlreadyExists(String title) {
        super(String.format("An account with the title %s already exists!", title));
        this.title = title;
    }
}
