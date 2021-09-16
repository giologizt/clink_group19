package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public interface ReviewCallback {

    //Interface specifically for review functions

    //function to call when server receives a successful status (e.g. 200)
    void success(Message message, ArrayList<Review> reviews);

    //function to call when server receives a unsuccessful status (e.g. 404)
    void error(Message message);

    //function to call when server receives an error
    void failure(Throwable t);

}
