package com.czura.recipies.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class ImageData {

    @SerializedName("imboId")
    private String imageId;

    @SerializedName("url")
    private String imageUrl;

    public String getImageId() {
        return imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
