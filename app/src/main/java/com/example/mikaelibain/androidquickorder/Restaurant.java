package com.example.mikaelibain.androidquickorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikaelibain.androidquickorder.Interface.ItemClickListener;
import com.example.mikaelibain.androidquickorder.Model.Food;
import com.example.mikaelibain.androidquickorder.Model.Restaurant_Model;
import com.example.mikaelibain.androidquickorder.ViewHolder.FoodViewHolder;
import com.example.mikaelibain.androidquickorder.ViewHolder.Restaurant_View_Holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Restaurant extends AppCompatActivity {

    TextView restaurant_name, restaurant_description;
    ImageView  restaurant_image;
    FirebaseDatabase database;
    DatabaseReference restaurants, foods;



    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    Restaurant_Model currentRes;

    String restaurantId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Restaurant Description");
        setSupportActionBar(toolbar);

        //Firebase connection
        database = FirebaseDatabase.getInstance();
        restaurants = database.getReference("Restaurant");
        foods = database.getReference("Food");

        restaurant_name = findViewById(R.id.restaurant_name);
        restaurant_description = findViewById(R.id.restaurant_description);
        restaurant_image = findViewById(R.id.restaurant_image);





        //Load menu
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        recycler_menu.setAdapter(adapter);



        if (getIntent() != null)
            restaurantId = getIntent().getStringExtra("RestaurantId");
        if (!restaurantId.isEmpty()) {
            getDetailFood(restaurantId);
            loadMenu(restaurantId);
        }
    }
    private void getDetailFood(final String restaurantId) {
        restaurants.child(restaurantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentRes = dataSnapshot.getValue(Restaurant_Model.class);

                restaurant_name.setText(currentRes.getRestaurant_Name());
                restaurant_description.setText(currentRes.getRestaurant_Description());
                Picasso.with(getBaseContext()).load(currentRes.getRestaurant_Image())
                        .into(restaurant_image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void loadMenu(String restaurantId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.restaurant_item,
                FoodViewHolder.class,
                foods.orderByChild("MenuId").equalTo(restaurantId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.name.setText(model.getName());
                viewHolder.description.setText(model.getDescription());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(Restaurant.this, Activity_Food_Detail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
            }

                });
            }
        };
        Log.d("TAG",""+adapter.getItemCount());
        recycler_menu.setAdapter(adapter);
    }
}