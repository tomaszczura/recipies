package com.czura.recipes.utils;

import com.activeandroid.ActiveAndroid;
import com.czura.recipes.model.entities.Recipe;

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
        try {
            ActiveAndroid.beginTransaction();
            Recipe.deleteAll();
            for (Recipe recipe : recipes) {
                recipe.saveWithRelations();
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}