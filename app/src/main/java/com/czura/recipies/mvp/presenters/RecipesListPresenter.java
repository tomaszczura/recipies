package com.czura.recipies.mvp.presenters;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.widget.SearchView;

import com.czura.recipies.model.entities.Ingredient;
import com.czura.recipies.model.entities.Item;
import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.model.rest.RestDataSource;
import com.czura.recipies.mvp.views.RecipesListView;
import com.czura.recipies.mvp.views.View;
import com.czura.recipies.utils.Constants;
import com.czura.recipies.utils.RecipeSaveTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipesListPresenter implements Presenter, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, SearchView.OnCloseListener {
    private static final String TAG = RecipesListPresenter.class.getSimpleName();

    private RestDataSource restApi;
    private RecipesListView view;

    private ExecutorService saveService = Executors.newSingleThreadExecutor();

    private List<Recipe> recipes;
    private Call apiCall;

    @Inject
    public RecipesListPresenter(RestDataSource restApi) {
        this.restApi = restApi;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if(apiCall != null){
            apiCall.cancel();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        view = (RecipesListView) v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            List<Long> recipesIds = (List<Long>) savedInstanceState.getSerializable(Constants.RECIPE_IDS);
            if(recipesIds != null && recipesIds.size() > 0){
                recipes = Recipe.getRecipesOfIds(recipesIds);
                insertRecipesList();
                return;
            }
        }
        downloadRecipes();
    }

    public void saveDisplayedRecipes(ArrayList<Long> recipesIds, Bundle outState){
        outState.putSerializable(Constants.RECIPE_IDS, recipesIds);
    }

    public void downloadRecipes() {
        view.showLoading();
        if(apiCall != null){
            apiCall.cancel();
        }
        apiCall = restApi.getRecipes();
        apiCall.enqueue(getRecipesCallback);
    }

    private Callback<List<Recipe>> getRecipesCallback = new Callback<List<Recipe>>() {

        @Override
        public void onResponse(Response<List<Recipe>> response) {
            recipes = response.body();
            saveService.execute(new RecipeSaveTask(recipes));

            insertRecipesList();
        }

        @Override
        public void onFailure(Throwable t) {
            recipes = Recipe.getAllRecipes();

            insertRecipesList();
        }
    };

    private void insertRecipesList() {
        view.hideLoading();
        view.bindRecipeList(recipes);
    }

    public void onRecipeClick(Recipe recipe){
        view.showRecipeDetails(recipe);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Item> items = Item.withName(query);

        List<Ingredient> ingredientsWithItems = Ingredient.getIngredientsWithItems(items);
        List<Recipe> recipes = Recipe.getRecipesWithIngredientsOrName(ingredientsWithItems, query);
        view.bindRecipeList(recipes);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        prepareSuggestions(newText);
        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        view.suggestionClicked(position);
        return true;
    }

    private void prepareSuggestions(String query) {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, Constants.MODEL_NAME_KEY, Constants.SUGGESTION_RESULT_KEY});

        List<Recipe> recipes = Recipe.withName(query);
        List<Item> items = Item.withName(query);

        long id = 0;
        for (Recipe recipe : recipes) {
            cursor.addRow(new Object[] {id, Recipe.class.getSimpleName(), recipe.getTitle()});
            id++;
        }

        for (Item item : items) {
            cursor.addRow(new Object[] {id, Item.class.getSimpleName(), item.getName()});
            id++;
        }

        view.insertSuggestions(cursor);
    }

    public void filterRecipesBy(Cursor cursor){
        String modelName = cursor.getString(cursor.getColumnIndex(Constants.MODEL_NAME_KEY));
        String suggestion = cursor.getString(cursor.getColumnIndex(Constants.SUGGESTION_RESULT_KEY));
        if(modelName.equals(Recipe.class.getSimpleName())){
            List<Recipe> recipes = Recipe.hasName(suggestion);
            view.bindRecipeList(recipes);
        } else if(modelName.equals(Item.class.getSimpleName())){
            List<Recipe> recipes = getRecipesWithItemOfName(suggestion);
            view.bindRecipeList(recipes);
        }
    }

    private List<Recipe> getRecipesWithItemOfName(String suggestion) {
        List<Item> items = Item.hasName(suggestion);
        List<Ingredient> ingredientsWithItems = Ingredient.getIngredientsWithItems(items);
        return Recipe.getRecipesWithIngredients(ingredientsWithItems);
    }

    @Override
    public boolean onClose() {
        recipes = Recipe.getAllRecipes();
        view.bindRecipeList(recipes);
        return false;
    }
}
