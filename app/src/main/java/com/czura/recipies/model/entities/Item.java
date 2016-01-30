package com.czura.recipies.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Table(name = "Items", id = BaseColumns._ID)
public class Item extends Model implements Parcelable{

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.amount);
        dest.writeString(this.name);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.amount = in.readDouble();
        this.name = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
