package com.mobdeve.group19.clink;

public class Feedback_test {
    private String username, comment;
    //private int imageId;

    public Feedback_test(String username, String comment) {
        //this.imageId = imageId;
        this.username = username;
        this.comment = comment;
    }

//   public int getImageId() {
//        return imageId;
//    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
