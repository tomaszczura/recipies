package com.czura.recipies.mvp.presenters;

import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.model.rest.RestDataSource;
import com.czura.recipies.mvp.views.RecipesListView;
import com.czura.recipies.mvp.views.View;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipesListPresenter implements Presenter {

    private RestDataSource restApi;
    private RecipesListView view;

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
            view.hideLoading();
            view.bindRecipeList(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            view.hideLoading();
        }
    };
}
