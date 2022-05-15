package org.loose.fis.sre.exceptions;

import javafx.scene.control.Alert;

public class BookAlreadyExistsException extends Exception{
    private String title;
    private String author;

    public BookAlreadyExistsException(String title, String author){

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Error adding book");
        alert.setContentText("A book with the title " + title + " and author " + author + " already exists!");
        alert.show();

        this.title = title;
        this.author = author;
    }
}
