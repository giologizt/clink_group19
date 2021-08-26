package com.mobdeve.group19.clink;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderRecipes extends RecyclerView.ViewHolder {
    private TextView nameTv, timeTv;
    private ImageView cocktailIv;

    public ViewHolderRecipes (@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);

    }
}
