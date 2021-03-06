package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Ingredients;
import com.mobdeve.group19.clink.model.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AddRecipeActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";


    private Button btnCancel;
    private Button btnPublish;

    private Button btnIngredients;
    private Button btnSteps;

    private Button btnImage;

    private EditText etName, etprepTime;

    private int ingredientsLines;
    private int stepsLines;

    private ApiHelper helper;
    private ExecutorService executorService;

    private Uri imageUri;

    private static final String TAG = "AddRecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        this.ingredientsLines = 100;
        this.stepsLines = 200;

        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        this.btnIngredients = findViewById(R.id.btn_addIngredient);
        this.btnSteps = findViewById(R.id.btn_steps);

        this.btnImage = findViewById(R.id.btn_addImage);

        this.etName = findViewById(R.id.et_editname);
        this.etprepTime = findViewById(R.id.et_prepTime);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Camera
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intent);

                //Access image from gallery
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);

            }
        });

        //Button to create more edit text for ingredients
        btnIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ingredientsLayout = findViewById(R.id.ll_ingredients);
                ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(ingredientsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                et.setTextSize(14);
                et.setHint("add ingredient here");
                ingredientsLayout.addView(et);
                ingredientsLines = ingredientsLines + 1;
                Log.d("New Ingredient", Integer.toString(ingredientsLines));
            }
        });

        //Button to create more edit text for steps
        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout stepsLayout = findViewById(R.id.ll_steps);
                stepsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(stepsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                stepsLayout.addView(et);
                et.setTextSize(14);
                et.setHint("Add step here.");
                stepsLines = stepsLines + 1;

            }
        });

        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();

        int prepTime;

        this.Cancel();

        this.Publish();

    }

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    //Function for cancelling a recipe post
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

    //Function to have all ingredient inputs into a single array list
    private ArrayList<Ingredients> getIngredients(){
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        Log.d("Ingredient Lines", Integer.toString(ingredientsLines));
        Log.d("Ingredient Lines", Integer.toString(this.ingredientsLines));
        for(int i = 0; i <= (this.ingredientsLines - 100); i++){
            EditText ingredient;

            if(i == 0) {
                ingredient = findViewById(R.id.et_ingredient0);
            } else {
                ingredient = findViewById(i + 100);
            }

            Log.d("Ingredients", ingredient.getText().toString());
            ingredients.add(new Ingredients(ingredient.getText().toString()));
        }

        return ingredients;
    }

    //Function to have all steps inputs into a single array list
    private ArrayList<String> getSteps(){
        ArrayList<String> steps = new ArrayList<>();
        for(int i = 0; i <= (this.stepsLines - 200); i++){
            EditText step;

            if(i == 0) {
                step = findViewById(R.id.et_steps0);
            } else {
                step = findViewById(i + 200);
            }

            Log.d("Steps", step.getText().toString());
            steps.add(step.getText().toString());
        }

        return steps;
    }

    //Function for posting a recipe
    private void Publish() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        this.btnPublish = findViewById(R.id.publishBtn);
        this.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String time = etprepTime.getText().toString();
                ArrayList<Ingredients> ingredients =  getIngredients();
                ArrayList<String> steps = getSteps();
                Uri image = imageUri;

                Intent intent = new Intent(AddRecipeActivity.this, RecipesActivity.class);
                Log.d("Add Recipe", "Publish was reached");
                Log.d(TAG, "Recipe Name: " + name);

                //Check whether all text fields have inputs
                if (name.equals("") || time.equals("") || ingredients.get(0).getIngredientName().equals("") ||
                        steps.get(0).equals("")) {
                    Toast.makeText(getApplicationContext(), "Error: Please fill up all text fields.", Toast.LENGTH_SHORT).show();
                    Log.d("Add Recipe", name);
                    Log.d("Add Recipe", time);
                    Log.d("Add Recipe", ingredients.get(0).getIngredientName());
                    Log.d("Add Recipe", steps.get(0));
                } else {
                    int prepTime = Integer.parseInt(time);

                    File imageFile = new File(getRealPathFromURI(imageUri));
                    String author = sp.getString(JSON_TOKEN_KEY, "");
                    Log.d("Author", author);

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            helper.postRecipe(steps, ingredients, name, prepTime, imageUri, imageFile, author, new CustomCallback() {
                                @Override
                                //Successfully added recipe
                                public void success(Message message) {
                                    Toast.makeText(getApplicationContext(), "Recipe added", Toast.LENGTH_SHORT).show();
                                    Launcher.launch(intent);
                                }

                                //Unsuccessfully added recipe
                                @Override
                                public void error(Message message) {
                                    Toast.makeText(getApplicationContext(), "An error occurred.", Toast.LENGTH_SHORT).show();
                                }

                                //error on the server side
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

    //Function to get the image from the gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && data != null && resultCode == RESULT_OK
            && data.getData() != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                imageUri = data.getData();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Error: Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    //Function for getting the file path from Uri
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}