package com.czura.recipies.model.rest;

import com.czura.recipies.model.entities.ImageSize;
import com.czura.recipies.model.entities.Recipe;

import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * Created by Tomasz on 30.01.2016.
 */
public class RestDataSource {

    private final RecipesApi recipesApi;

    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

//        client.interceptors().add(loggingInterceptor);

        Retrofit recipesApiAdapter = new Retrofit.Builder()
                .baseUrl(RecipesApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        recipesApi =  recipesApiAdapter.create(RecipesApi.class);
    }

    public Call<List<Recipe>> getRecipes(){
        return recipesApi.getRecipes("", ImageSize.THUMBNAIL_MEDIUM, 1, 50, 1);
    }
}
