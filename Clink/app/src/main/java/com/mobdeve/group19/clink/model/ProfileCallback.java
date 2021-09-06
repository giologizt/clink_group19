package com.mobdeve.group19.clink.model;

public interface ProfileCallback {

    void success(Message message, Profile profile);
    void error(Message message);
    void failure(Throwable t);

}
