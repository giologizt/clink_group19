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

public class EditRecipeActivity extends AppCompatActivity {

    private static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";
    public static final int PICK_IMAGE = 1;
    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";
    private static final String KEY_PREPTIME = "KEY_PREPTIME";
    private static final String KEY_IMAGE = "KEY_IMAGE";
    private static final String KEY_STEPS = "KEY_STEPS";
    private static final String KEY_INGREDIENTS = "KEY_INGRDIENTS";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_INGREDIENTS_SIZE = "KEY_INGREDIENTS_SIZE";

    private Button btnCancel, btnUpdate, btnDelete;

    private Button btnIngredients;
    private Button btnSteps;
    private Button btnImage;

    private EditText etName, etprepTime;

    private int ingredientsLines;
    private int stepsLines;
    private int imageExist;

    ApiHelper helper;
    SharedPreferences sp;
    private ExecutorService executorService;

    private Uri imageUri;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        this.btnDelete = findViewById(R.id.deleteBtn);

        this.ingredientsLines = 100;
        this.stepsLines = 200;
        this.imageExist = 0;

        this.imageUri = null;

        this.btnIngredients = findViewById(R.id.btn_addIngredient_edit);
        this.btnSteps = findViewById(R.id.btn_steps_edit);
        this.btnImage = findViewById(R.id.btn_image);

        this.executorService = Executors.newSingleThreadExecutor();

        this.etName = findViewById(R.id.et_editname);
        this.etprepTime = findViewById(R.id.et_editpreptime);

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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                LinearLayout ingredientsLayout = findViewById(R.id.ll_ingredients_edit);
                ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(ingredientsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                et.setTextSize(14);
                ingredientsLayout.addView(et);
                ingredientsLines = ingredientsLines + 1;
                Log.d("New Ingredient", Integer.toString(ingredientsLines));
            }
        });

        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout stepsLayout = findViewById(R.id.ll_steps_edit);
                stepsLayout.setOrientation(LinearLayout.VERTICAL);
                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                et.setLayoutParams(p);
                et.setId(stepsLines + 1);
                et.setTextColor(Color.BLACK);
                et.setHighlightColor(Color.BLACK);
                et.setTextSize(14);
                stepsLayout.addView(et);
                stepsLines = stepsLines + 1;
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        this.btnUpdate = findViewById(R.id.updateBtn_er);
        this.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String time = etprepTime.getText().toString();
                ArrayList<Ingredients> ingredients =  getIngredients();
                ArrayList<String> steps = getSteps();
                Uri image = imageUri;

                if (name.equals("") && time.equals("") && ingredients.get(0).getIngredientName().equals("") &&
                        steps.get(0).equals("") && imageExist == 0) {
                    Toast.makeText(getApplicationContext(), "Error: Please fill up at least one text field.", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = getIntent();



                    String id = intent.getStringExtra(KEY_RECIPE_ID);
                    String currentName = intent.getStringExtra(KEY_NAME);
                    int prepTime = intent.getIntExtra(KEY_PREPTIME, 0);
                    int ingExists = 0;
                    ArrayList<Ingredients> finalIngredients = new ArrayList<>();
                    ArrayList<Ingredients> ing = new ArrayList<>();

                    if(steps.get(0).equals("")) {
                        steps = intent.getStringArrayListExtra(KEY_STEPS);
                    }

                    if(ingredients.get(0).getIngredientName().equals("")){
                        for(int i = 0; i < intent.getIntExtra(KEY_INGREDIENTS_SIZE, 0); i++){
                            Log.d("Ingredient", intent.getStringExtra(KEY_INGREDIENTS + i));
                            ing.add(new Ingredients(intent.getStringExtra(KEY_INGREDIENTS + i)));
                        }
                        ingExists = 1;
                    }

                    if(!time.equals("")){
                        prepTime = Integer.parseInt(time);
                    }

                    if(name.equals("")){
                       name = currentName;
                    }

                    String author = sp.getString(JSON_TOKEN_KEY, "");
                    Log.d("Author", author);

                    int finalPrepTime = prepTime;
                    ArrayList<String> finalSteps = steps;

                    if(ingExists == 0){
                        finalIngredients = ingredients;
                    } else {
                        finalIngredients = ing;
                    }

                    Log.d("Add Recipe", name);
                    Log.d("Add Recipe", String.valueOf(finalPrepTime));
                    Log.d("Add Recipe", finalSteps.get(0));
                    Log.d("Add Recipe", finalIngredients.get(0).getIngredientName());

                    ArrayList<Ingredients> finalIngredients1 = finalIngredients;
                    String finalName = name;

                    if(imageExist == 1) {
                        Log.d("ImageExist", String.valueOf(imageExist));
                        File imageFile = new File(getRealPathFromURI(imageUri));

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                helper.updateImage(imageUri, imageFile, author, id, new CustomCallback() {
                                    @Override
                                    public void success(Message message) {
                                        Toast.makeText(getApplicationContext(), "Image edited", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void error(Message message) {

                                    }

                                    @Override
                                    public void failure(Throwable t) {

                                    }
                                });

                                helper.updateRecipe(id, finalSteps, finalIngredients1, finalName, finalPrepTime, author, new CustomCallback() {
                                    @Override
                                    public void success(Message message) {
                                        Intent intent = new Intent (EditRecipeActivity.this, RecipesActivity.class);
                                        Toast.makeText(getApplicationContext(), "Recipe edited", Toast.LENGTH_SHORT).show();
                                        Launcher.launch(intent);
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
                    } else {
                        executorService.execute(new Runnable() {
                            @Override
                            //imageUri, imageFile,
                            public void run() {
                                helper.updateRecipe(id, finalSteps, finalIngredients1, finalName, finalPrepTime, author, new CustomCallback() {
                                    @Override
                                    public void success(Message message) {
                                        Intent intent = new Intent (EditRecipeActivity.this, RecipesActivity.class);
                                        Toast.makeText(getApplicationContext(), "Recipe edited", Toast.LENGTH_SHORT).show();
                                        Launcher.launch(intent);
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


            }
        });
    }

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

    private ArrayList<String> getSteps(){
        ArrayList<String> steps = new ArrayList<>();
        for(int i = 0; i <= (this.stepsLines - 200); i++){
            EditText step;

            if(i == 0) {
                step = findViewById(R.id.et_steps0);
            } else {
                step = findViewById(i + 200);
            }

            //TextView step = findViewById(Integer.parseInt("et_steps" + (i)));
            Log.d("Steps", step.getText().toString());
            steps.add(step.getText().toString());
        }

        return steps;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                imageUri = data.getData();
                imageExist = 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Error: Image not found", Toast.LENGTH_SHORT).show();
        }
    }

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