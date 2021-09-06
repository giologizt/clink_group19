package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobdeve.group19.clink.model.ApiHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // JSON Token  Key
    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        if(sp.getString(JSON_TOKEN_KEY, "").equals("")) {
            Intent intent = new Intent (MainActivity.this, LoginActivity.class);
            Launcher.launch(intent);
        } else {
            Intent intent = new Intent (MainActivity.this, RecipesActivity.class);
            Launcher.launch(intent);
        }

    }
    // For launching Intent
    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

}