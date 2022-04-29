package org.loose.fis.sre.exceptions;

public class InvalidCredentials extends Exception{
    private final String password, user;

    public InvalidCredentials(String user, String password) {
        super("Invalid Credentials!");
        this.user = user;
        this.password = password;
    }

    public String getUsername(){
        return user;
    }

    public String getPassword() {
            return password;
    }
}
