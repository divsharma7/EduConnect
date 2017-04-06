package com.example.divsharma7.educonnect.Student;

/**
 * Created by divsharma7 on 23/2/17.
 */

public class Stud_user {

    public String email;
    public String user_type;
    public String name;
    public String phonenum;
    public String major;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Stud_user() {
    }

    public Stud_user(String email, String user_type,String name,String phonenum,String major) {
        this.email = email;
        this.user_type = user_type;
        this.name=name;
        this.major=major;
        this.phonenum=phonenum;
    }
}