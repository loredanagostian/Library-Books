package org.loose.fis.sre.exceptions;

import javafx.collections.ObservableList;
import org.loose.fis.sre.model.CartItems;

public class EmptyCart extends Exception{
    public EmptyCart(){
        super("Your cart is empty!");
    }
}
