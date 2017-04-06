package com.example.divsharma7.educonnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.*;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

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


public class Tab_Alumni extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<String> grad;
    List<String> curr_com;
    List<String> curr_field = new ArrayList<String>();
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ExpandableListView myexpview;
    public static String cat_type;
    public static String valueofcat;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String user_type;



    public Tab_Alumni() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View rootView = inflater.inflate(R.layout.fragment_tab__alumni, container, false);
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

                    case "Grad School":
                        System.out.println(grad.get(childPosition));
                        valueofcat=grad.get(childPosition);

                         break;
                    case "Current Company":
                        valueofcat=curr_com.get(childPosition);
                        break;
                    case "Job Profile":
                        valueofcat=curr_field.get(childPosition);
                        break;
                }


                Intent intent=new Intent(getContext(),Specific_users.class);
                intent.putExtra("mycontext","Alumni");
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
        listDataHeader.add("Grad School");
        listDataHeader.add("Current Company");
        listDataHeader.add("Job Profile");
        listDataChild = new HashMap<String, List<String>>();
        DatabaseReference mDatabase;
        mDatabase= FirebaseDatabase.getInstance().getReference("Alum_users");
         grad = new ArrayList<String>();
          curr_com = new ArrayList<String>();
        curr_field = new ArrayList<String>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String e_mail = (String) messageSnapshot.child("email").getValue();
                    String gradschool = (String) messageSnapshot.child("grad_school").getValue();
                    String company=(String)messageSnapshot.child("job").getValue();
                    String field=(String)messageSnapshot.child("field").getValue();

                    //  System.out.println(e_mail + "  " + user_type);
                    //  System.out.println(email + " " + selected);

                    if(!grad.contains(gradschool))
                    {
                        grad.add(gradschool);
                    }
                    if(!curr_com.contains((company)))
                    {
                        curr_com.add(company);
                    }
                    if (!curr_field.contains(field))
                    {

                        curr_field.add(field);
                    }

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        listDataChild.put(listDataHeader.get(0), grad); // Header, Child data
        listDataChild.put(listDataHeader.get(1), curr_com);
        listDataChild.put(listDataHeader.get(2), curr_field);
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
