package com.example.divsharma7.educonnect;

/**
 * Created by divsharma7 on 23/2/17.
 */

public class User {

    public String email;
    public String user_type;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String user_type) {
        this.email = email;
        this.user_type = user_type;

    }
}