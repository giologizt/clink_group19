package com.mobdeve.group19.clink.model;

public interface CustomCallback {

    void success(Message message);
    void failure(Throwable t);

}
