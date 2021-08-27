package com.mobdeve.group19.clink;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderRecipes extends RecyclerView.ViewHolder {
    private TextView nameTv, timeTv, ingredientsTv, stepsTv;
    private ImageView cocktailIv;
    private LinearLayout boxLl;

    public ViewHolderRecipes (@NonNull View itemView) {
        super(itemView);
        this.nameTv = itemView.findViewById(R.id.cocktail_nameTv);
        this.timeTv = itemView.findViewById(R.id.preptimeTv);
        this.cocktailIv = itemView.findViewById(R.id.cocktailIv);
        this.boxLl = itemView.findViewById(R.id.boxLl);
        this.ingredientsTv = itemView.findViewById(R.id.ingredientsTv);
        this.stepsTv = itemView.findViewById(R.id.stepsTv);
    }

    public void setNameTv (String name) {this.nameTv.setText(name);}

    public void setTimeTv (String time) {this.timeTv.setText(time);}

    public void setCocktailIv (int cocktailIv) {this.cocktailIv.setImageResource(cocktailIv);}

    public LinearLayout getBoxLl () {return this.boxLl; }

    public void setIngredientsTv (String ingredients) {this.ingredientsTv.setText(ingredients);}

    public void setStepsTv (String steps) {this.stepsTv.setText(steps);}
}
