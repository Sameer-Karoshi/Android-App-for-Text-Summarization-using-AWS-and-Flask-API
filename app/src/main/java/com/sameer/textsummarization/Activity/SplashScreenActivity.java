package com.sameer.textsummarization.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sameer.textsummarization.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Using handler with postDelayed called runnable run method
        new Handler().postDelayed(() -> {

            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            // close this activity
            finish();

        }, 2*1000); // wait for 2 seconds
    }
}