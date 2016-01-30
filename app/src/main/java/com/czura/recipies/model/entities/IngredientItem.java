package com.czura.recipies.model.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class IngredientItem extends Model {
    @Column(name = "ingredient", onDelete = Column.ForeignKeyAction.CASCADE)
    public Ingredient ingredient;

    @Column(name = "item", onDelete = Column.ForeignKeyAction.CASCADE)
    public Item bar;

    public List<Ingredient> ingredients() {
        return getMany(Ingredient.class, "ingredient");
    }
    public List<Item> items() {
        return getMany(Item.class, "item");
    }
}
