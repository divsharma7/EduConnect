package com.example.divsharma7.educonnect.modelclasses;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.divsharma7.educonnect.MaterialColorPalette;

/**
 * Created by divsharma7 on 1/4/17.
 */
public  class Feeder {
    private String mName;
    private String mMessage;
    private String mUid;

    int color = MaterialColorPalette.getRandomColor("500");
    private TextDrawable mdrawable ;
    public Feeder() {
        // Needed for Firebase
    }

    public Feeder(String name, String message, String uid ) {
        mName = name;
        mMessage = message;
        mUid = uid;
        
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getUid() {
        return mUid;
    }
    public TextDrawable getDrawable(){
        int color = MaterialColorPalette.getRandomColor("500");
         mdrawable = TextDrawable.builder().buildRound(mUid.toString().toUpperCase().charAt(0) + "", color);
        return mdrawable; }



    public void setUid(String uid) {
        mUid = uid;
    }
}