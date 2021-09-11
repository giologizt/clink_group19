package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;

public class EditRecipeActivity extends AppCompatActivity {

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    private Button btnCancel, btnUpdate, btnDelete;

    ApiHelper helper;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        this.btnDelete = findViewById(R.id.deleteBtn);

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