package com.czura.recipies.mvp.views;

import android.database.Cursor;

import com.czura.recipies.model.entities.Recipe;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
public interface RecipesListView extends View {

    void bindRecipeList(List<Recipe> recipes);
    void showLoading();
    void hideLoading();
    void showRecipeDetails(Recipe recipe);
    void insertSuggestions(Cursor cursor);
}
