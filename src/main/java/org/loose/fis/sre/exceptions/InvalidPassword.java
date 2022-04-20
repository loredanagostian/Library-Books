package org.loose.fis.sre.exceptions;

public class InvalidPassword extends Exception{
    private String password;

    public InvalidPassword(String password) {
        super(String.format("Password incorrect. Please fill in again."));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
