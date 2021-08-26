package com.mobdeve.group19.clink;

import java.util.ArrayList;

public class DataHelperRecipe {
    public static ArrayList<Recipe> initializeData() {
        ArrayList<Recipe> data = new ArrayList<Recipe>();

        data.add(new Recipe(
                R.drawable.margarita, "Margarita", "5 minutes"));
        data.add(new Recipe(
                R.drawable.grasshopper, "Grasshopper", "10 minutes"));
        return data;
    }
}
