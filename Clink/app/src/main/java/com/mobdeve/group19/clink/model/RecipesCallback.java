package com.mobdeve.group19.clink.model;

import java.util.ArrayList;
import java.util.Collection;

public interface RecipesCallback {

    //Interface specifically for getting recipes functions

    //function to call when server receives a successful status (e.g. 200)
    void success(Message message, ArrayList<Recipe> recipe);

    //function to call when server receives an error
    void error(Message message);

    //function to call when server receives a unsuccessful status (e.g. 404)
    void failure(Throwable t);

}
