package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchActivity extends AppCompatActivity {

    private FloatingActionButton fabRecipe;
    private LinearLayout llRecipes;
    private LinearLayout llProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.addRecipe();
        this.Recipes();
        this.Profile();
    }

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    private void addRecipe() {
        this.fabRecipe = findViewById(R.id.addrecipeFab_search);
        this.fabRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SearchActivity.this, AddRecipeActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Profile() {
        this.llProfile = findViewById(R.id.profileLl_expand);
        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SearchActivity.this, ProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Recipes() {
        this.llRecipes = findViewById(R.id.recipesLl_expand);
        this.llRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SearchActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }
}