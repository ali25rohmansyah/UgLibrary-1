package com.greget.uglibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greget.uglibrary.Common.Common;
import com.greget.uglibrary.Model.Users;

import io.paperdb.Paper;

import static com.greget.uglibrary.R.color.purple;

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView = findViewById(R.id.welcome);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_regist = findViewById(R.id.btn_reg);



        //        init paper
        Paper.init(this);

        //      check remember
        String npm = Paper.book().read(Common.USER_KEY);
        String pw = Paper.book().read(Common.PW_KEY);
        if (npm != null && pw != null){
            if (!npm.isEmpty() && !pw.isEmpty()){
                Login(npm,pw);
            }

        }

        String text = "Welcome to UG Library";

        SpannableString ss = new SpannableString(text);

        @SuppressLint("ResourceAsColor") ForegroundColorSpan fcsPurple = new ForegroundColorSpan(R.color.purple);

        ss.setSpan(fcsPurple,11,21,Spanned.SPAN_COMPOSING);

        textView.setText(ss);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(splash.this,
                        login.class);
                startActivity(loginpage);

            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registpage = new Intent(splash.this,
                        register.class);
                startActivity(registpage);

            }
        });
    }

    private void Login(final String npm, final String pw) {
        //        init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                        check exist user
                if (dataSnapshot.child(npm).exists()) {

//                        Get user information
                    Users users = dataSnapshot.child(npm).getValue(Users.class);
                    if (users.getPass().equals(pw)) {
                        Intent home = new Intent(splash.this,
                                MainActivity.class);
                        startActivity(home);
                        users.setNpm(npm);
                        Common.currentUsers = users;
                    } else {
                        Toast.makeText(splash.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(splash.this, "User not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
