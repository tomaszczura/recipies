package com.czura.recipies.mvp.presenters;

import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.czura.recipies.model.entities.Item;
import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.model.rest.RestDataSource;
import com.czura.recipies.mvp.views.RecipesListView;
import com.czura.recipies.mvp.views.View;
import com.czura.recipies.utils.Constants;
import com.czura.recipies.utils.RecipeSaveTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipesListPresenter implements Presenter, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {
    private static final String TAG = RecipesListPresenter.class.getSimpleName();

    private RestDataSource restApi;
    private RecipesListView view;

    private ExecutorService saveService = Executors.newSingleThreadExecutor();

    @Inject
    public RecipesListPresenter(RestDataSource restApi) {
        this.restApi = restApi;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {
        //TODO: pause call
    }

    @Override
    public void attachView(View v) {
        view = (RecipesListView) v;
    }

    @Override
    public void onCreate() {
        downloadRecipes();
    }

    public void downloadRecipes() {
        view.showLoading();
        restApi.getRecipes().enqueue(getRecipesCallback);
    }

    private Callback<List<Recipe>> getRecipesCallback = new Callback<List<Recipe>>() {

        @Override
        public void onResponse(Response<List<Recipe>> response) {
            List<Recipe> recipes = response.body();
            saveService.execute(new RecipeSaveTask(recipes));

            view.hideLoading();
            view.bindRecipeList(recipes);
        }

        @Override
        public void onFailure(Throwable t) {
            view.hideLoading();
            view.bindRecipeList(Recipe.getAllRecipes());
        }
    };

    public void onRecipeClick(Recipe recipe){
        Log.d(TAG, "Recipe: " + recipe.getTitle());
        view.showRecipeDetails(recipe);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("MainActivity", "onQueryTextSubmit: " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("MainActivity", "onQueryTextChange: " + newText);
        prepareSuggestions(newText);
        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        Log.d("MainActivity", "onSuggestionSelect: " + position);
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Log.d("MainActivity", "onSuggestionClick: " + position);
        return true;
    }

    private void prepareSuggestions(String query) {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, Constants.MODEL_NAME_KEY, Constants.SUGGESTION_RESULT_KEY});

        List<Recipe> recipes = Recipe.withName(query);
        List<Item> items = Item.withName(query);

        for (Recipe recipe : recipes) {
            cursor.addRow(new Object[] {recipe.getId(), Recipe.class.getSimpleName(), recipe.getTitle()});
        }

        for (Item item : items) {
            cursor.addRow(new Object[] {item.getId(), Item.class.getSimpleName(), item.getName()});
        }

        view.insertSuggestions(cursor);
    }
}
