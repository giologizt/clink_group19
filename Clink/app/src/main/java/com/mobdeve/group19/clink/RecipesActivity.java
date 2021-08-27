package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipesActivity extends AppCompatActivity {

    private FloatingActionButton fabRecipe;
    private LinearLayout llSearch;
    private LinearLayout llProfile;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        this.fabRecipe = (FloatingActionButton) findViewById(R.id.addrecipeFab);
        this.llProfile = findViewById(R.id.profileLl);
        this.llSearch = findViewById(R.id.searchLl);

        this.addRecipe();
        this.Search();
        this.Profile();

        this.recyclerView = findViewById(R.id.recipesRv);
        this.MyManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.MyManager);

        this.recyclerView.setAdapter(new AdapterRecipes(DataHelperRecipe.initializeData()));
    }

    public ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    public ActivityResultLauncher Launcher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    public void addRecipe() {
        this.fabRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, AddRecipeActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    public void Search() {
        this.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, SearchActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    public void Profile() {
        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RecipesActivity.this, ProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

}