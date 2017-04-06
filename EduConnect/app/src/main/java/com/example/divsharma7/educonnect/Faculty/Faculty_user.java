package com.example.divsharma7.educonnect.Faculty;

/**
 * Created by divsharma7 on 29/3/17.
 */

public class Faculty_user {

    public String email;
    public String user_type;
    public String name;
    public String phonenum;

    public String field_res;
    public String dept;



    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Faculty_user() {
    }

    public Faculty_user(String email, String user_type,String name,String phonenum,String field_res,String dept) {
        this.email = email;
        this.user_type = user_type;
        this.name=name;
        this.dept=dept;
        this.field_res=field_res;

        this.phonenum=phonenum;




    }
}
