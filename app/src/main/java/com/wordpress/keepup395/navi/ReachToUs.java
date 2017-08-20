package com.wordpress.keepup395.navi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReachToUs extends AppCompatActivity {
    String text = " 2ndFour  \n\n Flat no: B-301\n  Elegance Apartment, \n" +
            " Patia Staion Road\n Bhubaneswar, Odisha, 751024 \n Contact :+91 96925 33223 \n email: support@twondfour.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_to_us);

        TextView textView = (TextView) findViewById(R.id.reach);
        textView.setText(text);
    }
}
