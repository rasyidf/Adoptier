package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.rasyidf.adoptier.R;

public class PetContentActivity extends AppCompatActivity {

  String phoneNumbers;
  private TextView petType;
  private TextView petName;
  private TextView petAge;
  private TextView petCity;
  private DatabaseReference mDatabase;
  private TextView petInfo;
  private TextView userName;
  private ImageView petImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pet_content);

    petType = (TextView) findViewById(R.id.petType);
    petName = (TextView) findViewById(R.id.petName);
    petAge = (TextView) findViewById(R.id.petAge);
    petCity = (TextView) findViewById(R.id.petCity);
    petInfo = (TextView) findViewById(R.id.petInfo);
    petImage = (ImageView) findViewById(R.id.petImage);
    userName = (TextView) findViewById(R.id.userName);


    Intent intent = getIntent();

    String type = intent.getStringExtra("type");
    String name = intent.getStringExtra("name");
    String age = intent.getStringExtra("age");
    String city = intent.getStringExtra("city");
    String info = intent.getStringExtra("info");
    String image = intent.getStringExtra("image");
    String userNames = intent.getStringExtra("fullName");
    phoneNumbers = intent.getStringExtra("phoneNumber");


    petType.setText(type);
    petName.setText(name);
    petAge.setText(age);
    petCity.setText(city);
    petInfo.setText(info);
    userName.setText(userNames);


    Glide.with(getApplicationContext()).load(image)
            .into(petImage);


  }

  public void btnPhone(View view) {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:" + phoneNumbers));
    startActivity(intent);
  }

  public void whatsappButton(View view) {
    Uri uri = Uri.parse("smsto:" + phoneNumbers);
    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
    i.setPackage("com.whatsapp");
    startActivity(Intent.createChooser(i, ""));
  }
}
