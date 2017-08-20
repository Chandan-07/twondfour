package com.wordpress.keepup395.navi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    TextView textView;

    String longText = "     Vehicle, may it be bike or car they are not just a means of transportation. A biker dreams to explore the world on its bike and for a car lover, the car itself is its whole world.\n\n" +
            "2& Four wishes to serve this mass. We have started with 2 wheeler rental service and soon we will be extending it to 4 wheeler  then bike and car customization .Car Tourism and so on\n\n" +
            "2& Four is the beginning of the beginnings which will not only change the 2 & 4 wheeler culture in Odisha but in whole india.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        textView = (TextView) findViewById(R.id.scroltext);
        textView.setText(longText);

    }

    public void facebookclick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/twondfour/"));
        startActivity(i);
    }

    public void instaclick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/twondfour/"));
        startActivity(i);
    }

    public void twitterclick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/twondfour"));
        startActivity(i);
    }

    public void websiteclick(View view) {
        Intent n = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twondfour.com/"));
        startActivity(n);
    }
}
