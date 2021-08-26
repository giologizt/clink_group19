package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class AddRecipeActivity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnPublish;

    private Button btnIngredients;
    private Button btnSteps;

    private int ingredientsLines;
    private int stepsLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        this.ingredientsLines = 1;
        this.stepsLines = 1;

        this.btnIngredients = findViewById(R.id.btn_addIngredient);
        this.btnSteps = findViewById(R.id.btn_steps);

        btnIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ingredientsLayout = findViewById(R.id.ll_ingredients);
                ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(ingredientsLines + 1);
                ingredientsLayout.addView(et);
                ingredientsLines++;
            }
        });

        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout stepsLayout = findViewById(R.id.ll_steps);
                stepsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(ingredientsLines + 1);
                stepsLayout.addView(et);
                stepsLines++;
            }
        });

        this.Cancel();
        this.Publish();
    }

//    private OnClickListener onClick() {
//        return new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mLayout.addView(createNewTextView(mEditText.getText().toString()));
//            }
//        };
//    }
//
//    private TextView createNewTextView(String text) {
//        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        final TextView textView = new TextView(this);
//        textView.setLayoutParams(lparams);
//        textView.setText("New text: " + text);
//        return textView;
//    }



    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    private void Cancel() {
        this.btnCancel = findViewById(R.id.cancelBtn);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddRecipeActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }

    private void Publish() {
        this.btnPublish = findViewById(R.id.publishBtn);
        this.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddRecipeActivity.this, RecipesActivity.class);

                Launcher.launch(intent);
            }
        });
    }
}