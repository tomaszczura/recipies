package com.czura.recipies.model.entities;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "IngredientItems", id = BaseColumns._ID)
public class IngredientItem extends Model {
    public static final String TABLE_NAME = "IngredientItems";
    public static final String INGREDIENT_COLUMN = "ingredient";
    public static final String ITEM_COLUMN = "item";

    @Column(name = INGREDIENT_COLUMN, onDelete = Column.ForeignKeyAction.CASCADE)
    public Ingredient ingredient;

    @Column(name = ITEM_COLUMN, onDelete = Column.ForeignKeyAction.CASCADE)
    public Item item;

    public IngredientItem(Ingredient ingredient, Item item) {
        this.ingredient = ingredient;
        this.item = item;
    }
}