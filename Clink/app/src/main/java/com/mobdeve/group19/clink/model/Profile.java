package com.mobdeve.group19.clink.model;

public class Profile {

    String id;
    String username;
    String email;
    String fullname;
    String birthday;
    String newpassword;
    String oldpassword;

    public Profile (String email, String fullName, String birthday) {
        this.email = email;
        this.fullname = fullName;
        this.birthday = birthday;
    }

    public Profile (String newpassword, String oldpassword){
        this.newpassword = newpassword;
        this.oldpassword = oldpassword;
    }

    public String getUsername () { return this.username; }

    public String getEmail () { return this.email; }

    public String getFullName () { return this.fullname; }

    public String getBirthday () { return this.birthday; }

    public String getPassword () { return this.newpassword; }

}
