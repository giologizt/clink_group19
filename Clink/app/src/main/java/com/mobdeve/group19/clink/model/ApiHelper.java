package com.mobdeve.group19.clink.model;

import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ApiHelper {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static String BASE_URL = "http://localhost:3000";
    private static String authToken;

    Message message;
    String filePath = "";
    //ArrayList<Recipe> recipes = new ArrayList<>();


    public ApiHelper() {

        CookieHandler cookieHandler = new CookieManager();

        //OkHttpClient.Builder client = new OkHttpClient().Builder();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);

    }

    public String login(String username, String password) {
        Login loginInformation = new Login(username, password);
        Call<Login> call = retrofitInterface.executeLogin(loginInformation);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - Login", response.body().getMessage());
                    Log.d("ApiHelper - Login", response.body().getAccessToken());
                    //System.out.println(response.body().getMessage());
                    //System.out.println(response.body().getAccessToken());
                    authToken = "bearer " + response.body().getAccessToken();
                    message = new Message("Login Successful", response.code(), authToken);
                } else {
                    Log.d("ApiHelper - Login", "Invalid Credentials Given");
                    message = new Message("Login Failed", response.code());
                    //System.out.println("Invalid Credentials");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("ApiHelper - Login", "Something went wrong");
                t.printStackTrace();
            }
        });

        return authToken;
    }

    public void register(String username,
                         String fullname,
                         String email,
                         String birthday,
                         String password) {
        Register registerInformation = new Register(username, fullname, email, birthday, password);
        Call<Register> call = retrofitInterface.executeRegister(registerInformation);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - Register", response.body().getMessage());
                    Log.d("ApiHelper - Register", response.body().getUsername());
                    //System.out.println(response.body().getMessage());
                    //System.out.println(response.body().getUsername());
                } else
                    Log.d("ApiHelper - Register", response.errorBody().toString());
                    //System.out.println(response.errorBody());
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.d("ApiHelper - Register", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void getProfile(String id) {
        Call<Profile> call = retrofitInterface.executeGetProfile(authToken);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - getProfile", response.body().getEmail());
                    Log.d("ApiHelper - getProfile", response.body().getUsername());
                    Log.d("ApiHelper - getProfile", response.body().getFullName());
                    Log.d("ApiHelper - getProfile", response.body().getBirthday());
                    //System.out.println(response.body().getUsername());
                    //System.out.println(response.body().getEmail());
                    //System.out.println(response.body().getFullName());
                    //System.out.println(response.body().getBirthday());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - getProfile", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void editProfile(String email, String fullName, String birthday) {
        Profile profileInformation = new Profile(email, fullName, birthday);
        Call<Profile> call = retrofitInterface.executeEditProfile(authToken, profileInformation);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - editProfile", "Profile edited");
                    Log.d("ApiHelper - editProfile", response.body().getEmail());
                    Log.d("ApiHelper - editProfile", response.body().getUsername());
                    Log.d("ApiHelper - editProfile", response.body().getFullName());
                    Log.d("ApiHelper - editProfile", response.body().getBirthday());
                    //System.out.println("Profile Edited.");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - editProfile", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void changePassword(String newpassword, String oldpassword) {
        Profile profileInformation = new Profile(newpassword, oldpassword);
        Call<Profile> call = retrofitInterface.executeChangePassword(authToken, profileInformation);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - changePass", "Password changed");
                    Log.d("ApiHelper - changePass", response.body().getPassword());
                    //System.out.println("Password Changed.");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("ApiHelper - changePass", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void postRecipe(ArrayList<String> recipesteps, ArrayList<Ingredients> recipeingredients, String recipename,
                           Integer recipeprepTime, String recipeauthor){  //Uri imageUri

        File file = new File(filePath);
        //File file = new File(imageUri.getPath());
        //String fileName = file.getName();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("recipe-image", file.getName(), requestBody);

        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), recipename);
        RequestBody prepTime = RequestBody.create(MediaType.parse("multipart/form-data"), recipeprepTime.toString());
        RequestBody author = RequestBody.create(MediaType.parse("multipart/form-data"), recipeauthor);
        //RequestBody steps = RequestBody.create(MediaType.parse(), recipesteps);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("prepTime", prepTime);
        map.put("author", author);

        for(int i = 0; i < recipesteps.size(); i++) {
            RequestBody steps = RequestBody.create(MediaType.parse("multipart/form-data"), recipesteps.get(i));
            map.put("steps[" + i + "]", steps);
        }

        for(int i = 0; i < recipeingredients.size(); i++) {
            RequestBody quantity = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getQuantity().toString());
            RequestBody ingredientName = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getIngredient());
            map.put("ingredients[" + i + "][quantity]", quantity);
            map.put("ingredients[" + i + "][ingredientName]", ingredientName);
        }

        Call<Recipe> call = retrofitInterface.executePostRecipe(filePart, map);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - postRecipe", "Recipe added");
                    Log.d("ApiHelper - postRecipe", response.body().getAuthor());
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

    public void getRecipes(){
        Gson gson = new Gson();
        Call<Recipe> call = retrofitInterface.executeGetRecipes();

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    String result = response.body().toString();

                    Type dataType = new TypeToken<Collection<Recipe>>() {}.getType();
                    Collection<Recipe> enums = gson.fromJson(result, dataType);
                    Recipe[] recipe = enums.toArray(new Recipe[enums.size()]);
                    //recipe[0].getAuthor()
                    //add callback method for recipe arraylist
                    Log.d("ApiHelper - getRecipes", "Recipes not collected");
                    Log.d("ApiHelper - getRecipes", recipe[0].getAuthor());
                    Log.d("ApiHelper - getRecipes", recipe[0].getIngredients().toString());
                    Log.d("ApiHelper - getRecipes", recipe[0].getName());
                } else {
                    Log.d("ApiHelper - getRecipes", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - getRecipes", "Something went wrong");
                t.printStackTrace();
            }
        });

    }

    public void getRecipe(String id) {
        Call<Recipe> call = retrofitInterface.executeGetRecipe(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - getRecipe", response.body().getName());
                    Log.d("ApiHelper - getRecipe", response.body().getAuthor());
                    Log.d("ApiHelper - getRecipe", response.body().getPrepTime().toString());
                    Log.d("ApiHelper - getRecipe", response.body().getSteps().toString());
                    //System.out.println(response.body().getUsername());
                    //System.out.println(response.body().getEmail());
                    //System.out.println(response.body().getFullName());
                    //System.out.println(response.body().getBirthday());
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

    public void updateRecipe(ArrayList<String> recipesteps, ArrayList<Ingredients> recipeingredients, String recipename,
                             Integer recipeprepTime, String recipeauthor, String id) { //Uri imageUri

        File file = new File(filePath);
        //File file = new File(imageUri.getPath());
        //String fileName = file.getName();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("recipe-image", file.getName(), requestBody);

        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), recipename);
        RequestBody prepTime = RequestBody.create(MediaType.parse("multipart/form-data"), recipeprepTime.toString());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("prepTime", prepTime);

        for(int i = 0; i < recipesteps.size(); i++) {
            RequestBody steps = RequestBody.create(MediaType.parse("multipart/form-data"), recipesteps.get(i));
            map.put("steps[" + i + "]", steps);
        }

        for(int i = 0; i < recipeingredients.size(); i++) {
            RequestBody quantity = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getQuantity().toString());
            RequestBody ingredientName = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).getIngredient());
            map.put("ingredients[" + i + "][quantity]", quantity);
            map.put("ingredients[" + i + "][ingredientName]", ingredientName);
        }

        Call<Recipe> call = retrofitInterface.executeUpdateRecipe(filePart, map);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - upRecipe", "Recipe updated");
                    Log.d("ApiHelper - upRecipe", response.body().getAuthor());
                    Log.d("ApiHelper - upRecipe", response.body().getName());
                    Log.d("ApiHelper - upRecipe", response.body().getPrepTime().toString());
                    Log.d("ApiHelper - upRecipe", response.body().getSteps().toString());
                    Log.d("ApiHelper - upRecipe", response.body().getIngredients().toString());
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

    public void searchRecipe(String searchQuery){
        Gson gson = new Gson();
        Call<Recipe> call = retrofitInterface.executeSearchRecipe(searchQuery);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    String result = response.body().toString();

                    Type dataType = new TypeToken<Collection<Recipe>>() {}.getType();
                    Collection<Recipe> enums = gson.fromJson(result, dataType);
                    Recipe[] recipe = enums.toArray(new Recipe[enums.size()]);
                    //recipe[0].getAuthor()
                    //add callback method for recipe arraylist
                    Log.d("ApiHelper - search", "Recipes not collected");
                    Log.d("ApiHelper - search", recipe[0].getAuthor());
                    Log.d("ApiHelper - search", recipe[0].getIngredients().toString());
                    Log.d("ApiHelper - search", recipe[0].getName());
                } else {
                    Log.d("ApiHelper - search", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - search", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void deleteRecipe(String id){
        Call<Recipe> call = retrofitInterface.executeDeleteRecipe(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Log.d("ApiHelper - delRecipe", "Recipe deleted");
                   //Log.d("ApiHelper - delRecipe", response.body().getMessage());
                } else {
                    Log.d("ApiHelper - delRecipe", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("ApiHelper - delRecipe", "Something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void addReview(Review review, String recipeId){
        Recipe recipe = new Recipe(review, recipeId);
        Call<Recipe> call = retrofitInterface.executeAddReview(recipe);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    Log.d("ApiHelper - addReview", "Review added");
                } else {
                    Log.d("ApiHelper - addReview", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
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

    public void deleteReview(Review review, String recipeId){
        Recipe recipe = new Recipe(review, recipeId);
        Call<Recipe> call = retrofitInterface.executeDeleteReview(recipe);

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

