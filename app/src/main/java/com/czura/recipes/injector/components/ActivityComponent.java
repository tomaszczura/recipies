package com.czura.recipes.injector.components;

import android.content.Context;

import com.czura.recipes.injector.Activity;
import com.czura.recipes.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Activity @Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Context context();
}
