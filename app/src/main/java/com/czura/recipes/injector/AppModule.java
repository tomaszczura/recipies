package com.czura.recipes.injector;

import com.czura.recipes.RecipesApplication;
import com.czura.recipes.model.rest.RestDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Module
public class AppModule {
    private final RecipesApplication recipesApplication;

    public AppModule(RecipesApplication recipesApplication) {
        this.recipesApplication = recipesApplication;
    }

    @Provides @Singleton
    RecipesApplication provideRecipesAppContext() {
        return recipesApplication;
    }

    @Provides @Singleton
    RestDataSource provideRestDataSource(){
        return new RestDataSource();
    }


}
