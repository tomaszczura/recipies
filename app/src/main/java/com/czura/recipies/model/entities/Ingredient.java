package com.czura.recipies.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Ingredients", id = BaseColumns._ID)
public class Ingredient extends Model implements Parcelable{

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private int id;

    @SerializedName("elements")
    private List<Item> items;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    private Recipe recipe;

    public List<Item> getItems() {
        if(items == null || items.size() == 0){
            items = getMany(Item.class, "ingredient");
        }
        return items;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void saveWithRelations(){
        for (Item item : items) {
            item.setIngredient(this);
            item.save();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(items);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.id = in.readInt();
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
}
