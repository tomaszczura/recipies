package com.czura.recipies.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
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
public class Recipe extends Model implements Parcelable{
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

        //TODO: offline mode - crash!
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeTypedList(images);
        dest.writeTypedList(ingredients);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.images = in.createTypedArrayList(ImageData.CREATOR);
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public static List<Recipe> withName(String name){
        return new Select().from(Recipe.class).where("title LIKE '%" + name + "%'").execute();
    }
}
