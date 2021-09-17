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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Profile;
import com.mobdeve.group19.clink.model.ProfileCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    // JSON TOKEN KEY from shared preferences
    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";
    private static final String USER_ID_KEY = "USER_ID_KEY";

    private LinearLayout llRecipes;
    private LinearLayout llPassword;
    private LinearLayout fabRecipe;

    private Button btnEdit, btnLogout;

    private TextView tvName, tvEmail, tvBirthday;

    private ExecutorService executorService;

    private ApiHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        this.helper = new ApiHelper();

        executorService = Executors.newSingleThreadExecutor();

        // For the getProfile service
        this.tvName = findViewById(R.id.tv_profileName);
        this.tvEmail = findViewById(R.id.tv_profileEmail);
        this.tvBirthday = findViewById(R.id.tv_profileBirthday);

        this.fabRecipe = findViewById(R.id.addrecipeFab_profile);

        // Executs a single thread to call the API in the background
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                // Uses the authentication token to get the userID and passed to the server
                String authToken = sp.getString(JSON_TOKEN_KEY, "");

                // Calls the API Helper of getProfile
                helper.getProfile(authToken, new ProfileCallback() {
                    @Override
                    public void success(Message message, Profile profile) {
                        tvName.setText(profile.getFullName());
                        tvEmail.setText(profile.getEmail());
                        tvBirthday.setText(profile.getBirthday());
                    }

                    @Override
                    public void error(Message message) {
                        Toast.makeText(getApplicationContext(), "An error occurred.", Toast.LENGTH_SHORT).show();
                        tvName.setText("");
                        tvEmail.setText("");
                        tvBirthday.setText("");

                    }

                    @Override
                    public void failure(Throwable t) {

                        Toast.makeText(getApplicationContext(), "A server error occurred.", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        // For logout
        this.btnLogout = findViewById(R.id.logoutBtn);

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);

        // When the user is logged out, his JSON Token is deleted from the SharedPreferences and is redirected to the login page.
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(JSON_TOKEN_KEY, "");
                editor.putString(USER_ID_KEY, "");
                editor.apply();
                Launcher.launch(intent);
                finish();
            }
        });

        // For button actions
        this.recipes();
        this.password();
        this.addRecipe();
        this.editProfile();
    }

    // Function for Launching Intent
    private ActivityResultLauncher Launcher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        }
    );

    // Redirects to the Recipes Activity
    private void recipes() {
        this.llRecipes = findViewById(R.id.recipesLl_profile);
        this.llRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    // Redirects to the Change Password Activity
    private void password() {
        this.llPassword = findViewById(R.id.passwordLl);
        this.llPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, PasswordActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    // Redirects to the Edit Profile Activity
    private void editProfile() {
        this.btnEdit = findViewById(R.id.editprofileBtn);
        this.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, EditProfileActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    // Redirects to the Add Recipe Activity
    public void addRecipe() {
        this.fabRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, AddRecipeActivity.class);
                Launcher.launch(intent);
            }
        });
    }
}