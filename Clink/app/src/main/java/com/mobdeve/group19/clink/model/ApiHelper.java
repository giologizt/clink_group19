package com.mobdeve.group19.clink.model;

import android.provider.MediaStore;
import android.util.Log;

import com.mobdeve.group19.clink.Recipe;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ApiHelper {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static String BASE_URL = "http://10.0.2.2:3000";
    private static String authToken;

    String filePath = "";
    ArrayList<Recipe> recipes = new ArrayList<>();


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
                    Log.d("ApiHelper - Login", response.body().getAccessToken());
                    //System.out.println(response.body().getMessage());
                    //System.out.println(response.body().getAccessToken());
                    authToken = "bearer " + response.body().getAccessToken();
                    Message message = new Message("Login Successful", response.code(), authToken);
                    callback.success(message);

                } else {
                    Log.d("ApiHelper - Login", "Invalid Credentials Given");
                    Message message = new Message("Login Failed", response.code());
                    callback.success(message);
                    //System.out.println("Invalid Credentials");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("ApiHelper - Login", "Something went wrong");
                callback.failure(t);
            }
        });

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
                    // Log.d("ApiHelper - changePass", response.body().getPassword());
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

    public void postRecipe(ArrayList<String> recipesteps, ArrayList<String> recipereviews, ArrayList<ArrayList<String>> recipeingredients,
                           String recipename, Integer recipeprepTime, String recipeauthor){

        File file = new File(filePath);


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
            RequestBody quantity = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).get(0));
            RequestBody ingredientName = RequestBody.create(MediaType.parse("multipart/form-data"), recipeingredients.get(i).get(1));
            map.put("ingredients[" + i + "][quantity]", quantity);
            map.put("ingredients[" + i + "][ingredientName]", ingredientName);
        }

        Call<Recipe> call = retrofitInterface.executePostRecipe(filePart, map);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Log.d("ApiHelper - postRecipe", "Recipe added");
                    // Log.d("ApiHelper - postRecipe", response.body().getAuthor());
                    Log.d("ApiHelper - postRecipe", response.body().getName());
                    // Log.d("ApiHelper - postRecipe", response.body().getPrepTime().toString());
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

}
