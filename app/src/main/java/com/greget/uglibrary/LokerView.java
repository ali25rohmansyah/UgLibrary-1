package com.greget.uglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greget.uglibrary.Interface.ItemClickListener;
import com.greget.uglibrary.Model.LokerModel;
import com.greget.uglibrary.ViewHolder.LokerViewHolder;

public class LokerView extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Loker;

    RecyclerView recycler_loker;
    RecyclerView.LayoutManager layoutManager;

    String LokerID = "";
    FirebaseRecyclerAdapter<LokerModel,LokerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loker_view);


        database = FirebaseDatabase.getInstance();
        Loker = database.getReference("Loker");

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
            protected void populateViewHolder(LokerViewHolder viewHolder, LokerModel model, int position) {
                viewHolder.TxtLokerId.setText(model.getLokerID());
                viewHolder.TxtLokerStatus.setText(model.getStatus());

                final LokerModel clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(LokerView.this, "Loke ke - "+clickItem.getLokerID(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycler_loker.setAdapter(adapter);
    }
}
