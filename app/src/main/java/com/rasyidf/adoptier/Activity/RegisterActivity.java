package com.rasyidf.adoptier.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rasyidf.adoptier.Objects.User;
import com.rasyidf.adoptier.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "AddToDataBase";
    private TextInputLayout mMail,mPassword,fullName,phoneNumber;
    private EditText dateOfBirth;
    private Button btnCity;
    FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String uriDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFireBaseDatabase.getReference().child("adoptier").child("user");
        final FirebaseUser user = mAuth.getCurrentUser();

        mMail = (TextInputLayout) findViewById(R.id.etName);
        mPassword = (TextInputLayout) findViewById(R.id.etPass);
        fullName = (TextInputLayout)findViewById(R.id.etFullName);
        dateOfBirth = (EditText) findViewById(R.id.etDateOfBirth);
        phoneNumber = (TextInputLayout)findViewById(R.id.etPhoneNumber);
        btnCity = (Button)findViewById(R.id.btCity);

        final AlertDialog.Builder cityDialog = new AlertDialog.Builder(this);


        btnCity.setOnClickListener(view -> {
            cityDialog.setTitle("Pilih kota");
            final String[] city = getResources().getStringArray(R.array.city);
            cityDialog.setItems(city, (dialogInterface, i) -> btnCity.setText(city[i++]));

            cityDialog.create();
            cityDialog.show();
        });
    }

    public void register(View view) {

        String userName = mMail.getEditText().getText().toString().trim();
        String userPassword = mPassword.getEditText().getText().toString().trim();
        String fullNames = fullName.getEditText().getText().toString().trim();
        String city = btnCity.getText().toString();
        String dateOfBirths = dateOfBirth.getText().toString().trim();
        String phoneNumbers = phoneNumber.getEditText().getText().toString().trim();



        final User user = new User(userName,userPassword,fullNames,city,dateOfBirths,phoneNumbers,uriDownload);


        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)&&
                !TextUtils.isEmpty(fullNames)&& !TextUtils.isEmpty(city)&& !TextUtils.isEmpty(dateOfBirths)&&!TextUtils.isEmpty(phoneNumbers)){


            mAuth.createUserWithEmailAndPassword(user.getUserName(),user.getUserPassword()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    DatabaseReference databaseReference = mRef.child("adoptier").child("users").child(userId);

                    databaseReference.child("userName").setValue(user.getUserName());
                    databaseReference.child("password").setValue(user.getUserPassword());
                    databaseReference.child("fullName").setValue(user.getFullName());
                    databaseReference.child("city").setValue(user.getCity());
                    databaseReference.child("dateOfBirth").setValue(user.getDateOfBirth());
                    databaseReference.child("phoneNumber").setValue(user.getPhoneNumber());
                    databaseReference.child("uriDownload").setValue(user.getProfileImageKey());

                    Toast.makeText(RegisterActivity.this,"Berhasil mendaftar",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"Pendaftaran tidak berhasil",Toast.LENGTH_LONG).show();

                }
            });
            mMail.getEditText().setText("");
            mPassword.getEditText().setText("");
            fullName.getEditText().setText("");
            dateOfBirth.setText("");
            phoneNumber.getEditText().setText("");
        }else{
            Toast.makeText(this,"Tidak boleh ada item kosong",Toast.LENGTH_SHORT).show();
        }


        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }

    }

}
