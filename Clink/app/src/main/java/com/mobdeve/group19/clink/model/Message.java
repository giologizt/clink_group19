package com.mobdeve.group19.clink.model;

public class Message {

    private int code;
    private String message;

    public Message (int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int code () {
        return this.code;
    }

}
