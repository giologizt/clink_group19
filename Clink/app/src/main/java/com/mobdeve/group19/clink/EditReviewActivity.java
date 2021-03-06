package com.mobdeve.group19.clink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditReviewActivity extends AppCompatActivity {

    // Intent Keys
    private static final String REVIEW_ID_KEY = "REVIEW_ID_KEY";
    private static final String RECIPE_ID_KEY = "RECIPE_ID_KEY";

    private Button editBtn, cancelBtn;
    private TextView editReviewTv;

    private ExecutorService executorService;

    private ApiHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        this.editBtn = findViewById(R.id.editBtn_editfb);
        this.cancelBtn = findViewById(R.id.cancelBtn_editfb);

        this.editReviewTv = findViewById(R.id.editReviewTv);

        Intent intent = getIntent();

        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        // Listener when cancel button is pressed. Closes the intent.
        this.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener when edit button is pressed.
        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewId = intent.getStringExtra(REVIEW_ID_KEY);
                String recipeId = intent.getStringExtra(RECIPE_ID_KEY);
                String review = editReviewTv.getText().toString();

                if(review.equals(""))
                    Toast.makeText(getApplicationContext(), "The Edit Review Field is Empty.", Toast.LENGTH_SHORT).show();
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            helper.editReview(recipeId, reviewId, review, new CustomCallback() {
                                // If successfully edited the review, close the intent.
                                @Override
                                public void success(Message message) {
                                    finish();
                                }

                                // If the server passes an error, this is called.
                                @Override
                                public void error(Message message) {
                                    Toast.makeText(getApplicationContext(), "An error occurred.", Toast.LENGTH_SHORT).show();

                                }

                                // If the server timed out or there is a problem with formatting, this is called.
                                @Override
                                public void failure(Throwable t) {
                                    Toast.makeText(getApplicationContext(), "A server error occurred.", Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                                }
                            });
                        }
                    });

                }

            }
        });

    }
}