package com.example.divsharma7.educonnect.Student;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.divsharma7.educonnect.LoginActivity;
import com.example.divsharma7.educonnect.R;
import com.example.divsharma7.educonnect.ResetPasswordActivity;
import com.example.divsharma7.educonnect.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Stud_register extends AppCompatActivity {

    private EditText inputEmail, inputPassword,phonenum,major,name;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth; String selected;
    int sp_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.stud_name);
        major=(EditText)findViewById(R.id.stud_branch);
        phonenum=(EditText)findViewById(R.id.stud_phone_number);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stud_register.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stud_register.this,LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String stud_name=name.getText().toString().trim();
                final String stud_phone=phonenum.getText().toString().trim();
                final String stud_major=major.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(stud_major)){
                    Toast.makeText(getApplicationContext(), "Enter Major!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(stud_phone.length()<10){
                    Toast.makeText(getApplicationContext(), "Enter 10 digit phone number!", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Stud_register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Stud_register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                DatabaseReference uDatabase = FirebaseDatabase.getInstance().getReference("Stud_users");
                                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("users");

                                // Creating new user node, which returns the unique key value
                                // new user node would be /users/$userid/
                                String userId = mDatabase.push().getKey();
                                String userIdstud=uDatabase.push().getKey();

                                // creating user object
                                User u=new User(email,"Student");
                                Stud_user user = new Stud_user(email, "Student",stud_name,stud_phone,stud_major);

                                // pushing user to 'users' node using the userId
                                mDatabase.child(userId).setValue(u);
                                uDatabase.child(userIdstud).setValue(user);
                                if (!task.isSuccessful()) {
                                    if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."))
                                    {     Snackbar snackbar = Snackbar
                                            .make(v, "Email not valid , enter again", Snackbar.LENGTH_LONG);

                                        snackbar.show();}
                                    else{
                                    Toast.makeText(Stud_register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();}
                                } else {
                                    startActivity(new Intent(Stud_register.this, Student_mainPage.class));
                                    finish();
                                }
                            }
                        });

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_dropdown_item);
//link the adapter to the spinner






    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}