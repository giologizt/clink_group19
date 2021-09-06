package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout fabRecipe;
    private LinearLayout llRecipes;
    private LinearLayout llSearch;
    private LinearLayout llPassword;
    private  Button btnEdit;
//    private Button btnEdit;
//    private TextView tvName;
//    private EditText ptName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.addRecipe();
        this.Recipes();
        //this.Search();
        this.Password();
        this.EditProfile();

//        this.btnEdit = findViewById(R.id.editBtn);
//        this.tvName = findViewById(R.id.editnameTv);
//        this.ptName = findViewById(R.id.editnamePt);

//        this.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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
        this.fabRecipe = findViewById(R.id.addrecipeFab_profile);
        this.fabRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, AddRecipeActivity.class);

                Launcher.launch(intent);
            }
        });
    }

//    private void Search() {
//        this.llSearch = findViewById(R.id.recipesLl_profile);
//        this.llSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (ProfileActivity.this, SearchActivity.class);
//
//                Launcher.launch(intent);
//            }
//        });
//    }

    private void Recipes() {
        this.llRecipes = findViewById(R.id.recipesLl_profile);
        this.llRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Password() {
        this.llPassword = findViewById(R.id.passwordLl);
        this.llPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, PasswordActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void EditProfile() {
        this.btnEdit = findViewById(R.id.editprofileBtn);
        this.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, EditProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }
}