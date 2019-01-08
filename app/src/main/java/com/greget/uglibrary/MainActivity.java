package com.greget.uglibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greget.uglibrary.Common.Common;
import com.greget.uglibrary.Model.Users;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    TextView textNama, npm;
    String gender="";
    ImageView avatar;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNama = (TextView)findViewById(R.id.textName);
        npm = (TextView)findViewById(R.id.textNPM);
        avatar = (ImageView)findViewById(R.id.avatar);

        //        init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        TypedArray avatarImg = getResources().obtainTypedArray(R.array.genderImg);
        textNama.setText(Common.currentUsers.getNama());
        npm.setText(Common.currentUsers.getNpm());
        gender = Common.currentUsers.getJkel().toString();
        if(gender.equals("pria")){
            avatar.setImageResource(avatarImg.getResourceId(0,-1));
        }
        if(gender.equals("wanita")){
            avatar.setImageResource(avatarImg.getResourceId(1,-1));
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.mainGrid);

        setSingleEvent(gridLayout);

    }

    private void setSingleEvent(GridLayout gridLayout) {

        for (int i=0;i<gridLayout.getChildCount();i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0){
                        Intent booking = new Intent(v.getContext(),LokerView.class);
                        startActivity(booking);
                    }
                }
            });

        }
    }
}
