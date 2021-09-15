package com.mobdeve.group19.clink.model;


import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class ApiHelper {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://10.0.2.2:3000";
    private static String authToken;


    Message message;
    String filePath = "";
    //ArrayList<Recipe> recipes = new ArrayList<>();

    public ApiHelper() {

        CookieHandler cookieHandler = new CookieManager();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);

    }



    public void login(String username, String password, CustomCallback callback) {
        Login loginInformation = new Login(username, password);
        Call<Login> call = retrofitInterface.executeLogin(loginInformation);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - Login", response.body().getMessage());
                    //Log.d("ApiHelper - Login", response.body().getId());
                    Log.d("ApiHelper - Login", response.body().getId());

                    authToken = "bearer " + response.body().getAccessToken();
                    String id = response.body().getId();
                    Message message = new Message(id, "Login Successful", response.code(), authToken);
                    callback.success(message);

                } else {
                    Log.d("ApiHelper - Login", "Invalid Credentials Given");
                    Message message = new Message("Login Failed", response.code());
                    callback.error(message);

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("ApiHelper - Login", "Something went wrong");
                callback.failure(t);

            }
        });

    }

    public void register(String username, String fullname, String email, String birthday,
                         String password, CustomCallback callback) {
        Register registerInformation = new Register(username, fullname, email, birthday, password);
        Call<Register> call = retrofitInterface.executeRegister(registerInformation);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - Register", response.body().getMessage());
                    Log.d("ApiHelper - Register", response.body().getUsername());
                    Message message = new Message(response.code());
                    callback.success(message);
                    //System.out.println(response.body().getMessage());
                    //System.out.println(response.body().getUsername());
                } else {
                    Log.d("ApiHelper - Register", response.errorBody().toString());
                    Message message = new Message(response.code());
                    callback.error(message);
                    //System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.d("ApiHelper - Register", "Something went wrong");
                callback.failure(t);
                t.printStackTrace();
            }
        });
    }

    public void getProfile(String authToken, ProfileCallback callback) {

        Call<Profile> call = retrofitInterface.executeGetProfile(authToken);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - getProfile", response.body().getEmail());
                    Log.d("ApiHelper - getProfile", response.body().getUsername());
                    Log.d("ApiHelper - getProfile", response.body().getFullName());
                    Log.d("ApiHelper - getProfile", response.body().getBirthday());
                    Message message = new Message(response.code());
                    Profile profile = new Profile(response.body().getId(), response.body().getUsername(), response.body().getEmail(),
                            response.body().getFullName(), response.body().getBirthday(), response.body().getPassword());
                    callback.success(message, profile);

                    //System.out.println(response.body().getUsername());
                    //System.out.println(response.body().getEmail());
                    //System.out.println(response.body().getFullName());
                    //System.out.println(response.body().getBirthday());
                } else {
                    Log.d("ApiHelper - getProfile", "Cannot find profile");
                    Message message = new Message(response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - getProfile", "Something went wrong");
                callback.failure(t);
                t.printStackTrace();
            }
        });
    }

    public void getUsername(String id, ProfileCallback callback) {
        Call<Profile> call = retrofitInterface.executeGetUsername(id);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()) {
                    Message message = new Message("Successfully retrieved username.", response.code());
                    Profile profile = new Profile(response.body().getUsername());
                    callback.success(message, profile);
                } else {
                    Message message = new Message("Error retrieving username.", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                callback.failure(t);
            }
        });
    }

    public void editProfile(String email, String fullName, String birthday, String authToken, CustomCallback callback) {
        Profile profileInformation = new Profile(email, fullName, birthday);
        Call<Profile> call = retrofitInterface.executeEditProfile(authToken, profileInformation);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - editProfile", "Profile edited");
                    //Log.d("ApiHelper - editProfile", response.body().getEmail());
                    //Log.d("ApiHelper - editProfile", response.body().getUsername());
                    //Log.d("ApiHelper - editProfile", response.body().getFullName());
                    //Log.d("ApiHelper - editProfile", response.body().getBirthday());
                    //System.out.println("Profile Edited.");
                    Message message = new Message("Profile edited", response.code());
                    callback.success(message);
                } else {
                    Message message = new Message("Profile not edited", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - editProfile", "Something went wrong");
                callback.failure(t);
                t.printStackTrace();
            }
        });
    }

    public void changePassword(String newpassword, String oldpassword, String authToken, CustomCallback callback) {
        Profile passwordInformation = new Profile(newpassword, oldpassword);
        Call<Profile> call = retrofitInterface.executeChangePassword(authToken, passwordInformation);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - changePass", "Password changed");
                    //Log.d("ApiHelper - changePass", response.body().getNewpassword());
                    Message message = new Message("Password changed", response.code());
                    callback.success(message);
                    //System.out.println("Password Changed.");
                } else {
                    Message message = new Message("Password not changed", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - changePass", "Something went wrong");
                callback.failure(t);
                t.printStackTrace();
            }
        });
    }

    public void postRecipe(ArrayList<String> recipesteps, ArrayList<Ingredients> recipeingredients, String recipename,
                           Integer recipeprepTime, Uri imageUri, File imageFile, String authToken, CustomCallback callback){

        //File file = new File(filePath);
        File file = new File(imageUri.getPath());
        String fileName = file.getName();

        Log.d("ApiHelper - postRecipe", imageFile.getName());
        Log.d("ApiHelper - postRecipe", imageUri.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("recipe-image", imageFile.getName(), requestBody);


        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), recipename);
        RequestBody prepTime = RequestBody.create(MediaType.parse("multipart/form-data"), recipeprepTime.toString());
        //RequestBody author = RequestBody.create(MediaType.parse("multipart/form-data"), recipeauthor);
        //RequestBody steps = RequestBody.create(MediaType.parse(), recipesteps);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("prepTime", prepTime);
        //map.put("author", author);

        for(int i = 0; i < recipesteps.size(); i++) {
            RequestBody steps = RequestBody.create(MediaType.parse("multipart/form-data"), recipesteps.get(i));
            map.put("steps[" + i + "]", steps);
        }

        for(int i = 0; i < recipeingredients.size(); i++) {
            //RequestBody quantity = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(recipeingredients.get(i).getQuantity()));
            RequestBody ingredientName = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getIngredientName());
            //map.put("ingredients[" + i + "][quantity]", quantity);
            map.put("ingredients[" + i + "][ingredientName]", ingredientName);
            //map.put("ingredients[" + i + "]", ingredientName);
        }

        Call<Recipe> call = retrofitInterface.executePostRecipe(authToken, filePart, map);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - postRecipe", "Recipe added");
                    Message message = new Message("Recipe added", response.code());
                    callback.success(message);
                    //Log.d("ApiHelper - postRecipe", response.body().getAuthor());
                    Log.d("ApiHelper - postRecipe", response.body().getName());
                    Log.d("ApiHelper - postRecipe", response.body().getPrepTime().toString());
                    Log.d("ApiHelper - postRecipe", response.body().getSteps().toString());
                    Log.d("ApiHelper - postRecipe", response.body().getIngredients().toString());
                } else {
                    Log.d("ApiHelper - postRecipe", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - postRecipe", "Something went wrong");
                t.printStackTrace();
            }
        });

    }

    public void getRecipes(RecipesCallback callback){
        Gson gson = new Gson();
        Call<ArrayList<Recipe>> call = retrofitInterface.executeGetRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if(response.isSuccessful()){

                    //String result = response.body().toString();
                    //System.out.println(result);
                    //Type dataType = new TypeToken<Collection<Recipe>>() {}.getType();
                    //Collection<Recipe> enums = gson.fromJson(response.body().toString(), dataType);
                    // Recipe[] recipe = enums.toArray(new Recipe[enums.size()]);
                    //recipe[0].getAuthor()
                    //add callback method for recipe arraylist
                    Log.d("ApiHelper - getRecipes", "Recipes collected");
                    //for(Recipe recipe: enums){
                    //    Log.d("ApiHelper - getRecipes", recipe.toString());
                    //}
                    // Log.d("ApiHelper - getRecipes", recipe[0].getAuthor());
                    // Log.d("ApiHelper - getRecipes", recipe[0].getIngredients().toString());
                    // Log.d("ApiHelper - getRecipes", recipe[0].getName());

                    ArrayList<Recipe> recipes = response.body();

                    if(recipes != null) {
                        //File file = new File(recipes.get(0).getImage());
                        //URI image = file.toURI();

                        //Log.d("ApiHelper - getRecipes", image.toString());
                        //Log.d("ApiHelper - getRecipes", recipes.get(0).getPrepTime().toString());
                        //Log.d("ApiHelper - getRecipes", recipes.get(0).getSteps().get(0));

                        //Log.d("ApiHelper - getRecipes", recipes.get(1).getPrepTime().toString());
                        //Log.d("ApiHelper - getRecipes", recipes.get(1).getSteps().get(0));

                        Message message = new Message("Successful.", response.code());

                        callback.success(message, recipes);
                    } else {
                        Log.d("ApiHelper - getRecipes", "DB is empty");
                    }
                } else {
                    Log.d("ApiHelper - getRecipes", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                callback.failure(t);
            }
        });

    }

    public void getRecipe(String id, RecipeCallback callback) {
        Call<Recipe> call = retrofitInterface.executeGetRecipe(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - getRecipe", response.body().getName());
                    //Log.d("ApiHelper - getRecipe", response.body().getAuthor());
                    Log.d("ApiHelper - getRecipe", response.body().getPrepTime().toString());
                    Log.d("ApiHelper - getRecipe", response.body().getSteps().toString());

                    Recipe recipe = response.body();
                    Message message = new Message("Successful.", response.code());

                    callback.success(message, recipe);
                } else {
                    Log.d("ApiHelper - getRecipe", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - getRecipe", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void updateRecipe(String ID, ArrayList<String> recipesteps, ArrayList<Ingredients> recipeingredients, String recipename,
                             int recipeprepTime, String authToken, CustomCallback callback){ //Uri imageUri, File imageFile,

        //File file = new File(filePath);
        //File file = new File(imageUri.getPath());
        //String fileName = file.getName();

        Log.d("ApiHelper - ID", ID);
        //Log.d("ApiHelper - postRecipe", imageUri.getPath());

        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        //MultipartBody.Part filePart = MultipartBody.Part.createFormData("recipe-image", imageFile.getName(), requestBody);

        //RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), recipename);
        //RequestBody prepTime = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(recipeprepTime));
        //RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), ID);
        //RequestBody steps = RequestBody.create(MediaType.parse(), recipesteps);

        //HashMap<String, RequestBody> map = new HashMap<>();
        //map.put("name", name);
        //map.put("prepTime", prepTime);
        //map.put("id", id);

        //Log.d("Api Helper", name.toString());
        //Log.d("Api Helper", String.valueOf(recipeprepTime));
        //Log.d("Api Helper", recipeingredients.get(0).getIngredientName());
        //Log.d("Api Helper", recipesteps.get(0));

        //for(int i = 0; i < recipesteps.size(); i++) {
        //    RequestBody steps = RequestBody.create(MediaType.parse("multipart/form-data"), recipesteps.get(i));
        //    map.put("steps[" + i + "]", steps);
        //}

        //for(int i = 0; i < recipeingredients.size(); i++) {
            //RequestBody quantity = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(recipeingredients.get(i).getQuantity()));
        //    RequestBody ingredientName = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getIngredientName());
            //map.put("ingredients[" + i + "][quantity]", quantity);
        //    map.put("ingredients[" + i + "][ingredientName]", ingredientName);
            //map.put("ingredients[" + i + "]", ingredientName);
        //}

        Recipe recipe = new Recipe(recipesteps, recipeingredients, recipename, recipeprepTime, ID);

        //filepart
        //Call<Recipe> call = retrofitInterface.executeUpdateRecipe(authToken, map);
        Call<Recipe> call = retrofitInterface.executeUpdateRecipe(authToken, recipe);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - upRecipe", "Recipe updated");
                    //Log.d("ApiHelper - upRecipe", response.body().getAuthor());
                    //Log.d("ApiHelper - upRecipe", response.body().getName());
                    //Log.d("ApiHelper - upRecipe", response.body().getPrepTime().toString());
                    //Log.d("ApiHelper - upRecipe", response.body().getSteps().toString());
                    //Log.d("ApiHelper - upRecipe", response.body().getIngredients().toString());
                    Message message = new Message("Successful", response.code());
                    callback.success(message);
                } else {
                    Log.d("ApiHelper - upRecipe", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - upRecipe", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void updateImage(Uri imageUri, File imageFile, String authToken, String ID, CustomCallback callback){
        File file = new File(imageUri.getPath());
        String fileName = file.getName();

        Log.d("New Image", file.getName());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("recipe-image", imageFile.getName(), requestBody);

        RequestBody _id = RequestBody.create(MediaType.parse("multipart/form-data"), ID);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("_id", _id);

        Call<Recipe> call = retrofitInterface.executeUpdateImage(authToken, filePart, map);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - upImage", "Image updated");
                    Message message = new Message("Successful", response.code());
                    callback.success(message);
                } else {
                    Log.d("ApiHelper - upImage", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }

    public void searchRecipe(String searchQuery, RecipesCallback callback){
        Gson gson = new Gson();
        Call<ArrayList<Recipe>> call = retrofitInterface.executeSearchRecipe(searchQuery);

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Recipe> recipes = response.body();
                    //File file = new File(recipes.get(0).getImage());
                    //URI image = file.toURI();

                    Log.d("ApiHelper - search", "Recipes not collected");
                    Message message = new Message("Successful.", response.code());
                    callback.success(message, recipes);
                } else {
                    Log.d("ApiHelper - search", response.errorBody().toString());
                    Message message = new Message("Error.", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d("ApiHelper - search", "Something went wrong");
                callback.failure(t);
            }
        });
    }

    public void deleteRecipe(String id, CustomCallback callback){
        Call<Recipe> call = retrofitInterface.executeDeleteRecipe(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - delRecipe", "Recipe deleted");
                   //Log.d("ApiHelper - delRecipe", response.body().getMessage());
                    Message message = new Message("Successful", response.code());
                    callback.success(message);
                } else {
                    Log.d("ApiHelper - delRecipe", response.errorBody().toString());
                    Message message = new Message("Error", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - delRecipe", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void getReviews(String recipeId, ReviewCallback callback) {
        Gson gson = new Gson();
        Call<ArrayList<Review>> call = retrofitInterface.executeGetReviews(recipeId);

        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()) {
                    Message message = new Message("Review Loaded", response.code());
                    ArrayList<Review> reviews = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                callback.failure(t);
            }
        });

    }

    public void addReview(Review review, String recipeId, CustomCallback callback){
        //Review recipe = new Review(review, recipeId);
        Call<Review> call = retrofitInterface.executeAddReview(review);

        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if(response.isSuccessful()){
                    Log.d("ApiHelper - addReview", "Review added");
                    Message message = new Message("Review added", response.code());
                    callback.success(message);
                } else {
                    Log.d("ApiHelper - addReview", response.errorBody().toString());
                    Message message = new Message("Review not added", response.code());
                    callback.error(message);
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("ApiHelper - addReview", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void editReview(Review review, String recipeId){
        Recipe recipe = new Recipe(review, recipeId);
        Call<Recipe> call = retrofitInterface.executeEditReview(recipe);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    Log.d("ApiHelper - editReview", "Review edited");
                } else {
                    Log.d("ApiHelper - editReview", "Review not edited");
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - editReview", "Something went wrong");
                t.printStackTrace();
            }
        });

    }

    public void deleteReview(String recipeId, String reviewId, String authToken, CustomCallback callback){
        Call<Recipe> call = retrofitInterface.executeDeleteReview(authToken, recipeId, reviewId);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    Log.d("ApiHelper - delReview", "Review deleted");
                } else {
                    Log.d("ApiHelper - delReview", "Review not deleted");
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - editReview", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

}

