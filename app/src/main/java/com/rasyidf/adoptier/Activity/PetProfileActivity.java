package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rasyidf.adoptier.R;

import java.util.Map;

public class PetProfileActivity extends AppCompatActivity {

  TextView petName, petAge, petType, petMoreInfo;
  FirebaseUser firebaseUser;
  private DatabaseReference mDatabase;
  private ImageView petImageView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pet_profile_acitvity);

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    mDatabase = firebaseDatabase.getReference();


    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    petName = (TextView) findViewById(R.id.tvPetname);
    petAge = (TextView) findViewById(R.id.tvPetage);
    petType = (TextView) findViewById(R.id.tvPetype);
    petMoreInfo = (TextView) findViewById(R.id.tvPetmoreinfo);
    petImageView = (ImageView) findViewById(R.id.petImageView);

    mDatabase.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (firebaseUser != null) {

          String key = firebaseUser.getUid();

          Map<String, String> map = (Map<String, String>) dataSnapshot.child("adoptier").child("Pet").child(key).getValue();


          if (map == null) {


          } else {

            String petAges = map.get("age");
            String petNames = map.get("name");
            String petMoreInfos = map.get("petMoreInfo");
            String petTypes = map.get("type");
            String uriKey = map.get("uriKey");

            petName.setText(petNames);
            petAge.setText(petAges);
            petType.setText(petTypes);
            petMoreInfo.setText(petMoreInfos);

            Glide.with(getApplicationContext()).load(uriKey)
                    .into(petImageView);


          }


        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }


  public void updatepetprofile(View view) {


    Intent intent = new Intent(this, PetProfileCreationActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onStart() {
    super.onStart();

  }
}
