package com.example.divsharma7.educonnect.Faculty;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.divsharma7.educonnect.Alumni.User_Profile_alum;
import com.example.divsharma7.educonnect.Student.User_Profile_stu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.divsharma7.educonnect.R;
import com.example.divsharma7.educonnect.Student.Student_mainPage;
import com.example.divsharma7.educonnect.modelclasses.Feed_holder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile_fac extends AppCompatActivity {
    private ProgressDialog p;
    DatabaseReference mDatabase;
    private TextView name, user_type, phone, dept, email, curr_res, grad;
    String pres_email;
    int resp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile_fac);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        pres_email = bd.getString("this_email");
        name = (TextView) findViewById(R.id.user_profile_name);
        user_type = (TextView) findViewById(R.id.user_profile_type);
        phone = (TextView) findViewById(R.id.fac_phone);
        dept = (TextView) findViewById(R.id.dept);
        curr_res = (TextView) findViewById(R.id.res_field);

        email = (TextView) findViewById(R.id.fac_email);
        new loadingprofile().execute();
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(User_Profile_fac.this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    resp);
            ActivityCompat.requestPermissions(User_Profile_fac.this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    resp);
        }
        curr_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    String term = curr_res.getText().toString().substring(24);
                    intent.putExtra(SearchManager.QUERY, term);
                    startActivity(intent);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email.getText().toString()));
                    //   intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                    //   intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //TODO smth
                }


            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number ="tel:"+phone.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });


    }
    private class loadingprofile extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... params) {

            mDatabase= FirebaseDatabase.getInstance().getReference("Fac_users");

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        String e_mail = (String) messageSnapshot.child("email").getValue();
                        String u_type = (String) messageSnapshot.child("user_type").getValue();
                        //  System.out.println(e_mail + "  " + user_type);
                        //  System.out.println(email + " " + selected);
                        if(pres_email.equals(e_mail)){
                            System.out.println("I am here" + pres_email);
                            name.setText("Name :"+(String)messageSnapshot.child("name").getValue());
                            curr_res.setText("Current Research Field :" +messageSnapshot.child("field_res").getValue().toString());
                            phone.setText("Phone :" +messageSnapshot.child("phonenum").getValue().toString());
                            user_type.setText(u_type);
                            email.setText("Email :" +e_mail);
                            dept.setText("Department:" + messageSnapshot.child("dept").getValue().toString());



                        }
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });


            return null;
        }

        @Override
        protected void onPreExecute() {

            p = new ProgressDialog(User_Profile_fac.this);
            p.setMessage("Loading Profile...");
            p.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            p.dismiss();
        }
    }
}

