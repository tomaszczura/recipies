package com.czura.recipes.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Recipes", id = BaseColumns._ID)
public class Recipe extends Model implements Parcelable{
    public static final String TABLE_NAME = "Recipes";
    public static final String ID = "_id";
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private int id;

    @Column(name = TITLE)
    @SerializedName("title")
    private String title;

    @Column(name = DESCRIPTION)
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
            images = ImageData.getImagesOfRecipe(getId());
        }
        if(images.size() > 0){
            return images.get(0).getImageUrl();
        }
        return "";
    }

    public List<Ingredient> getIngredients() {

        //TODO: offline mode - crash!
        if(ingredients == null || ingredients.size() == 0){
            ingredients = Ingredient.getIngredientsOfRecipe(getId());
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
        return new Select().from(Recipe.class).where(Recipe.TITLE + " LIKE '%" + name + "%'")
                .orderBy(Recipe.TITLE).execute();
    }

    public static List<Recipe> hasName(String name){
        return new Select().from(Recipe.class).where("title = ?", name).execute();
    }

    public static void deleteAll(){
        new Delete().from(Recipe.class).execute();
        new Delete().from(Item.class).execute();
    }

    public static Recipe getRecipeOfId(Long id){
        return new Select().from(Recipe.class).where(BaseColumns._ID + " = ?", id).executeSingle();
    }

    public static List<Recipe> getRecipesOfIds(List<Long> ids){
        String joined = TextUtils.join(",", ids);
        return new Select().from(Recipe.class).where(BaseColumns._ID + " in (" + joined + ")").execute();
    }

    public static List<Recipe> getRecipesWithIngredients(List<Ingredient> ingredients){
        String joined = prepareIngredientIds(ingredients);

        return new Select(allColumns()).distinct().from(Recipe.class).innerJoin(Ingredient.class)
                .on(Recipe.TABLE_NAME + "." + Recipe.ID + " = " + Ingredient.TABLE_NAME + "." + Ingredient.RECIPE_KEY)
                .where(Ingredient.TABLE_NAME + "." + Ingredient.ID + " in (" + joined + ")").execute();
    }

    public static List<Recipe> getRecipesWithIngredientsOrName(List<Ingredient> ingredients, String name){
        String joined = prepareIngredientIds(ingredients);

        return new Select(allColumns()).distinct().from(Recipe.class).innerJoin(Ingredient.class)
                .on(Recipe.TABLE_NAME + "." + Recipe.ID + " = " + Ingredient.TABLE_NAME + "." + Ingredient.RECIPE_KEY)
                .where(Ingredient.TABLE_NAME + "." + Ingredient.ID + " in (" + joined + ") OR " +
                        Recipe.TABLE_NAME + "." + Recipe.TITLE + " LIKE '%" + name + "%'").execute();
    }

    private static String prepareIngredientIds(List<Ingredient> ingredients) {
        List<Long> ids = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ids.add(ingredient.getId());
        }
        return TextUtils.join(",", ids);
    }

    private static String allColumns(){
        return Recipe.TABLE_NAME + "." + Recipe.ID  + ", " +
        Recipe.TABLE_NAME + ".id, " +
        Recipe.TABLE_NAME + "." + Recipe.TITLE  + ", " +
        Recipe.TABLE_NAME + "." + Recipe.DESCRIPTION;
    }
}
