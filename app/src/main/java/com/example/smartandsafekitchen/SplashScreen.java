package com.example.smartandsafekitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );
        ImageView imageView = findViewById( R.id.imagelogo );
        Intent i = new Intent(this , MyService.class);
        startService(i);
//        ImageView imageView1 = findViewById( R.id.imagelogo1 );
        Animation animation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.fade );
//        Animation animation1 = AnimationUtils.loadAnimation( getApplicationContext(),R.anim.fade1 );
        imageView.startAnimation( animation );
//        imageView1.startAnimation( animation1 );
        Thread timer = new Thread(  ) {
            @Override
            public void run() {
                try {
                    sleep( 5000 );

                    startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                    finish();




                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();


    }
}
