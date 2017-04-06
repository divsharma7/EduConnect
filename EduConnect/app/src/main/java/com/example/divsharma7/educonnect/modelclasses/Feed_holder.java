package com.example.divsharma7.educonnect.modelclasses;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.divsharma7.educonnect.MaterialColorPalette;
import com.example.divsharma7.educonnect.R;

/**
 * Created by divsharma7 on 1/4/17.
 */
public  class Feed_holder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private final TextView mNameField;
    private final TextView mTextField;
    private final TextView muid;
    public String[] emails;int i;

    CardView cv;
    public static String user_email;
    public static ImageView posterimage;




    public Feed_holder(View itemView) {
        super(itemView);
        cv=(CardView)itemView.findViewById(R.id.cardView);
        mNameField = (TextView) itemView.findViewById(R.id.title);
        mTextField = (TextView) itemView.findViewById(R.id.message);
        muid=(TextView)itemView.findViewById(R.id.userid);
        posterimage=(ImageView)itemView.findViewById(R.id.imageView);


        emails=new String[100];
        i=0;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email=muid.getText().toString().trim();
            }
        });




    }

    public void setName(String name) {
        mNameField.setText(name);
    }

    public void setText(String text) {
        mTextField.setText(text);
    }
    public void setUid(String mail){muid.setText(mail);
    emails[i]=mail;
    i++;}
    public void setDrawable(TextDrawable d){ posterimage.setImageDrawable(d); }
    @Override
    public void onClick(View v) {
        user_email=muid.getText().toString().trim();
    }
}