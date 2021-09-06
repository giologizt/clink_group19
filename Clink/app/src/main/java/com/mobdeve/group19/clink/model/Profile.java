package com.mobdeve.group19.clink.model;

public class Profile {

    String id;
    String username;
    String email;
    String fullname;
    String birthday;
    String password;
    String newpassword;
    String oldpassword;

    public Profile(String id, String username, String email, String fullname, String birthday, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.password = password;
    }

    public Profile (String email, String fullName, String birthday) {
        this.email = email;
        this.fullname = fullName;
        this.birthday = birthday;
    }

    public Profile (String newpassword, String oldpassword){
        this.newpassword = newpassword;
        this.oldpassword = oldpassword;
    }

    public String getUsername () {
        return this.username;
    }

    public String getEmail () {
        return this.email;
    }

    public String getFullName () {
        return this.fullname;
    }

    public String getBirthday () {
        return this.birthday;
    }

    public String getNewpassword () {
        return this.newpassword;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
