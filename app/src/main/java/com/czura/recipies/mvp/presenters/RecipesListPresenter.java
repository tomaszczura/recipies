package com.czura.recipies.mvp.presenters;

import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.model.rest.RestDataSource;
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

    }

    @Override
    public void attachView(View v) {

    }

    @Override
    public void onCreate() {
        restApi.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Response<List<Recipe>> response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
