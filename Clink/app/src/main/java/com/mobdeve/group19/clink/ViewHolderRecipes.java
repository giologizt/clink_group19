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

    // Intializes the View Holder with the Data
    public ViewHolderRecipes (@NonNull View itemView) {
        super(itemView);
        this.nameTv = itemView.findViewById(R.id.cocktail_nameTv);
        this.timeTv = itemView.findViewById(R.id.preptimeTv);
        this.cocktailIv = itemView.findViewById(R.id.cocktailIv);
        this.boxLl = itemView.findViewById(R.id.boxLl);
    }

    // Binds the data into the View Holder
    public void bindData (Recipe data, Context context) {
        this.nameTv.setText(data.getName());

        // Checks if the Preparation time is less than or equal to 1 minute. Minute if 1 minute or less, minutes if more.
        if(data.getPrepTime() <= 1)
            timeTv.setText(Integer.toString(data.getPrepTime()) + " minute");
        else
            timeTv.setText(Integer.toString(data.getPrepTime()) + " minutes");

        // Gets the image from the server using Picasso
        File file = new File("http://10.0.2.2:3000/image/" + data.getImage());
        Picasso.with(context).load("http://10.0.2.2:3000/image/" + data.getImage()).into(this.cocktailIv);

    }

    public LinearLayout getBoxLl () {return this.boxLl; }
}
