package org.loose.fis.sre.exceptions;

public class InvalidPassword extends Exception{
    private final String password;

    public InvalidPassword(String password) {
        super("Password incorrect. Please fill in again.");
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
