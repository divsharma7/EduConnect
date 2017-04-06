package com.example.divsharma7.educonnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.divsharma7.educonnect.Alumni.Alumni_Register;
import com.example.divsharma7.educonnect.Faculty.Faculty_Register;
import com.example.divsharma7.educonnect.Student.Stud_register;

public class UserType_select extends AppCompatActivity {
        Button student,faculty,alumni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_select);
        student=(Button) findViewById(R.id.studentbutton);
        faculty=(Button) findViewById(R.id.facultybutton);
        alumni=(Button) findViewById(R.id.alumnibutton);


        alumni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserType_select.this,Alumni_Register.class));
            }
        });
        student.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserType_select.this,Stud_register.class));
            }
        });
        faculty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserType_select.this,Faculty_Register.class));
            }
        });


    }
}
