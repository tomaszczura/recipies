package com.czura.recipies.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.czura.recipies.R;
import com.czura.recipies.RecipesApplication;
import com.czura.recipies.injector.components.DaggerMainActivityComponent;
import com.czura.recipies.injector.modules.ActivityModule;
import com.czura.recipies.mvp.presenters.RecipesListPresenter;
import com.czura.recipies.mvp.views.RecipesListView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements RecipesListView{

    @Inject
    RecipesListPresenter recipesListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeInjector();
        initializePresenter();
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
}
