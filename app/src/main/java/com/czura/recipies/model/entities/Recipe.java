package com.czura.recipies.model.entities;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Recipes", id = BaseColumns._ID)
public class Recipe extends Model{
    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private int id;

    @Column(name = "title")
    @SerializedName("title")
    private String title;

    @Column(name = "description")
    @SerializedName("description")
    private String description;

    @SerializedName("images")
    private List<ImageData> images;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        if(images == null || images.size() == 0){
            images = getMany(ImageData.class, "recipe");
        }
        if(images.size() > 0){
            return images.get(0).getImageUrl();
        }
        return "";
    }

    public List<Ingredient> getIngredients() {
        if(ingredients == null || ingredients.size() == 0){
            ingredients = getMany(Ingredient.class, "recipe");
        }
        return ingredients;
    }

    public void saveWithRelations(){
        save();

        for (ImageData imageData : images) {
            imageData.setRecipe(this);
            imageData.save();
        }

        for (Ingredient ingredient : ingredients) {
            ingredient.setRecipe(this);
            ingredient.saveWithRelations();
        }
    }

    public static List<Recipe> getAllRecipes(){
        return new Select().from(Recipe.class).execute();
    }
}
