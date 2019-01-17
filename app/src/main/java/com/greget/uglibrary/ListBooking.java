package com.greget.uglibrary;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greget.uglibrary.Common.Common;
import com.greget.uglibrary.Model.BookingModel;

public class ListBooking extends AppCompatActivity {

    TextView list_user,list_npm,list_loker,list_jam;

    FirebaseDatabase database;
    DatabaseReference Booking;
    String user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_booking);

        list_user = (TextView)findViewById(R.id.list_user);
        list_npm = (TextView)findViewById(R.id.list_npm);
        list_loker = (TextView)findViewById(R.id.list_loker);
        list_jam = (TextView)findViewById(R.id.list_jam);

        user = Common.currentUsers.getNpm().toString();

        database = FirebaseDatabase.getInstance();
        Booking = database.getReference("Booking");

        Booking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    BookingModel bookingModel = dataSnapshot.child(user).getValue(BookingModel.class);
                    list_npm.setText(bookingModel.getNpm());
                    list_user.setText(bookingModel.getNama());
                    list_loker.setText(bookingModel.getLoker());
                    list_jam.setText(bookingModel.getWaktu());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
