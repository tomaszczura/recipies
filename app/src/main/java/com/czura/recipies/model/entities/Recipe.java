package com.czura.recipies.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class Recipe {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("images")
    private List<ImageData> images;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        if(images == null || images.size() == 0){
            return "";
        }
        return images.get(0).getImageUrl();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
