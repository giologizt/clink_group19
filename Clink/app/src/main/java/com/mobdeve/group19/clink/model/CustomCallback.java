package com.mobdeve.group19.clink.model;

public interface CustomCallback {

    //function to call when server receives a successful status (e.g. 200)
    void success(Message message);

    //function to call when server receives a unsuccessful status (e.g. 404)
    void error(Message message);

    //function to call when server receives an error
    void failure(Throwable t);

}
