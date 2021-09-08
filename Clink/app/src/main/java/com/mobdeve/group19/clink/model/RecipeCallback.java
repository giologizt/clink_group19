package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public interface RecipeCallback {

    void success(Message message, Recipe recipe);
    void error(Message message);
    void failure(Throwable t);

}