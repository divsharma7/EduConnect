package com.example.divsharma7.educonnect;

import android.content.Intent;
import android.provider.*;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divsharma7.educonnect.Alumni.User_Profile_alum;
import com.example.divsharma7.educonnect.Faculty.User_Profile_fac;
import com.example.divsharma7.educonnect.Student.User_Profile_stu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Specific_users extends AppCompatActivity {
    ListView user_list; String user_type;
    ArrayList<String> users;
    String cat_type;
    String valueofcat;
    TextView cat_type_text ;
    DatabaseReference mDatabase; FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_users);
        user= FirebaseAuth.getInstance().getCurrentUser();
        cat_type_text=(TextView)findViewById(R.id.cattype);

        user_list=(ListView)findViewById(R.id.mylistview);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        user_type=bd.getString("mycontext");
        valueofcat=bd.getString("valueofcat");
        cat_type=bd.getString("cat_type");
        System.out.println("This is user_type"+user_type);
        System.out.println("This is value of cat"+ valueofcat);
        System.out.println("Category type "+ cat_type);
        users=new ArrayList<>();
        cat_type_text.setText("Category :" +cat_type + "," + valueofcat);


        switch(user_type) {

            case "Alumni":
                mDatabase = FirebaseDatabase.getInstance().getReference("Alum_users");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String u_type = (String) messageSnapshot.child("user_type").getValue();
                            String grad_school=(String)messageSnapshot.child("grad_school").getValue();
                            String field=(String)messageSnapshot.child("field").getValue();
                            String job=(String)messageSnapshot.child("job").getValue();
                            //  System.out.println(e_mail + "  " + user_type);
                            //  System.out.println(email + " " + selected);
                            if(cat_type.equals("Grad School")) {
                                if (valueofcat.equals(grad_school)) {
                                    users.add(e_mail);
                                    Log.d("user added",e_mail);
                                    Log.d("New size", String.valueOf(users.size()));
                                    Toast.makeText(getApplicationContext(),"I am here",Toast.LENGTH_SHORT).show();
                                }
                            }
                             if(cat_type.equals("Current Company")) {
                                 if (valueofcat.equals(job)) {
                                     users.add(e_mail);
                                     Log.d("user added", e_mail);
                                     Log.d("New size", String.valueOf(users.size()));
                                     //      Toast.makeText(getApplicationContext(),"Not  here",Toast.LENGTH_SHORT).show();
                                 }

                             }

                            if(cat_type.trim().equals("Job Profile")) {
                                System.out.println(field);
                                if (valueofcat.equals(field)) {

                                    users.add(e_mail);
                                    Log.d("user added", e_mail);
                                    Log.d("New size", String.valueOf(users.size()));
                                   // Toast.makeText(getApplicationContext(), "Or here", Toast.LENGTH_SHORT).show();
                                }
                            }

                    }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.mylistviewlayout,
                                users );

                        user_list.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
                break;
            case "Student":
                mDatabase = FirebaseDatabase.getInstance().getReference("Stud_users");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String u_type = (String) messageSnapshot.child("user_type").getValue();
                            //  System.out.println(e_mail + "  " + user_type);
                            //  System.out.println(email + " " + selected);

                                //  System.out.println("I am here");
                              String major=(String)messageSnapshot.child("major").getValue();

                            if(cat_type.equals("Major")){

                                if(valueofcat.equals(major)){
                                    users.add(e_mail);
                                 //   System.out.println(users.get(0));
                                    Toast.makeText(getApplicationContext(),"Or here",Toast.LENGTH_SHORT).show();


                            }
                        }


                    }ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.mylistviewlayout,
                                users );

                        user_list.setAdapter(arrayAdapter);}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
                break;
            case "Faculty":
                mDatabase = FirebaseDatabase.getInstance().getReference("Fac_users");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String u_type = (String) messageSnapshot.child("user_type").getValue();
                            String dept=(String )messageSnapshot.child("dept").getValue();
                            String res_field=(String)messageSnapshot.child("field_res").getValue();

                            if(cat_type.equals("Department"))
                            { if(valueofcat.equals(dept))
                                {
                                    users.add(e_mail);
                                   // Toast.makeText(getApplicationContext(),"At department",Toast.LENGTH_SHORT);
                                }
                             }

                             else if(cat_type.equals("Research Field")){
                                if(valueofcat.equals(res_field))
                                {
                                    users.add(e_mail);
                                 //   Toast.makeText(getApplicationContext(),"At research",Toast.LENGTH_SHORT);
                                }


                    }}
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.mylistviewlayout,
                                users );

                        user_list.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
                break;
        }

            Log.d("size is ", String.valueOf(users.size()));
            for ( int i =0; i<users.size();i++)
            {
                System.out.println(" User " + i +""+users.get(i));
            }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.mylistviewlayout,
                users );

        user_list.setAdapter(arrayAdapter);


        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final String this_email=users.get(position);

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            String e_mail = (String) messageSnapshot.child("email").getValue();
                            String user_type = (String) messageSnapshot.child("user_type").getValue();
                            //System.out.println(e_mail);
                            //  System.out.println(e_mail + "  " + user_type);
                            //  System.out.println(email + " " + selected);
                            if(this_email.equals(e_mail)){
                                System.out.println("picked email" +this_email);
                                if(user_type.equals("Student"))
                                { //   Toast.makeText(LoginActivity.this,"HI",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),User_Profile_stu.class);
                                    intent.putExtra("this_email",this_email);
                                    startActivity(intent);
                                }
                                else if(user_type.equals("Faculty")){
                                    Intent intent=new Intent(getApplicationContext(),User_Profile_fac.class);
                                    intent.putExtra("this_email",this_email);
                                    startActivity(intent);


                                }
                                else if(user_type.equals("Alumni")){
                                    Intent intent=new Intent(getApplicationContext(),User_Profile_alum.class);
                                    intent.putExtra("this_email",this_email);
                                    startActivity(intent);

                                }

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
            }
        });




    }}

