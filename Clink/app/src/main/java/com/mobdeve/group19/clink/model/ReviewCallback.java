package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public interface ReviewCallback {

    void success(Message message, ArrayList<Review> reviews);
    void error(Message message);
    void failure(Throwable t);

}
