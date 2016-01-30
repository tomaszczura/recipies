package com.czura.recipies.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class Item {

    @SerializedName("id")
    private int id;

    @SerializedName("amount")
    private double amount;

    @SerializedName("name")
    private String name;

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
