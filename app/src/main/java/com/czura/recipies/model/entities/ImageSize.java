package com.czura.recipies.model.entities;

/**
 * Created by Tomasz on 30.01.2016.
 */
public enum ImageSize {
    THUMBNAIL_MEDIUM("thumbnail-medium");

    private String description;

    ImageSize(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}