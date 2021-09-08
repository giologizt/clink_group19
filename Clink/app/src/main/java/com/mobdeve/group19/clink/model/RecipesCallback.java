package com.mobdeve.group19.clink.model;

import java.util.ArrayList;
import java.util.Collection;

public interface RecipesCallback {

    void success(Message message, ArrayList<Recipe> recipe);
    void error(Message message);
    void failure(Throwable t);

}
