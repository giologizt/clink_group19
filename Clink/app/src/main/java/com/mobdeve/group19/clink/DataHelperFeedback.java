package com.mobdeve.group19.clink;

import java.util.ArrayList;

public class DataHelperFeedback {
    public static ArrayList<Feedback> initializeData() {
        ArrayList<Feedback> data = new ArrayList<Feedback>();

        data.add(new Feedback(
                "Ed Sheeran", "I love the drink!"));
        data.add(new Feedback(
                 "Taylor Swift", "This is life changing....."));
        return data;
    }
}

