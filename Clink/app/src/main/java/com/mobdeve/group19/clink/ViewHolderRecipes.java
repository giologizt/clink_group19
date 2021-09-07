package com.mobdeve.group19.clink;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import com.mobdeve.group19.clink.model.Recipe;

public class ViewHolderRecipes extends RecyclerView.ViewHolder {
    private TextView nameTv, timeTv;
    private ImageView cocktailIv;
    private LinearLayout boxLl;

    public ViewHolderRecipes (@NonNull View itemView) {
        super(itemView);
        this.nameTv = itemView.findViewById(R.id.cocktail_nameTv);
        this.timeTv = itemView.findViewById(R.id.preptimeTv);
        this.cocktailIv = itemView.findViewById(R.id.cocktailIv);
        this.boxLl = itemView.findViewById(R.id.boxLl);
    }

    public void bindData (Recipe data) {
        this.nameTv.setText(data.getName());
        this.timeTv.setText(data.getPrepTime().toString());

        Uri cocktail = Uri.parse(cocktailIv.toString());
        this.cocktailIv.setImageURI(cocktail);
    }

    public LinearLayout getBoxLl () {return this.boxLl; }
}
