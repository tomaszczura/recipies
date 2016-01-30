package com.czura.recipies.views.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.czura.recipies.R;
import com.czura.recipies.RecipesApplication;
import com.czura.recipies.injector.components.DaggerRecipeActivityComponent;
import com.czura.recipies.injector.modules.ActivityModule;
import com.czura.recipies.model.entities.Ingredient;
import com.czura.recipies.model.entities.Item;
import com.czura.recipies.model.entities.Recipe;
import com.czura.recipies.utils.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recipeDescription)
    TextView recipeDescription;

    @Bind(R.id.recipeTitle)
    TextView recipeTitle;

    @Bind(R.id.recipeImage)
    ImageView recipeImage;

    @Bind(R.id.ingredients)
    TextView ingredientsList;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeInjector();

        recipe = getIntent().getParcelableExtra(Constants.RECIPE_TAG);
        fillRecipeData();
    }

    private void fillRecipeData() {
        if(!TextUtils.isEmpty(recipe.getImageUrl())){
            Glide.with(this).load(recipe.getImageUrl()).into(recipeImage);
        }

        toolbar.setTitle(R.string.recipe);
        recipeTitle.setText(recipe.getTitle());
        recipeDescription.setText(Html.fromHtml(recipe.getDescription()));

        StringBuilder builder = new StringBuilder();
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(0);

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (Item item : ingredient.getItems()) {
                String amount = df.format(item.getAmount());

                builder.append("- ");

                if(!TextUtils.isEmpty(amount) && !amount.equals("0")){
                    builder.append(amount).append("x ");
                }
                builder.append(item.getName())
                        .append(System.getProperty("line.separator"));
            }
        }
        ingredientsList.setText(builder);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initializeInjector() {
        RecipesApplication application = (RecipesApplication) getApplication();

        DaggerRecipeActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }
}
