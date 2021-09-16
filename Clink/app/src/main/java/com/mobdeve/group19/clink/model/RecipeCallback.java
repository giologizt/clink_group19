package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public interface RecipeCallback {

    //Interface specifically for getting a recipe functions

    //function to call when server receives a successful status (e.g. 200)
    void success(Message message, Recipe recipe);

    //function to call when server receives an error
    void error(Message message);

    //function to call when server receives a unsuccessful status (e.g. 404)
    void failure(Throwable t);

}