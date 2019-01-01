package com.greget.uglibrary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greget.uglibrary.Model.Users;

public class register extends AppCompatActivity {

    String gender = "";
    EditText npm_id,nama,password,repassword;
    Button regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        npm_id = (EditText)findViewById(R.id.npm_id);
        nama = (EditText)findViewById(R.id.nama);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.re_password);
        regist = (Button)findViewById(R.id.register);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //        init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().equals(repassword.getText().toString())){
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

//                        check exist user
                            if (dataSnapshot.child(npm_id
                                    .getText().toString()).exists()) {
                                Toast.makeText(register.this, "Account already exists", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Users users = new Users(gender,nama.getText().toString(),password.getText().toString());
                                table_user.child(npm_id.getText().toString()).setValue(users);
                                Toast.makeText(register.this, "Regist Success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(register.this, "Your Password doesn't match", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }



    public void genderClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = "pria";
                    break;
            case R.id.radio_female:
                if (checked)
                    gender = "wanita";
                    break;
        }
    }
}
