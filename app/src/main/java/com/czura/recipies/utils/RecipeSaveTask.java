package com.czura.recipies.utils;

import com.czura.recipies.model.entities.Recipe;

import java.util.List;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipeSaveTask implements Runnable {

    private List<Recipe> recipes;

    public RecipeSaveTask(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void run() {
        for (Recipe recipe : recipes) {
            recipe.saveWithRelations();
        }
    }
}