package com.example.mikaelibain.androidquickorder.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mikaelibain.androidquickorder.Interface.ItemClickListener;
import com.example.mikaelibain.androidquickorder.R;



/**
 * Created by Alice bain on 2/27/2018.
 */

public class Restaurant_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView restaurant_name;
    public ImageView restaurant_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public Restaurant_View_Holder (View itemView) {
        super(itemView);
        restaurant_name = (TextView)itemView.findViewById(R.id.food_name);
        restaurant_image = (ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
