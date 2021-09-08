package com.mobdeve.group19.clink;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.net.URI;
import com.mobdeve.group19.clink.model.Recipe;
import com.squareup.picasso.Picasso;

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

    public void bindData (Recipe data, Context context) {
        this.nameTv.setText(data.getName());
        this.timeTv.setText(data.getPrepTime().toString());

        Log.d("View Holder Recipes", data.getImage());
        File file = new File("http://10.0.2.2:3000/image/" + data.getImage());
        Picasso.with(context).load("http://10.0.2.2:3000/image/" + data.getImage()).into(this.cocktailIv);

    }

    public LinearLayout getBoxLl () {return this.boxLl; }
}
