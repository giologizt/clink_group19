package com.mobdeve.group19.clink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterFeedback extends RecyclerView.Adapter<ViewHolderFeedback> {
    private ArrayList<Feedback> data;

    public AdapterFeedback(ArrayList<Feedback> data) {this.data = data; }

    @NonNull
    @NotNull
    @Override
    public ViewHolderFeedback onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout, parent, false);
        ViewHolderFeedback ViewHolderFeedback = new ViewHolderFeedback(v);

        return ViewHolderFeedback;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderFeedback holder, int position) {
        holder.setUsernameTv(data.get(position).getUsername());
        holder.setCommentTv(data.get(position).getComment());
        holder.setUserIv(data.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

//public class AdapterRecipes extends RecyclerView.Adapter<ViewHolderRecipes> {
//    private ArrayList<Recipe> data;
//
//    public AdapterRecipes(ArrayList<Recipe> data) {this.data = data; }
//
//    @NonNull
//    @Override
//    public ViewHolderRecipes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_layout, parent, false);
//        ViewHolderRecipes ViewHolderRecipes  = new ViewHolderRecipes(v);
//
//        return ViewHolderRecipes;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolderRecipes holder, int position) {
//        holder.setNameTv(data.get(position).getName());
//        holder.setTimeTv(data.get(position).getTime());
//        holder.setCocktailIv(data.get(position).getImageId());
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//}