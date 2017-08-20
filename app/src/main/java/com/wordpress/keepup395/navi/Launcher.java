package com.wordpress.keepup395.navi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Launcher extends AppCompatActivity {

    TextView text;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mediaPlayer = MediaPlayer.create(this, R.raw.bikesound);
        //ImageView imageView = (ImageView)findViewById(R.id.imageLogo);
        text = (TextView) findViewById(R.id.text);
        text.setText("2&Four");
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_on);
        text.startAnimation(animation);
        /*Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(6000);
                    Intent c= new Intent(getApplicationContext(),SignIn.class);
                    startActivity(c);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();*/

        /*Drawable backgrounds[] = new Drawable[2];
        Resources res = getResources();
        backgrounds[0] = res.getDrawable(R.drawable.off);
        backgrounds[1] = res.getDrawable(R.drawable.on);

        final TransitionDrawable crossfader = new TransitionDrawable(backgrounds);

        ImageView image = (ImageView)findViewById(R.id.imageBike);
        image.setImageDrawable(crossfader);

        crossfader.startTransition(500);*/
        Thread t2 = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }
        };
        t2.start();
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(6000);
                    mediaPlayer.stop();
                    Intent c = new Intent(getApplicationContext(), CheckNetConnection.class);
                    startActivity(c);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        finish();
        super.onPause();
    }
}
