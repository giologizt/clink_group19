package com.mobdeve.group19.clink.model;

public class Register {
    String username;
    String fullname;
    String email;
    String birthday;
    String password;

    // Return Messages
    String message;

    //Constructor
    public Register (String username, String fullname, String email, String birthday, String password) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }

    //Getters
    public String getMessage() {
        return this.message;
    }
    public String getUsername() {
        return this.username;
    }
}
