package com.czura.recipies.injector.components;

import com.czura.recipies.RecipesApplication;
import com.czura.recipies.injector.AppModule;
import com.czura.recipies.model.rest.RestDataSource;

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
