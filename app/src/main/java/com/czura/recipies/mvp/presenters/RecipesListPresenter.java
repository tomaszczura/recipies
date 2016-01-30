package com.czura.recipies.mvp.presenters;

import android.util.Log;

import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.model.rest.RestDataSource;
import com.czura.recipies.mvp.views.RecipesListView;
import com.czura.recipies.mvp.views.View;
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
public class RecipesListPresenter implements Presenter {
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
}
