package com.example.divsharma7.educonnect.Alumni;

/**
 * Created by divsharma7 on 29/3/17.
 */

public class Alum_user {

    public String email;
    public String user_type;
    public String name;
    public String phonenum;

    public String field;
    public String job;
    public String grad_school;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Alum_user() {
    }

    public Alum_user(String email, String user_type,String name,String phonenum,String field,String job,String grad_school) {
        this.email = email;
        this.user_type = user_type;
        this.name=name;

        this.phonenum=phonenum;
        this.grad_school=grad_school;
        this.field=field;
        this.job=job;

    }
}
