package com.czura.recipies.injector.components;

import android.content.Context;

import com.czura.recipies.injector.Activity;
import com.czura.recipies.injector.modules.ActivityModule;
import com.czura.recipies.views.activities.MainActivity;

import dagger.Component;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Activity @Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
    Context activityContext();
}
