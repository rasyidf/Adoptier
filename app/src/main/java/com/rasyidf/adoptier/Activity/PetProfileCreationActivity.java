package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edmodo.cropper.CropImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rasyidf.adoptier.Objects.Pet;
import com.rasyidf.adoptier.R;

import java.util.HashMap;
import java.util.Map;

public class PetProfileCreationActivity extends AppCompatActivity {

  public String uriDownload;
  CropImageView cropImageView;
  Handler handler;
  private EditText petName, petMoreInfo;
  private DatabaseReference mDatabase;
  private FirebaseAuth mAuth;
  private FirebaseUser firebaseUser;
  private StorageReference storageReference;
  private ImageView pictureLogo;
  private TextView petTvAge;
  private TextView petTvChoice;
  private TextView tvPetCity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_pet_profile_creation);

    mAuth = FirebaseAuth.getInstance();

    mDatabase = FirebaseDatabase.getInstance().getReference();
    storageReference = FirebaseStorage.getInstance().getReference();
    petTvChoice = (TextView) findViewById(R.id.petTvChoice);
    petName = (EditText) findViewById(R.id.etPetname);
    petTvAge = (TextView) findViewById(R.id.petTvAge);
    petMoreInfo = (EditText) findViewById(R.id.etMoreinfo);
    pictureLogo = (ImageView) findViewById(R.id.pictureLogo);
    tvPetCity = (TextView) findViewById(R.id.tvPetCity);


  }

  public void confirm(View view) {

    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    if (firebaseUser != null) {

      String petNames = petName.getText().toString();
      String petAges = petTvAge.getText().toString();
      final String petTypes = petTvChoice.getText().toString();
      String petMoreDetail = petMoreInfo.getText().toString();
      final String petCity = tvPetCity.getText().toString();

      final Pet pet = new Pet(petNames, petAges, petTypes, petMoreDetail, petCity, uriDownload);

      mDatabase.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

          String key = firebaseUser.getUid();

          Map<String, Object> maps = (Map<String, Object>) dataSnapshot.child("adoptier").child("user").child(key).getValue();

          if (maps == null) {

          } else {
            String phoneNumber = maps.get("phoneNumber").toString();
            String fullName = maps.get("fullName").toString();

            Map<String, Object> childupdate = new HashMap<>();
            childupdate.put("phoneNumber", phoneNumber);
            childupdate.put("fullName", fullName);
            mDatabase.child("adoptier").child("Pet").child(firebaseUser.getUid()).updateChildren(childupdate);

          }

          Intent intent = new Intent(PetProfileCreationActivity.this, PetProfileActivity.class);
          startActivity(intent);

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
      mDatabase.child("adoptier").child("Pet").child(firebaseUser.getUid()).setValue(pet);
      mDatabase.child("adoptier").child(petTypes).child(firebaseUser.getUid()).setValue(pet);
      mDatabase.child("adoptier").child(petCity).child(firebaseUser.getUid()).setValue(pet);


    }


    Toast.makeText(this, "Informasi telah berhasil diperbarui", Toast.LENGTH_SHORT).show();


  }

  public void petImageIdCreate(View view) {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                final StorageReference imageCrop = storageReference.child("images/" + resultUri.toString() );
//                imageCrop.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//
//
//                       Task<Uri> downloadUrl = imageCrop.getDownloadUrl();
//                        uriDownload = downloadUrl.toString();
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//                cropImageView.setImageUriAsync(resultUri);
//                pictureLogo.setVisibility(View.INVISIBLE);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    super.onActivityResult(requestCode, resultCode, data);
    Bitmap cropped = cropImageView.getCroppedImage();
    cropImageView.setImageBitmap(cropped);

  }

  public void choosePet(View view) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pilih binatang");
    final String[] petChoice = getResources().getStringArray(R.array.petArray);
    builder.setItems(petChoice, (dialogInterface, i) -> petTvChoice.setText(petChoice[i++]));

    builder.create();
    builder.show();
  }

  public void chooseAge(View view) {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pilih usia (dalam tahun)");
    final String[] petAgeArray = getResources().getStringArray(R.array.petAge);
    builder.setItems(petAgeArray, (dialogInterface, i) -> petTvAge.setText(petAgeArray[i++]));

    builder.create();
    builder.show();
  }


  public void btnCity(View view) {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pilih kota");
    final String[] petCity = getResources().getStringArray(R.array.city);
    builder.setItems(petCity, (dialogInterface, i) -> tvPetCity.setText(petCity[i++]));

    builder.create();
    builder.show();

  }

}









