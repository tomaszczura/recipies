package com.czura.recipies.views.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.czura.recipies.R;
import com.czura.recipies.RecipesApplication;
import com.czura.recipies.injector.components.DaggerMainActivityComponent;
import com.czura.recipies.injector.modules.ActivityModule;
import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.mvp.presenters.RecipesListPresenter;
import com.czura.recipies.mvp.views.RecipesListView;
import com.czura.recipies.views.adapters.RecipeListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipesListView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recipesList)
    RecyclerView recipesListView;

    @Bind(R.id.refreshRecipesLayout)
    SwipeRefreshLayout refreshLayout;

    @Inject
    RecipesListPresenter recipesListPresenter;

    private RecipeListAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initializeToolbar();
        initializeInjector();
        initializePresenter();
        initializeRecyclerView();
    }


    private void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initializeInjector() {
        RecipesApplication application = (RecipesApplication) getApplication();

        DaggerMainActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    private void initializePresenter() {
        recipesListPresenter.attachView(this);
        recipesListPresenter.onCreate();
    }

    private void initializeRecyclerView() {
        recipesListView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void bindRecipeList(List<Recipe> recipes) {
        recipeListAdapter = new RecipeListAdapter(this, recipes);
        recipesListView.setAdapter(recipeListAdapter);
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
