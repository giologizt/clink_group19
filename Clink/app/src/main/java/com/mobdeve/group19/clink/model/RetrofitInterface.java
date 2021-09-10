package com.mobdeve.group19.clink.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.RequiresOptIn;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.*;

public interface RetrofitInterface {

    @POST("/login")
    Call<Login> executeLogin(@Body Login login);

    @GET("/logout")
    Call<Login> executeLogout(@Header("Authorization") String accessToken);

    @POST("/register")
    Call<Register> executeRegister(@Body Register register);

    @GET("/profile")
    Call<Profile> executeGetProfile(@Header("Authorization") String accessToken);

    @PUT("/profile")
    Call<Profile> executeEditProfile(@Header("Authorization") String accessToken, @Body Profile profile);

    @PUT("/changePassword")
    Call<Profile> executeChangePassword(@Header("Authorization") String accessToken, @Body Profile profile);

    @Multipart
    @POST("/postRecipe")
    Call<Recipe> executePostRecipe(@Header("Authorization") String accessToken, @Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);
    //@Part("name") RequestBody name,
    //@Part("prepTime") RequestBody prepTime,
    //@Part("author") RequestBody author,
    //@Part("steps") RequestBody steps,
    //@Part("reviews") RequestBody reviews,
    //@Part("ingredients") RequestBody ingredients);

    @GET("/getRecipes")
    Call<ArrayList<Recipe>> executeGetRecipes();

    @GET("/getRecipe/{id}")
    Call<Recipe> executeGetRecipe(@Path("id") String id);

    @PUT("/updateRecipe")
    Call<Recipe> executeUpdateRecipe(@Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);

    @GET("/searchRecipe/{search}")
    Call<ArrayList<Recipe>> executeSearchRecipe(@Path("search") String search);

    @DELETE("/deleteRecipe/{id}")
    Call<Recipe> executeDeleteRecipe(@Path("id") String id);

    @POST("/addReview")
    Call<Review> executeAddReview(@Body Review review);

    @PUT("/editReview")
    Call<Recipe> executeEditReview(@Body Recipe recipe);

    @DELETE("/deleteReview")
    Call<Recipe> executeDeleteReview(@Body Recipe recipe);

}