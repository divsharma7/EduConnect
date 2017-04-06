package com.example.divsharma7.educonnect;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.divsharma7.educonnect.Alumni.Alumni_mainPage;
import com.example.divsharma7.educonnect.Alumni.User_Profile_alum;
import com.example.divsharma7.educonnect.Faculty.Fac_mainPage;
import com.example.divsharma7.educonnect.Faculty.User_Profile_fac;
import com.example.divsharma7.educonnect.Student.Stud_user;
import com.example.divsharma7.educonnect.Student.Student_mainPage;
import com.example.divsharma7.educonnect.Student.User_Profile_stu;
import com.example.divsharma7.educonnect.modelclasses.Feeder;
import com.example.divsharma7.educonnect.modelclasses.Feeder2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mabbas007.tagsedittext.TagsEditText;

public class NewsFeedUpdate extends AppCompatActivity {
    private Button post;

    private EditText title, cont;
    String uid;
    TagsEditText tagsEditText;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_update);
        post = (Button) findViewById(R.id.post);
        title = (EditText) findViewById(R.id.feed_title);
        cont = (EditText) findViewById(R.id.feed_content);
        user = FirebaseAuth.getInstance().getCurrentUser();


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uid = user.getEmail();
                String tit = title.getText().toString().trim();
                String message = cont.getText().toString().trim();

                if (tit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Title", Toast.LENGTH_LONG).show();
                    return;
                }
                if (message.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter post content", Toast.LENGTH_LONG);
                    return;
                }

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("newsfeed");

                // Creating new user node, which returns the unique key value
                // new user node would be /users/$userid/
                String userId = mDatabase.push().getKey();
                // creating user object
                Feeder2 u = new Feeder2(tit, message, uid);

                // pushing user to 'users' node using the userId
                mDatabase.child(userId).setValue(u);

                Snackbar snackbar = Snackbar
                        .make(getCurrentFocus(), "Posted to news feed", Snackbar.LENGTH_LONG);

                snackbar.show();

                mDatabase = FirebaseDatabase.getInstance().getReference("users");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String user_type = (String) messageSnapshot.child("user_type").getValue();
                            //System.out.println(e_mail);
                            //  System.out.println(e_mail + "  " + user_type);
                            //  System.out.println(email + " " + selected);
                            if (uid.equals(e_mail)) {

                                if (user_type.equals("Student")) { //   Toast.makeText(LoginActivity.this,"HI",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Student_mainPage.class);

                                    startActivity(intent);
                                } else if (user_type.equals("Faculty")) {
                                    Intent intent = new Intent(getApplicationContext(), Fac_mainPage.class);

                                    startActivity(intent);


                                } else if (user_type.equals("Alumni")) {
                                    Intent intent = new Intent(getApplicationContext(), Alumni_mainPage.class);

                                    startActivity(intent);

                                }

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

            }
        });
    }
}
