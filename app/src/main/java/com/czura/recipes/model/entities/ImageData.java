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
@Table(name = "ImagesData", id = BaseColumns._ID)
public class ImageData extends Model implements Parcelable{
    public static final String TABLE_NAME = "ImagesData";
    public static final String ID = "_id";
    public static final String RECIPE_KEY = "recipe";

    @Column(name = "url")
    @SerializedName("url")
    private String imageUrl;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    private Recipe recipe;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
    }

    public ImageData() {
    }

    protected ImageData(Parcel in) {
        this.imageUrl = in.readString();
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public static List<ImageData> getImagesOfRecipe(long id){
        return new Select().from(ImageData.class).where(RECIPE_KEY + " = ?", id).execute();
    }
}
