package com.czura.recipies.injector.modules;

import android.content.Context;

import com.czura.recipies.injector.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tomasz on 30.01.2016.
 */
@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides @Activity
    Context provideActivityContext(){
        return context;
    }
}
