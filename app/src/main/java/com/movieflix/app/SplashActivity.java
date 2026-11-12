package com.movieflix.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Netflix-style splash — black + red MovieFlix. */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(0xFF000000);
        TextView logo = new TextView(this);
        logo.setText("MOVIEFLIX");
        logo.setTextColor(0xFFE50914);
        logo.setTextSize(36);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);
        TextView by = new TextView(this);
        by.setText("By FaresCodeX");
        by.setTextColor(0xFFB3B3B3);
        by.setTextSize(13);
        by.setGravity(Gravity.CENTER);
        by.setPadding(0, 24, 0, 0);
        root.addView(logo);
        root.addView(by);
        setContentView(root);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1600);
    }
}
