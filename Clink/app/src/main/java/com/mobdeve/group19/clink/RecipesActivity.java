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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Recipe;
import com.mobdeve.group19.clink.model.RecipesCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipesActivity extends AppCompatActivity {

    private LinearLayout fabRecipe;
    private LinearLayout llProfile;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;

    private TextView etSearch;

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

        this.etSearch = findViewById(R.id.et_search);

        this.addRecipe();
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
                helper.getRecipes(new RecipesCallback() {
                    @Override
                    public void success(Message message, ArrayList<Recipe> recipe) {
                        recipes = recipe;
                        if(recipes.size() >= 1) {
                            recyclerView.setAdapter(new AdapterRecipes(recipes, getApplicationContext()));
                        }
                            //System.out.println(recipes.get(0).getName());
                    }

                    @Override
                    public void error(Message message) {

                    }

                    @Override
                    public void failure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(etSearch.getText().toString().equals("")) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            helper.getRecipes(new RecipesCallback() {
                                @Override
                                public void success(Message message, ArrayList<Recipe> recipe) {
                                    recipes = recipe;
                                    if(recipes.size() >= 1) {
                                        recyclerView.setAdapter(new AdapterRecipes(recipes, getApplicationContext()));
                                        System.out.println(recipes.get(0).getName());
                                    }

                                }

                                @Override
                                public void error(Message message) {

                                }

                                @Override
                                public void failure(Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            String searchEntry = etSearch.getText().toString();
                            helper.searchRecipe(searchEntry, new RecipesCallback() {
                                @Override
                                public void success(Message message, ArrayList<Recipe> recipe) {
                                    recipes = recipe;
                                    recyclerView.setAdapter(new AdapterRecipes(recipes, getApplicationContext()));
                                    //System.out.println(recipes.get(0).getName());
                                }

                                @Override
                                public void error(Message message) {

                                }

                                @Override
                                public void failure(Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }

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