package com.greget.uglibrary.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.greget.uglibrary.Interface.ItemClickListener;
import com.greget.uglibrary.R;


public class LokerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView TxtLokerId,TxtLokerStatus;
    private ItemClickListener itemClickListener;


    public LokerViewHolder(@NonNull View itemView) {
        super(itemView);

        TxtLokerId = (TextView)itemView.findViewById(R.id.name_loker);
        TxtLokerStatus = (TextView)itemView.findViewById(R.id.status_loker);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
