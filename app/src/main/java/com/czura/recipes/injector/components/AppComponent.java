package com.czura.recipes.injector.components;

import com.czura.recipes.RecipesApplication;
import com.czura.recipes.injector.AppModule;
import com.czura.recipes.model.rest.RestDataSource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Singleton @Component(modules = AppModule.class)
public interface AppComponent {
    RecipesApplication app();
    RestDataSource restDataSource();
}
