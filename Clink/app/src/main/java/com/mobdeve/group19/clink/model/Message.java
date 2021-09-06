package com.mobdeve.group19.clink.model;

public class Message {

    String message;
    Integer code;
    String token;

    public Message(String message, Integer code, String token) {
        this.message = message;
        this.code = code;
        this.token = token;
    }

    public Message(String message, Integer code) {
        this.message = message;
        this.code = code;
        this.token = null;
    }

    public Message(String token) {
        this.token = token;
        this.code = -1;
        this.message = null;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }
}
