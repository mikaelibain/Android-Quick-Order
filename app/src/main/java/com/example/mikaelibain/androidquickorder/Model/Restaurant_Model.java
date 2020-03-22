package com.example.mikaelibain.androidquickorder.Model;

/**
 * Created by Mikaeli Bain on 3/25/2018.
 */

public class Restaurant_Model {
    private String Restaurant_Name, Restaurant_Image, Restaurant_Description, Restaurant_MenuId;

    public Restaurant_Model() {
    }

    public Restaurant_Model(String restaurant_name, String restaurant_image, String restaurant_description, String restaurant_menuId) {
        Restaurant_Name = restaurant_name;
        Restaurant_Image = restaurant_image;
        Restaurant_Description = restaurant_description;
        Restaurant_MenuId = restaurant_menuId;

    }

    public String getRestaurant_Name() {
        return Restaurant_Name;
    }

    public void setRestaurant_Name(String restaurant_Name) {
        Restaurant_Name = restaurant_Name;
    }

    public String getRestaurant_Image() {
        return Restaurant_Image;
    }

    public void setRestaurant_Image(String restaurant_Image) {
        Restaurant_Image = restaurant_Image;
    }

    public String getRestaurant_Description() {
        return Restaurant_Description;
    }

    public void setRestaurant_Description(String restaurant_Description) {
        Restaurant_Description = restaurant_Description;
    }

    public String getRestaurant_MenuId() {
        return Restaurant_MenuId;
    }

    public void setRestaurant_MenuId(String restaurant_MenuId) {
        Restaurant_MenuId = restaurant_MenuId;
    }
}
