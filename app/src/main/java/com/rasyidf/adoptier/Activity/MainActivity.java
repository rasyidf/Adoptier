package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microsoft.fluentui.widget.BottomNavigationView;
import com.rasyidf.adoptier.Fragment.AreaSearchFragment;
import com.rasyidf.adoptier.Fragment.NewPetsFragment;
import com.rasyidf.adoptier.Fragment.SearchPetFragment;
import com.rasyidf.adoptier.R;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

  FrameLayout viewpager;
  FirebaseUser firebaseUser;
  private ImageView iconImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    myRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        if (firebaseUser != null) {
          String key = firebaseUser.getUid();

          Map<String, String> map = (Map<String, String>) dataSnapshot.child("adoptier").child("user").child(key).getValue();

          if (map != null) {
            String uriDownloads = map.get("uriDownload");
            Glide.with(getApplicationContext()).load(uriDownloads).into(iconImageView);
          }
        }

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Log.w("", "Failed to read value.", databaseError.toException());
      }
    });

    BottomNavigationView bottomNav = findViewById(R.id.main_nav);

    openFragment(new NewPetsFragment());
    bottomNav.setOnItemSelectedListener(v -> {
      int id = v.getItemId();
      switch (id) {

        case R.id.mnuHome:
          openFragment(new NewPetsFragment());
          return true;

        case R.id.mnuSearch:
          openFragment(new AreaSearchFragment());
          return true;

        case R.id.mnuInfo:
          openFragment(new SearchPetFragment());
          return true;

        case R.id.mnuProfile:
          Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
          startActivity(intent);
          return true;
        default:
          return false;
      }
    });


  }

  void openFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.frameLayout, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {


      default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);

    }
  }

}
