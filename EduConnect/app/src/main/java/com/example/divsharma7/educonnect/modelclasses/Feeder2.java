package com.example.divsharma7.educonnect.modelclasses;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.divsharma7.educonnect.MaterialColorPalette;

/**
 * Created by divsharma7 on 1/4/17.
 */
public  class Feeder2 {
    private String mName;
    private String mMessage;
    private String mUid;


    public Feeder2() {
        // Needed for Firebase
    }

    public Feeder2(String name, String message, String uid ) {
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



    public void setUid(String uid) {
        mUid = uid;
    }
}