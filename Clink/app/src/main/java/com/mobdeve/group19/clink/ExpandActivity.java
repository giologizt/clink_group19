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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpandActivity extends AppCompatActivity {

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
    private ArrayList<Feedback> dataFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        this.expand_nameTv = findViewById(R.id.tv_recipename);
        this.expand_timeTv = findViewById(R.id.expand_timeTv);
        this.expand_cocktailIv = findViewById(R.id.expand_picIv);
        this.expand_ingTv = findViewById(R.id.expand_ingredientsTv);
        this.expand_stepsTv = findViewById(R.id.expand_stepsTv);

        Intent intent = getIntent();
        /*
        String Name = intent.getStringExtra(AdapterRecipes.KEY_NAME);
        this.expand_nameTv.setText(Name);

        String Time = intent.getStringExtra(AdapterRecipes.KEY_TIME);
        this.expand_timeTv.setText(Time);

        int Pic = intent.getIntExtra(AdapterRecipes.KEY_PIC, 0);
        this.expand_cocktailIv.setImageResource(Pic);

        String Ingredients = intent.getStringExtra(AdapterRecipes.KEY_INGREDIENTS);
        this.expand_ingTv.setText(Ingredients);

        String Steps = intent.getStringExtra(AdapterRecipes.KEY_STEPS);
        this.expand_stepsTv.setText(Steps);

        */

        this.recyclerView = findViewById(R.id.feedbackRv);
        this.MyManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.MyManager);

//        this.Adapter = new AdapterFeedback(this.dataFeedback);
//        this.recyclerView.setAdapter(this.Adapter);

        this.recyclerView.setAdapter(new AdapterFeedback(DataHelperFeedback.initializeData()));

        this.Recipes();
        this.Search();
        this.Profile();
        this.EditRecipe();
        this.WriteFeedback();
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
        this.btnEditRecipe = findViewById(R.id.editrecipeBtn);
        this.btnEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, EditRecipeActivity.class);

                Launcher.launch(intent);
            }
        });
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

    private void WriteFeedback() {
        this.tvFeedback = findViewById(R.id.leavereviewTv);
        this.tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, AddReviewActivity.class);

                Launcher.launch(intent);
                //Launcher2.launch(intent);
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