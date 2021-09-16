package com.mobdeve.group19.clink.model;

public class Login {

    private final String username;
    private final String password;

    private String id;
    private String accessToken;
    private String message;

    //Constructor
    Login (String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Getters
    public String getId () {
        return this.id;
    }
    public String getMessage () {
        return this.message;
    }
    public String getAccessToken () {
        return this.accessToken;
    }

}