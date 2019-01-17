package com.greget.uglibrary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greget.uglibrary.Common.Common;
import com.greget.uglibrary.Interface.ItemClickListener;
import com.greget.uglibrary.Model.LokerModel;
import com.greget.uglibrary.ViewHolder.LokerViewHolder;

public class LokerView extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Loker,Booking;

    RecyclerView recycler_loker;
    RecyclerView.LayoutManager layoutManager;

    String lokerID = "";
    String status = "";
    FirebaseRecyclerAdapter<LokerModel,LokerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loker_view);


        database = FirebaseDatabase.getInstance();
        Loker = database.getReference("Loker");
        Booking = database.getReference("Booking");

        Bundle bundle = new Bundle();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarLoker);
        toolbar.setTitle("Pilih Loker");
        setSupportActionBar(toolbar);
        
        recycler_loker = (RecyclerView)findViewById(R.id.Recycler_loker);
        recycler_loker.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recycler_loker.setLayoutManager(layoutManager);
        
        loadmenu();

    }

    private void loadmenu() {

        adapter = new FirebaseRecyclerAdapter<LokerModel, LokerViewHolder>(LokerModel.class,R.layout.loker_item,LokerViewHolder.class,Loker) {
            @Override
            protected void populateViewHolder(final LokerViewHolder viewHolder, LokerModel model, int position) {
                viewHolder.TxtLokerId.setText(model.getLokerID());
                viewHolder.TxtLokerStatus.setText(model.getStatus());

                final LokerModel clickItem = model;
                status = clickItem.getStatus().toString();
                if (status.equals("Tersedia")){
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            lokerID = clickItem.getLokerID().toString();
                            Intent intent = new Intent(LokerView.this,booking.class);
                            intent.putExtra("lokerID",lokerID);
                            startActivity(intent);

                        }
                    });

            }

                if (status.equals("Penuh")){
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(LokerView.this, "Maaf Loker tidak tersedia", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


                if (status.equals("Penuh")){
                    viewHolder.TxtLokerStatus.setText(status);
                    viewHolder.TxtLokerStatus.setBackgroundColor(LokerView.this.getResources().getColor(R.color.full));
                }

                Booking.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(Common.currentUsers.getNpm()).exists()) {
                            viewHolder.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onClick(View view, int position, boolean isLongClick) {
                                    Toast.makeText(LokerView.this, "Anda telah menyewa 1 loker", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        };
        recycler_loker.setAdapter(adapter);


    }
}
