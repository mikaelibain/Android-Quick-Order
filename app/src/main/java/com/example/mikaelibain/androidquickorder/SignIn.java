package com.example.mikaelibain.androidquickorder;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mikaelibain.androidquickorder.Common.Common;
import com.example.mikaelibain.androidquickorder.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText editPhone, editPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editPhone = (MaterialEditText)findViewById(R.id.editPhone);
        btnSignIn = findViewById(R.id.btnSignIn);

        //Stuff for the firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check to see if user exists in the database
                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                    //Get User Information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                    user.setPhone(editPhone.getText().toString());//set phone
                    if(user.getPassword().equals(editPassword.getText().toString()))
                    {
                        Intent homeIntent = new Intent(SignIn.this,Home.class);
                        Common.currentUser = user;
                        startActivity(homeIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "The user does not exist in the Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
