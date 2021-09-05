package com.mobdeve.group19.clink.model;

public class Login {

    private final String username;
    private final String password;

    private String userId;
    private String accessToken;
    private String message;

    Login (String username, String password) {

        this.username = username;
        this.password = password;

    }

    public String getUserId () {
        return this.userId;
    }

    public String getMessage () {
        return this.message;
    }

    public String getAccessToken () {
        return this.accessToken;
    }

}
