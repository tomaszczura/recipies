package com.czura.recipes.model.entities;

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
@Table(name = "Items", id = BaseColumns._ID)
public class Item extends Model implements Parcelable{
    public static final String TABLE_NAME = "Items";
    public static final String ID = "_id";
    public static final String NAME = "name";

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private int id;

    @Column(name = "amount")
    @SerializedName("amount")
    private double amount;

    @Column(name = NAME)
    @SerializedName("name")
    private String name;

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
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

    public int getExternalId() {
        return id;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public static List<Item> withName(String name){
        return new Select().from(Item.class).where(Item.NAME + " LIKE '%" + name + "%'")
                .orderBy(Item.NAME).execute();
    }

    public static List<Item> hasName(String name){
        return new Select().from(Item.class).where("name = '" + name + "'").execute();
    }

    public static Item getItemOfExternalId(int extId){
        return new Select().from(Item.class).where("id = ?", extId).executeSingle();
    }
}
