package com.czura.recipes.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Ingredients", id = BaseColumns._ID)
public class Ingredient extends Model implements Parcelable{
    public static final String TABLE_NAME = "Ingredients";
    public static final String ID = "_id";
    public static final String RECIPE_KEY = "recipe";

//    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    @SerializedName("id")
//    private int id;

    @SerializedName("elements")
    private List<Item> items;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    private Recipe recipe;

    public List<Item> getItems() {
        if(items == null || items.size() == 0){
            items = new Select().from(Item.class).innerJoin(IngredientItem.class)
                    .on(Item.TABLE_NAME + "." + Item.ID + " = " + IngredientItem.TABLE_NAME + "." + IngredientItem.ITEM_COLUMN)
                    .where(IngredientItem.TABLE_NAME + "." + IngredientItem.INGREDIENT_COLUMN + " = ?", getId()).execute();
        }
        return items;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void saveWithRelations(){
        save();
        for (Item item : items) {
            Item saved = Item.getItemOfExternalId(item.getExternalId());
            if(saved == null){
                item.save();
                saved = item;
            }
            IngredientItem ingredientItem = new IngredientItem(this, saved);
            ingredientItem.save();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public static List<Ingredient> getIngredientsOfRecipe(long id){
        return new Select().from(Ingredient.class).where(RECIPE_KEY + " = ?", id).execute();
    }

    public static List<Ingredient> getIngredientsWithItems(List<Item> items){
        List<Long> ids = new ArrayList<>();
        for (Item item : items) {
            ids.add(item.getId());
        }
        String joined = TextUtils.join(",", ids);

        return new Select().from(Ingredient.class).innerJoin(IngredientItem.class)
                .on(Ingredient.TABLE_NAME + "." + Ingredient.ID + " = " + IngredientItem.TABLE_NAME + "." + IngredientItem.INGREDIENT_COLUMN)
                .where(IngredientItem.TABLE_NAME + "." + IngredientItem.ITEM_COLUMN + " in (" + joined + ")").execute();
    }
}
