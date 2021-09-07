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

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Recipe;
import com.mobdeve.group19.clink.model.RecipeCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipesActivity extends AppCompatActivity {

    private LinearLayout fabRecipe;
    private LinearLayout llSearch;
    private LinearLayout llProfile;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;

    // Recipes Array
    private ArrayList<Recipe> recipes;

    // API Helper
    private ApiHelper helper;

    // Single Thread
    private ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        this.fabRecipe = findViewById(R.id.addrecipeFab_profile);
        this.llProfile = findViewById(R.id.profileLl);
        //this.llSearch = findViewById(R.id.searchLl);

        this.addRecipe();
        //this.Search();
        this.Profile();

        this.recyclerView = findViewById(R.id.recipesRv);
        this.MyManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.MyManager);

        this.helper = new ApiHelper();

        this.recipes = new ArrayList<Recipe>();

        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                helper.getRecipes(new RecipeCallback() {
                    @Override
                    public void success(Message message, ArrayList<Recipe> recipe) {
                        recipes = recipe;
                        recyclerView.setAdapter(new AdapterRecipes(recipes));
                        System.out.println(recipes.get(0).getName());
                    }

                    @Override
                    public void error(Message message) {

                    }

                    @Override
                    public void failure(Throwable t) {

                    }
                });
            }
        });


    }

    public ActivityResultLauncher Launcher = registerForActivityResult(
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

//    public void Search() {
//        this.llSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (RecipesActivity.this, SearchActivity.class);
//
//                Launcher.launch(intent);
//            }
//        });
//    }

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