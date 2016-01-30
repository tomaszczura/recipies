package com.czura.recipies.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class Ingredient {

    @SerializedName("id")
    private int id;

    @SerializedName("elements")
    private List<Item> items;

    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
