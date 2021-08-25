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

public class RecipesActivity extends AppCompatActivity {

    private FloatingActionButton fabRecipe;
    private LinearLayout llSearch;
    private LinearLayout llProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        this.fabRecipe = findViewById(R.id.addrecipeFab);
        this.llProfile = findViewById(R.id.profileLl);
        this.llSearch = findViewById(R.id.searchLl);

        this.addRecipe();
        this.Search();
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
        this.fabRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, AddRecipeActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Search() {
        this.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, SearchActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Profile() {
        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, ProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

}