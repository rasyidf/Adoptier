package com.rasyidf.adoptier.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.rasyidf.adoptier.R;

public class SplashScreenActivity extends AppCompatActivity {
  final Handler handler = new Handler(Looper.getMainLooper());

  FirebaseAnalytics mFirebaseAnalytics;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FirebaseApp.initializeApp(this);

    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    handler.postDelayed(new Runnable() {
      @Override public void run() {
        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class); startActivity(i);
        finish(); } }, 3000);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash_screen);

  }
}