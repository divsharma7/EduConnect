package com.example.divsharma7.educonnect.Faculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.divsharma7.educonnect.Alumni.User_Profile_alum;
import com.example.divsharma7.educonnect.BrowseUsers;
import com.example.divsharma7.educonnect.NewsFeedUpdate;
import com.example.divsharma7.educonnect.R;
import com.example.divsharma7.educonnect.Settings;
import com.example.divsharma7.educonnect.Student.User_Profile_stu;
import com.example.divsharma7.educonnect.modelclasses.Feed_holder;
import com.example.divsharma7.educonnect.modelclasses.Feeder;
import com.example.divsharma7.educonnect.utility.ItemClickSupport;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fac_mainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseRecyclerAdapter mAdapter; String[] maillist=new String[100]; int i=0;
    String this_email; FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("News-Feed");

          user= FirebaseAuth.getInstance().getCurrentUser();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recycler = (RecyclerView) findViewById(R.id.myrecycler);

        recycler.setHasFixedSize(false);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("newsfeed");
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Feeder, Feed_holder>(Feeder.class, R.layout.row_layout_feed, Feed_holder.class, ref) {
            @Override
            public void populateViewHolder(Feed_holder MessageViewHolder, Feeder chatMessage, int position) {
                MessageViewHolder.setName(chatMessage.getName());
                MessageViewHolder.setText(chatMessage.getMessage());
                MessageViewHolder.setUid(chatMessage.getUid());
                MessageViewHolder.setDrawable(chatMessage.getDrawable());

                //   System.out.println("That is chat message "+chatMessage.getUid());
                maillist[i++]=chatMessage.getUid().toString();
            }
        };
        recycler.setAdapter(mAdapter);

        ItemClickSupport.addTo(recycler).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //   System.out.println("This is mail list " +maillist[position]);

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                this_email=maillist[position];
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
                                // System.out.println("picked email" +this_email);
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

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
        }
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fac_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mypro_fac) {
            this_email=user.getEmail().toString().trim();
            Intent intent=new Intent(getApplicationContext(),User_Profile_fac.class);
            intent.putExtra("this_email",this_email);
            startActivity(intent);
        }
      else if(id==R.id.posttofeed_fac){
            startActivity(new Intent(getApplicationContext(),NewsFeedUpdate.class));
        }
        else if(id==R.id.browse_users){
            startActivity(new Intent(getApplicationContext(), BrowseUsers.class));


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
