package com.mobdeve.group19.clink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecipes extends RecyclerView.Adapter<ViewHolderRecipes> {
    private ArrayList<Recipe> data;

    public AdapterRecipes(ArrayList<Recipe> data) {this.data = data; }

    @NonNull
    @Override
    public ViewHolderRecipes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
        ViewHolderRecipes ViewHolderRecipes  = new ViewHolderRecipes(v);

        return ViewHolderRecipes;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecipes holder, int position) {
        holder.setNameTv(data.get(position).getName());
        holder.setTimeTv(data.get(position).getTime());
        holder.setCocktailIv(data.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
