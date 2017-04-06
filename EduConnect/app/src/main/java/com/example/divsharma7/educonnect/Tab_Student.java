package com.example.divsharma7.educonnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.divsharma7.educonnect.utility.ExpandableListAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Tab_Student extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String cat_type;
    String valueofcat;

    ExpandableListView myexpview;
     List<String> major= new ArrayList<String>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Tab_Student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_Alumni.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_Alumni newInstance(String param1, String param2) {
        Tab_Alumni fragment = new Tab_Alumni();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View rootView = inflater.inflate(R.layout.fragment_tab__student, container, false);
        myexpview=(ExpandableListView)rootView.findViewById(R.id.myexpview);

        prepareListData();
        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        myexpview.setAdapter(listAdapter);

        myexpview.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        myexpview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public static final String TAG = "blah";

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition,long id) {

                System.out.println(listDataHeader.get(groupPosition));
                cat_type=listDataHeader.get(groupPosition);


                switch (cat_type){

                    case "Major":
                        //     System.out.println(department.get(childPosition));
                        valueofcat=major.get(childPosition);

                        break;


                }


                Intent intent=new Intent(getContext(),Specific_users.class);
                intent.putExtra("mycontext","Student");
                intent.putExtra("cat_type",cat_type);
                intent.putExtra("valueofcat",valueofcat);
                startActivity(intent);

                Log.d(TAG,"I got clicked childPosition:["+childPosition+"] groupPosition:["+groupPosition+"] id:["+id+"]");
                return true;
            }
        });







        return rootView;
    }

    public void prepareListData()
    {
        listDataHeader = new ArrayList<String>();
        listDataHeader.add("Major");


        listDataChild = new HashMap<String, List<String>>();
        DatabaseReference mDatabase;
        mDatabase= FirebaseDatabase.getInstance().getReference("Stud_users");
         major= new ArrayList<String>();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String e_mail = (String) messageSnapshot.child("email").getValue();
                    String maj = (String) messageSnapshot.child("major").getValue();


                    //  System.out.println(e_mail + "  " + user_type);
                    //  System.out.println(email + " " + selected);

                    if(!major.contains(maj))
                    {
                        major.add(maj);
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        listDataChild.put(listDataHeader.get(0), major); // Header, Child data


    }












    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
