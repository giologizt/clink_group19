package com.mobdeve.group19.clink;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.group19.clink.model.Recipe;

import java.util.ArrayList;

public class AdapterRecipes extends RecyclerView.Adapter<ViewHolderRecipes> {
    private ArrayList<Recipe> data;

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_TIME = "KEY_TIME";
    public static final String KEY_PIC = "KEY_PIC";
    public static final String KEY_INGREDIENTS = "KEY_INGREDIENTS";
    public static final String KEY_STEPS = "KEY_STEPS";
    public static final String KEY_IMAGE = "KEY_IMAGE";

    private Context context;

    public AdapterRecipes(ArrayList<Recipe> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderRecipes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_layout, parent, false);
        ViewHolderRecipes ViewHolderRecipes  = new ViewHolderRecipes(v);

        ViewHolderRecipes.getBoxLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExpandActivity.class);
                Recipe currentRecipe = data.get(ViewHolderRecipes.getAdapterPosition());

                intent.putExtra(KEY_NAME, currentRecipe.getName());
                intent.putExtra(KEY_TIME, currentRecipe.getPrepTime());
                intent.putExtra(KEY_PIC, currentRecipe.getRecipeId());
                intent.putExtra(KEY_INGREDIENTS, currentRecipe.getIngredients());
                intent.putExtra(KEY_STEPS, currentRecipe.getSteps());
                intent.putExtra(KEY_IMAGE, currentRecipe.getImageFile());

                v.getContext().startActivity(intent);
            }
        });

        return ViewHolderRecipes;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecipes holder, int position) {
        holder.bindData(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
