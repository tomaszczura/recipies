package com.czura.recipies.model.entities;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Items", id = BaseColumns._ID)
public class Item extends Model{

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private int id;

    @Column(name = "amount")
    @SerializedName("amount")
    private double amount;

    @Column(name = "name")
    @SerializedName("name")
    private String name;

    @Column(name = "ingredient", onDelete = Column.ForeignKeyAction.CASCADE)
    private Ingredient ingredient;

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
