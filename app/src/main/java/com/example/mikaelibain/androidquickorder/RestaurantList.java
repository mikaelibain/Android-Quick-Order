package com.example.mikaelibain.androidquickorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mikaelibain.androidquickorder.Interface.ItemClickListener;
import com.example.mikaelibain.androidquickorder.Model.Food;
import com.example.mikaelibain.androidquickorder.Model.Restaurant_Model;
import com.example.mikaelibain.androidquickorder.ViewHolder.FoodViewHolder;
import com.example.mikaelibain.androidquickorder.ViewHolder.Restaurant_View_Holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RestaurantList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    FirebaseRecyclerAdapter<Restaurant_Model, Restaurant_View_Holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase database
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Restaurant");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Restaurant_Model, Restaurant_View_Holder>(Restaurant_Model.class,
                R.layout.food_item,
                Restaurant_View_Holder.class,
                foodList.orderByChild("Restaurant_MenuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(Restaurant_View_Holder viewHolder, Restaurant_Model model, int position) {
                viewHolder.restaurant_name.setText(model.getRestaurant_Name());
                Picasso.with(getBaseContext()).load(model.getRestaurant_Image()).into(viewHolder.restaurant_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(RestaurantList.this, Restaurant.class);
                        foodDetail.putExtra("RestaurantId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }

                });
               }
        };
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
        }
    }

