package com.czura.recipes.model.rest;

import com.czura.recipes.model.entities.ImageSize;
import com.czura.recipes.model.entities.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tomasz on 30.01.2016.
 */
public interface RecipesApi {

    String END_POINT  = "http://www.godt.no/api/";

    @GET("getRecipesListDetailed")
    Call<List<Recipe>> getRecipes(@Query("tags") String tags, @Query("size")ImageSize size,
                                  @Query("ratio") int ratio, @Query("limit") int limit, @Query("from") int from);
}
