package com.example.divsharma7.educonnect;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.divsharma7.educonnect.Alumni.Alumni_mainPage;
import com.example.divsharma7.educonnect.Faculty.Fac_mainPage;
import com.example.divsharma7.educonnect.Student.Student_mainPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        user= FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null)
                {   final String  email=user.getEmail().toString();
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String user_type = (String) messageSnapshot.child("user_type").getValue();
                            //  System.out.println(e_mail + "  " + user_type);
                            //  System.out.println(email + " " + selected);
                            if(email.equals(e_mail)){
                                //  System.out.println("I am here");


                            switch(user_type) {
                                case "Alumni":


                                    Intent intent = new Intent(getApplicationContext(), Alumni_mainPage.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                case "Faculty":

                                    Intent i = new Intent(getApplicationContext(), Fac_mainPage.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                case "Student":
                                    startActivity(new Intent(getApplicationContext(),Student_mainPage.class));

                            }}
                        }}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }});}
                    else{
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));}


            }
        },4000);
    }
}
