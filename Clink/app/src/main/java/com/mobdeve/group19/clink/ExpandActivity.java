package com.mobdeve.group19.clink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandActivity extends AppCompatActivity {

    private TextView expand_nameTv, expand_timeTv, expand_ingTv, expand_stepsTv;
    private ImageView expand_cocktailIv;
    private RecyclerView recyclerView;
    private LinearLayoutManager MyManager;

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
    }
}