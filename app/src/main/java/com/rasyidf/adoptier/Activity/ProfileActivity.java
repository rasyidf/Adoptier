package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rasyidf.adoptier.R;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

  public String uriDownload;
  FirebaseUser firebaseUser;
  StorageReference storageReference;
  FirebaseDatabase mFireBaseDatabase;
  private DatabaseReference databaseReference;
  private TextView tvName, tvDate, tvPhone, tvCity;
  private ImageView profileImage;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    mFireBaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = mFireBaseDatabase.getReference();

    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    storageReference = FirebaseStorage.getInstance().getReference();


    tvName = (TextView) findViewById(R.id.tvName);
    tvDate = (TextView) findViewById(R.id.tvDate);
    tvCity = (TextView) findViewById(R.id.tvCity);
    tvPhone = (TextView) findViewById(R.id.tvPhone);
    profileImage = (ImageView) findViewById(R.id.profileImage);
    mAuth = FirebaseAuth.getInstance();


    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (firebaseUser != null) {
          String key = firebaseUser.getUid();
          Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("adoptier").child("user").child(key).getValue();

          if (map != null) {
            String name = map.get("fullName").toString();
            String date = map.get("dateOfBirth").toString();
            String city = map.get("city").toString();
            String phone = map.get("phoneNumber").toString();
            String uriDownload = map.get("uriDownload").toString();

            tvName.setText(name);
            tvDate.setText(date);
            tvCity.setText(city);
            tvPhone.setText(phone);

            profileImage.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(uriDownload)
                    .into(profileImage);

          }

        }

      }


      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });


  }

  public void cropImageView(View view) {
//        CropImage.activity()
//                .start(this);
//

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                final StorageReference imageCrop = storageReference.child("images/profileImage" + resultUri.toString() );
//                imageCrop.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//
//                 Task<Uri> downloadUrl = imageCrop.getDownloadUrl();
//                     uriDownload = downloadUrl.toString();
//
//                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
//                        Map<String, Object> childUpdates = new HashMap<>();
//                        childUpdates.put("uriDownload",uriDownload);
//                        databaseReference.child("user").child(firebaseUser.getUid()).updateChildren(childUpdates);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//                cropImage.setImageUriAsync(resultUri);
//                profileImage.setVisibility(View.INVISIBLE);
//
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    super.onActivityResult(requestCode, resultCode, data);
//        Bitmap cropped = cropImage.getCroppedImage();
//        cropImage.setImageBitmap(cropped);


  }


  public void petProfileButton(View view) {
    Intent intent = new Intent(this, PetProfileActivity.class);
    startActivity(intent);

  }

  public void updatepetprofile(View view) {
  }
}
