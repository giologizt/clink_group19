package com.mobdeve.group19.clink;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderRecipes extends RecyclerView.ViewHolder {
    private TextView nameTv, timeTv;
    private ImageView cocktailIv;

    public ViewHolderRecipes (@NonNull View itemView) {
        super(itemView);
        this.nameTv = itemView.findViewById(R.id.cocktail_nameTv);
        this.timeTv = itemView.findViewById(R.id.preptimeTv);
        this.cocktailIv = itemView.findViewById(R.id.cocktailIv);
    }

    public void setNameTv (String name) {this.nameTv.setText(name);}

    public void setTimeTv (String time) {this.timeTv.setText(time);}

    public void setCocktailIv (int cocktailIv) {this.cocktailIv.setImageResource(cocktailIv);}
}
