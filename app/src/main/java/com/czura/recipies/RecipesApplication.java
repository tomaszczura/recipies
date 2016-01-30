package com.czura.recipies;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.czura.recipies.injector.AppModule;
import com.czura.recipies.injector.components.AppComponent;
import com.czura.recipies.injector.components.DaggerAppComponent;

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
