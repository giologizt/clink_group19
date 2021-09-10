package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Recipe;
import com.mobdeve.group19.clink.model.RecipeCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpandActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    private TextView expand_nameTv, expand_timeTv, expand_ingTv, expand_stepsTv;
    private ImageView expand_cocktailIv;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;
    private LinearLayout llSearch;
    private LinearLayout llProfile;
    private LinearLayout llRecipes;
    private FloatingActionButton btnEditRecipe;
    private TextView tvFeedback;
    private TextView tvEditFeedback;
    private AdapterFeedback Adapter;
    private ArrayList<Feedback_test> dataFeedback;

    ExecutorService executorService;
    ApiHelper helper;

    SharedPreferences sp;

    String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        this.expand_nameTv = findViewById(R.id.tv_recipename);
        this.expand_timeTv = findViewById(R.id.expand_timeTv);
        this.expand_cocktailIv = findViewById(R.id.expand_picIv);
        this.expand_ingTv = findViewById(R.id.expand_ingredientsTv);
        this.expand_stepsTv = findViewById(R.id.expand_stepsTv);

        this.btnEditRecipe = findViewById(R.id.editrecipeBtn);

        Intent intent = getIntent();

        executorService = Executors.newSingleThreadExecutor();
        helper = new ApiHelper();

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        this.tvFeedback = findViewById(R.id.leavereviewTv);
        this.tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = intent.getStringExtra(AdapterRecipes.KEY_RECIPE_ID);

                Intent intent = new Intent (ExpandActivity.this, AddReviewActivity.class);
                intent.putExtra(KEY_RECIPE_ID, id);

                Launcher.launch(intent);
            }
        });


        this.btnEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = intent.getStringExtra(AdapterRecipes.KEY_RECIPE_ID);

                Intent intent = new Intent (ExpandActivity.this, EditRecipeActivity.class);
                intent.putExtra(KEY_RECIPE_ID, id);

                Launcher.launch(intent);
            }
        });

        this.recyclerView = findViewById(R.id.feedbackRv);
        this.MyManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.MyManager);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String id = intent.getStringExtra(AdapterRecipes.KEY_RECIPE_ID);
                recipeId = intent.getStringExtra(AdapterRecipes.KEY_RECIPE_ID);

                helper.getRecipe(id, new RecipeCallback() {
                    @Override
                    public void success(Message message, Recipe recipe) {
                        expand_nameTv.setText(recipe.getName());

                        //Log.i("id of author", recipe.getAuthor());
                        Log.i("id of user", sp.getString(USER_ID_KEY, ""));
                        //Log.i("id of user", recipe.getAuthor());

                        if(!sp.getString(USER_ID_KEY, "").equals(recipe.getAuthor()))
                            btnEditRecipe.setVisibility(View.INVISIBLE);
                        else
                            btnEditRecipe.setVisibility(View.VISIBLE);

                        if(recipe.getPrepTime() <= 1)
                            expand_timeTv.setText(Integer.toString(recipe.getPrepTime()) + " minute");
                        else
                            expand_timeTv.setText(Integer.toString(recipe.getPrepTime()) + " minutes");

                        StringBuilder steps = new StringBuilder();
                        StringBuilder ingredients = new StringBuilder();

                        for(int j = 0; j < recipe.getIngredients().size(); j++) {
                            ingredients.append(Integer.toString(j+1) + ". " + recipe.getIngredients().get(j).getIngredientName());
                            if(recipe.getIngredients().size() != j+1) {
                                ingredients.append("\n");
                            }
                        }

                        expand_ingTv.setText(ingredients);

                        for(int i = 0; i < recipe.getSteps().size(); i++) {

                            steps.append(Integer.toString(i+1) + ". " + recipe.getSteps().get(i));

                            if(recipe.getIngredients().size() != i+1) {
                                steps.append("\n");
                            }

                        }
                        expand_stepsTv.setText(steps.toString());

                        File file = new File("http://10.0.2.2:3000/image/" + recipe.getImage());
                        Picasso.with(getApplicationContext()).load("http://10.0.2.2:3000/image/" + recipe.getImage()).into(expand_cocktailIv);

                        recyclerView.setAdapter(new AdapterFeedback(recipe.getReviews()));


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

//        this.Adapter = new AdapterFeedback(this.dataFeedback);
//        this.recyclerView.setAdapter(this.Adapter);

        this.Recipes();
        this.Search();
        this.Profile();
        this.EditRecipe();
        //this.EditFeedback();
    }

    public ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

//    public ActivityResultLauncher Launcher2 = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getData() != null) {
//                        Intent intent = result.getData();
//                        String feedback = intent.getStringExtra(AddReviewActivity.KEY_FEEDBACK);
//
//                        dataFeedback.add(0, new Feedback(R.drawable.dua, "Dua Lipa", feedback));
//
//                        Adapter.notifyItemChanged(0);
//                        Adapter.notifyItemRangeChanged(0, Adapter.getItemCount());
//                    }
//                }
//            }
//    );

    public void EditRecipe() {

    }

    public void Search() {
        this.llSearch = findViewById(R.id.searchLl_expand);
        this.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, SearchActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    public void Profile() {
        this.llProfile = findViewById(R.id.profileLl);
        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, ProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Recipes() {
        this.llRecipes = findViewById(R.id.recipesLl_search);
        this.llRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

//    private void EditFeedback() {
//        this.tvEditFeedback = findViewById(R.id.editreviewTv);
//        this.tvEditFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (ExpandActivity.this, EditReviewActivity.class);
//
//                Launcher.launch(intent);
//                //Launcher2.launch(intent);
//            }
//        });
//    }

}