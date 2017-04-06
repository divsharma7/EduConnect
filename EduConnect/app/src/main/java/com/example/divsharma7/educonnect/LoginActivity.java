package com.example.divsharma7.educonnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.divsharma7.educonnect.Alumni.Alumni_mainPage;
import com.example.divsharma7.educonnect.Faculty.Fac_mainPage;
import com.example.divsharma7.educonnect.Student.Student_mainPage;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    private Button btnSignup, btnLogin, btnReset; int flag;
    private String selected;int sp_position; String user_type;
    private GoogleApiClient mGoogleApiClient;



    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,  this/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, Settings.class));
            finish();
        }
        FirebaseUser user;



        // set the view now
        setContentView(R.layout.activity_login);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_dropdown_item);
//link the adapter to the spinner
        final Spinner contextChooser = (Spinner) findViewById(R.id.user_type_spinner);
        contextChooser.setPrompt("Enter user type");
        contextChooser.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserType_select.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener(){


                                                                 @Override
                                                                 public void onClick(View v) {
                                                                     switch (v.getId()) {
                                                                         case R.id.sign_in_button:
                                                                             signIn();
                                                                             break;

                                                                     }
                                                             }});


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                flag=0;
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
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
                                                    if(selected.equals(user_type))
                                                    { //   Toast.makeText(LoginActivity.this,"HI",Toast.LENGTH_SHORT).show();
                                                        flag=1;
                                                        break;
                                                    }
                                                }}

                                        System.out.println("Flag is :" + flag);
                                          if(flag==0)
                                        { Toast.makeText(LoginActivity.this, "You have entered the wrong user type ",Toast.LENGTH_LONG).show();}


                                        else{
                                            switch (selected) {
                                                case "Alumni":


                                                    Intent intent = new Intent(LoginActivity.this, Alumni_mainPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                    break;
                                                case "Faculty":

                                                    Intent i = new Intent(LoginActivity.this, Fac_mainPage.class);
                                                    startActivity(i);
                                                    finish();
                                                    break;
                                                case "Student":
                                                    startActivity(new Intent(LoginActivity.this,Student_mainPage.class));

                                            }
                                        }}

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // ...
                                        }
                                    });


                                }
                            }
                        });
            }
        });




        contextChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selected = contextChooser.getSelectedItem().toString();

            //    Toast.makeText(LoginActivity.this,selected,Toast.LENGTH_SHORT).show();

            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("SignInActivity", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            System.out.print( acct.getDisplayName());
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void updateUI(boolean b)
    {

        if(b==true)
        {  /*Intent intent = new Intent(LoginActivity.this, Settings.class);
            startActivity(intent);
             finish();*/
             }
        else{

            Toast.makeText(LoginActivity.this,"Google Authentication Failed",Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

