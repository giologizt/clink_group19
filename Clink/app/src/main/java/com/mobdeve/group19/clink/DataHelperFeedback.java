package com.mobdeve.group19.clink;

import java.util.ArrayList;

public class DataHelperFeedback {
    public static ArrayList<Feedback_test> initializeData() {
        ArrayList<Feedback_test> data = new ArrayList<Feedback_test>();

        data.add(new Feedback_test(
                "Ed Sheeran", "I love the drink!"));
        data.add(new Feedback_test(
                 "Taylor Swift", "This is life changing....."));
        return data;
    }
}

