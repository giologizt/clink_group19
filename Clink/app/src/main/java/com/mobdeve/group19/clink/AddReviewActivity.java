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

public class AddReviewActivity extends AppCompatActivity {

    public static final String KEY_FEEDBACK = "KEY_FEEDBACK";

    private Button btnCancel, btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        this.Cancel();
        this.Post();
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
        this.btnCancel = findViewById(R.id.cancelBtn_writefb);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddReviewActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    public void Post() {
        this.btnPost = findViewById(R.id.postBtn_writefb);
        this.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddReviewActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }
}