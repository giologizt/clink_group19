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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Review;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddReviewActivity extends AppCompatActivity {

    public static final String KEY_FEEDBACK = "KEY_FEEDBACK";

    private static final String USER_ID_KEY = "USER_ID_KEY";

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";

    private Button btnCancel, btnPost;

    private EditText etReview;

    private ApiHelper helper;
    private ExecutorService executorService;

    Intent intent;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        etReview = findViewById(R.id.et_review);

        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        intent = getIntent();

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
                finish();
            }
        });
    }

    public void Post() {
        this.btnPost = findViewById(R.id.postBtn_writefb);
        this.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String review = etReview.getText().toString();

                String idRecipe = intent.getStringExtra(KEY_RECIPE_ID);
                String idUser = sp.getString(USER_ID_KEY, "");

                if(review.equals("")){
                    Toast.makeText(getApplicationContext(), "Error: Please fill up the text field.", Toast.LENGTH_SHORT).show();
                } else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("USERID", idUser);
                            Log.d("RECIPEID", idRecipe);
                            Log.d("BODY", review);
                            Intent intent = new Intent (AddReviewActivity.this, RecipesActivity.class);

                            Review newreview = new Review(idUser, review, idRecipe);
                            helper.addReview(newreview, idRecipe, new CustomCallback() {
                                @Override
                                public void success(Message message) {
                                    Intent intent = new Intent(AddReviewActivity.this, ExpandActivity.class);
                                    intent.putExtra(KEY_RECIPE_ID, idRecipe);
                                    Launcher.launch(intent);
                                    finish();
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
                }
            }
        });
    }
}