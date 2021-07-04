package com.rasyidf.adoptier.Objects;

import android.app.Application;

import com.google.firebase.FirebaseApp;


public class FireBaseInit extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    FirebaseApp.initializeApp(this);
  }
}
