package com.czura.recipies.model.entities;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "ImagesData", id = BaseColumns._ID)
public class ImageData extends Model{

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("imboId")
    private String imageId;

    @Column(name = "url")
    @SerializedName("url")
    private String imageUrl;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    private Recipe recipe;

    @Column(name = "ingredient")
    private Ingredient ingredient;

    public String getImageId() {
        return imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
