package com.czura.recipies.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.czura.recipies.R;
import com.czura.recipies.model.entities.Recipe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeRowViewHolder>{

    private Context context;
    private final List<Recipe> recipeList;

    public RecipeListAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recipe_row, parent, false);
        return new RecipeRowViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecipeRowViewHolder holder, int position) {
        holder.bindRecipe(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeRowViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recipeImage)
        ImageView recipeImage;

        @Bind(R.id.recipeTitle)
        TextView recipeTitle;

        @Bind(R.id.recipeDescription)
        TextView recipeDescription;

        public RecipeRowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindRecipe(Recipe recipe){
            if(!TextUtils.isEmpty(recipe.getImageUrl())){
                Glide.with(context).load(recipe.getImageUrl()).into(recipeImage);
            }

            recipeTitle.setText(recipe.getTitle());
            recipeDescription.setText(Html.fromHtml(recipe.getDescription()));
        }
    }
}
