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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpandActivity extends AppCompatActivity {

    private TextView expand_nameTv, expand_timeTv, expand_ingTv, expand_stepsTv;
    private ImageView expand_cocktailIv;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;
    private LinearLayout llSearch;
    private LinearLayout llProfile;
    private LinearLayout llRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        this.expand_nameTv = findViewById(R.id.expand_nameTv);
        this.expand_timeTv = findViewById(R.id.expand_timeTv);
        this.expand_cocktailIv = findViewById(R.id.expand_picIv);
        this.expand_ingTv = findViewById(R.id.expand_ingredientsTv);
        this.expand_stepsTv = findViewById(R.id.expand_stepsTv);

        Intent intent = getIntent();

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

        this.recyclerView = findViewById(R.id.feedbackRv);
        this.MyManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.MyManager);

        this.recyclerView.setAdapter(new AdapterFeedback(DataHelperFeedback.initializeData()));

        this.Recipes();
        this.Search();
        this.Profile();

    }

    public ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

//    public void Edit() {
//        this.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (ExpandActivity.this, AddRecipeActivity.class);
//
//                Launcher.launch(intent);
//            }
//        });
//    }

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
        this.llProfile = findViewById(R.id.profileLl_expand);
        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, ProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Recipes() {
        this.llRecipes = findViewById(R.id.recipesLl_expand);
        this.llRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ExpandActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

}