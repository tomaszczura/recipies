package com.czura.recipes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.czura.recipes.injector.AppModule;
import com.czura.recipes.injector.components.AppComponent;
import com.czura.recipes.injector.components.DaggerAppComponent;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipesApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        initializeInjector();
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
