package org.loose.fis.sre.exceptions;

public class UsernameNotFound extends Exception {
    private String username;

    public UsernameNotFound(String username) {
        super(String.format("An account with the username %s does not exist! You have to register first.", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}