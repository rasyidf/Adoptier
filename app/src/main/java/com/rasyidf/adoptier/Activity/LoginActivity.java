package com.rasyidf.adoptier.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rasyidf.adoptier.R;

public class LoginActivity extends AppCompatActivity {

  String userId;
  ProgressDialog progressBar;
  private FirebaseDatabase mFireBaseDatabase;
  private DatabaseReference mRef;
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private FirebaseAnalytics mFirebaseAnalytics;
  private TextInputLayout etUserName, etPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    FirebaseApp.initializeApp(this);

    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    mFireBaseDatabase = FirebaseDatabase.getInstance();

    etUserName = (TextInputLayout) findViewById(R.id.etEmail);
    etPassword = (TextInputLayout) findViewById(R.id.etPass);

    mAuth = FirebaseAuth.getInstance();
    mAuthListener = firebaseAuth -> {
      FirebaseUser userAuth = firebaseAuth.getCurrentUser();

      if (userAuth != null) {
        etUserName.getEditText().setText(userAuth.getEmail());
      } else {
        etUserName.getEditText().setText("");

      }


    };
  }

  public void register(View view) {


    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
    startActivity(intent);
  }


  public void signIn(View view) {

    String userName = etUserName.getEditText().getText().toString();
    String userPassword = etPassword.getEditText().getText().toString();

    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
      if (TextUtils.isEmpty(userName))
        etUserName.setError("username tidak boleh kosong");

      if (TextUtils.isEmpty(userPassword))
        etPassword.setError("password tidak boleh kosong");


    } else {
      mAuth.signInWithEmailAndPassword(userName, userPassword)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "Anda berhasil masuk", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                  } else {
                    Toast.makeText(LoginActivity.this, "Anda gagal masuk", Toast.LENGTH_SHORT).show();
                  }
                }
              });

    }

  }

  @Override
  protected void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);

  }

  @Override
  protected void onStop() {
    super.onStop();

    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }
}
