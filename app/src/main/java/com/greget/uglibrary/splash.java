package com.greget.uglibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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

import static com.greget.uglibrary.R.color.purple;

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView = findViewById(R.id.welcome);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_regist = findViewById(R.id.btn_reg);


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
                finish();
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registpage = new Intent(splash.this,
                        register.class);
                startActivity(registpage);
                finish();
            }
        });
    }

}
