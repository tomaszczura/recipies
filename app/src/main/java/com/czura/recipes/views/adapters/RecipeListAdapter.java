package com.czura.recipes.views.adapters;

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
import com.czura.recipes.R;
import com.czura.recipes.model.entities.Recipe;
import com.czura.recipes.views.RecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tomasz on 30.01.2016.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeRowViewHolder>{

    private Context context;
    private final List<Recipe> recipeList;
    private RecyclerViewClick<Recipe> recyclerViewClick;

    public RecipeListAdapter(Context context, List<Recipe> recipeList, RecyclerViewClick<Recipe> recyclerViewClick) {
        this.context = context;
        this.recipeList = recipeList;
        this.recyclerViewClick = recyclerViewClick;
    }

    @Override
    public RecipeRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recipe_row, parent, false);
        return new RecipeRowViewHolder(rootView, recyclerViewClick);
    }

    @Override
    public void onBindViewHolder(RecipeRowViewHolder holder, int position) {
        holder.bindRecipe(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public ArrayList<Long> getRecipesIds(){
        ArrayList<Long> recipeIds = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeIds.add(recipe.getId());
        }
        return recipeIds;
    }

    class RecipeRowViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recipeImage)
        ImageView recipeImage;

        @Bind(R.id.recipeTitle)
        TextView recipeTitle;

        @Bind(R.id.recipeDescription)
        TextView recipeDescription;
        private Recipe recipe;

        public RecipeRowViewHolder(View itemView, RecyclerViewClick<Recipe> recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        private void bindListener(View itemView, final RecyclerViewClick<Recipe> recyclerClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClickListener.onItemClicked(recipe);
                }
            });
        }

        public void bindRecipe(Recipe recipe){
            this.recipe = recipe;

            if(!TextUtils.isEmpty(recipe.getImageUrl())){
                Glide.with(context).load(recipe.getImageUrl()).into(recipeImage);
            }

            recipeTitle.setText(recipe.getTitle());
            recipeDescription.setText(Html.fromHtml(recipe.getDescription()));
        }
    }
}
