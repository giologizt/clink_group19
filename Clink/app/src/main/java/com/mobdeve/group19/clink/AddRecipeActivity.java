package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Ingredients;
import com.mobdeve.group19.clink.model.Message;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AddRecipeActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private Button btnCancel;
    private Button btnPublish;

    private Button btnIngredients;
    private Button btnSteps;

    private Button btnImage;

    private TextView etName, etprepTime;

    private int ingredientsLines;
    private int stepsLines;

    private ApiHelper helper;
    private ExecutorService executorService;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        this.ingredientsLines = 1;
        this.stepsLines = 1;

        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        this.btnIngredients = findViewById(R.id.btn_addIngredient);
        this.btnSteps = findViewById(R.id.btn_steps);

        this.btnImage = findViewById(R.id.btn_addImage);

        this.etName = findViewById(R.id.et_name);
        this.etprepTime = findViewById(R.id.et_prepTime);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intent);

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        btnIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ingredientsLayout = findViewById(R.id.ll_ingredients);
                ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(Integer.parseInt("et_ingredient" + (ingredientsLines + 1)));
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
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
                et.setId(Integer.parseInt("et_steps" + (stepsLines + 1)));
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                stepsLayout.addView(et);
                stepsLines++;
            }
        });

        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();

        String name = etName.getText().toString();
        int prepTime;

        for(int i = 0; i < ingredientsLines; i++){
            TextView ingredient;

            if(i == 0) {
                ingredient = findViewById(R.id.et_ingredient0);
            } else {
                ingredient = findViewById(Integer.parseInt("et_ingredient" + (i)));
            }

            Ingredients ingrdientName = new Ingredients(ingredient.getText().toString());
            ingredients.add(ingrdientName);
        }

        for(int i = 0; i < stepsLines; i++){
            TextView step;

            if(i == 0) {
                step = findViewById(R.id.et_steps0);
            } else {
                step = findViewById(Integer.parseInt("et_steps" + (i)));
            }

            //TextView step = findViewById(Integer.parseInt("et_steps" + (i)));
            steps.add(step.getText().toString());
        }


        this.Cancel();

        if(name.equals("") || etprepTime.getText().toString().equals("") || ingredients.get(0).equals("") ||
        steps.get(0).equals("")) {
            Toast.makeText(getApplicationContext(), "Error: Please fill up all text fields.", Toast.LENGTH_SHORT).show();
        } else {
            prepTime = Integer.parseInt(etprepTime.getText().toString());
            this.Publish(name, prepTime, ingredients, steps, imageUri);
        }
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

    private void Publish(String name, int prepTime, ArrayList<Ingredients> ingridients, ArrayList<String> steps, Uri imageUri) {
        this.btnPublish = findViewById(R.id.publishBtn);
        this.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddRecipeActivity.this, RecipesActivity.class);

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        helper.postRecipe(steps, ingridients, name, prepTime, imageUri, new CustomCallback() {
                            @Override
                            public void success(Message message) {
                                Toast.makeText(getApplicationContext(), "Recipe added", Toast.LENGTH_SHORT).show();
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

                Launcher.launch(intent);
            }
        });
    }

    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            imageUri = data.getData();
        } else {
            Toast.makeText(getApplicationContext(), "Error: Image not found", Toast.LENGTH_SHORT).show();
        }
    }
}