package com.example.divsharma7.educonnect.Alumni;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.divsharma7.educonnect.LoginActivity;
import com.example.divsharma7.educonnect.R;
import com.example.divsharma7.educonnect.ResetPasswordActivity;
import com.example.divsharma7.educonnect.Settings;
import com.example.divsharma7.educonnect.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Alumni_Register extends AppCompatActivity {
    EditText name,alum_email,job,field,post_grad,pass,phone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni__register);
        phone=(EditText)findViewById(R.id.alum_phone_number);
        name=(EditText)findViewById(R.id.alum_name);
        alum_email=(EditText)findViewById(R.id.alumni_email);
        job=(EditText)findViewById(R.id.alum_job);
        field=(EditText)findViewById(R.id.alum_field);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        post_grad=(EditText)findViewById(R.id.post_grad);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        pass=(EditText)findViewById(R.id.alumni_password);
        auth = FirebaseAuth.getInstance();


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String email = alum_email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                final String phone_alum=phone.getText().toString().trim();
                final String grad=post_grad.getText().toString().trim();
                final String job_alum=job.getText().toString().trim();
                final String alum_field=field.getText().toString().trim();
                final String alum_name=field.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone_alum.length()<10)
                {
                    Toast.makeText(getApplicationContext(), "Enter 10 digit phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(alum_field)){
                    Toast.makeText(getApplicationContext(), "Enter current field!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(alum_name)){
                    Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(job_alum)){
                    Toast.makeText(getApplicationContext(), "Enter current Job profile!", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Alumni_Register.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                DatabaseReference uDatabase = FirebaseDatabase.getInstance().getReference("Alum_users");
                                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("users");

                                // Creating new user node, which returns the unique key value
                                // new user node would be /users/$userid/
                                String userId = mDatabase.push().getKey();
                                String userIdalum=uDatabase.push().getKey();

                                // creating user object
                                User u=new User(email,"Alumni");
                                Alum_user user = new Alum_user(email, "Alumni",alum_name,phone_alum,alum_field,job_alum,grad);

                                // pushing user to 'users' node using the userId
                                mDatabase.child(userId).setValue(u);
                                uDatabase.child(userIdalum).setValue(user);
                                if (!task.isSuccessful()) {
                                    if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."))
                                    {     Snackbar snackbar = Snackbar
                                            .make(v, "Email not valid , enter again", Snackbar.LENGTH_LONG);

                                        snackbar.show();}
                                    else{
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();}
                                } else {
                                    startActivity(new Intent(Alumni_Register.this, Alumni_mainPage.class));
                                    finish();
                                }
                            }
                        });

            }
        });





    }
}
