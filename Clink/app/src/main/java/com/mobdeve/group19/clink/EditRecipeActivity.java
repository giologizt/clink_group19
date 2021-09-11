package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;

public class EditRecipeActivity extends AppCompatActivity {

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    private Button btnCancel, btnUpdate, btnDelete;

    private Button btnIngredients;
    private Button btnSteps;

    private int ingredientsLines;
    private int stepsLines;

    ApiHelper helper;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        this.btnDelete = findViewById(R.id.deleteBtn);

        this.ingredientsLines = 100;
        this.stepsLines = 200;

        this.btnIngredients = findViewById(R.id.btn_addIngredient_edit);
        this.btnSteps = findViewById(R.id.btn_steps_edit);

        this.helper = new ApiHelper();
        this.sp = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = intent.getStringExtra(KEY_RECIPE_ID);
                helper.deleteRecipe(id, new CustomCallback() {
                    @Override
                    public void success(Message message) {
                        Intent intent = new Intent(EditRecipeActivity.this, RecipesActivity.class);
                        Launcher.launch(intent);
                        finish();
                    }

                    @Override
                    public void error(Message message) {
                        Toast.makeText(getApplicationContext(), "An Error Occurred.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        btnIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ingredientsLayout = findViewById(R.id.ll_ingredients_edit);
                ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(ingredientsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                ingredientsLayout.addView(et);
                ingredientsLines = ingredientsLines + 1;
                Log.d("New Ingredient", Integer.toString(ingredientsLines));
            }
        });

        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout stepsLayout = findViewById(R.id.ll_steps_edit);
                stepsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(stepsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                stepsLayout.addView(et);
                stepsLines = stepsLines + 1;
            }
        });

        this.Cancel();
        this.Update();
    }

    public ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    public void Cancel() {
        this.btnCancel = findViewById(R.id.cancelBtn_er);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditRecipeActivity.this, RecipesActivity.class);
                Launcher.launch(intent);
                finish();
            }
        });
    }

    public void Update() {
        this.btnUpdate = findViewById(R.id.updateBtn_er);
        this.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditRecipeActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }
}