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

    //The function that would call the server function for loging in
    @POST("/login")
    Call<Login> executeLogin(@Body Login login);

    //The function that would call the server function for logout out
    @GET("/logout")
    Call<Login> executeLogout(@Header("Authorization") String accessToken);

    //The function that would call the server function for registering
    @POST("/register")
    Call<Register> executeRegister(@Body Register register);

    //The function that would call the server function for getting a user profile
    @GET("/profile")
    Call<Profile> executeGetProfile(@Header("Authorization") String accessToken);

    //The function that would call the server function for editing a profile
    @PUT("/profile")
    Call<Profile> executeEditProfile(@Header("Authorization") String accessToken, @Body Profile profile);

    //The function that would call the server function for changing password
    @PUT("/changePassword")
    Call<Profile> executeChangePassword(@Header("Authorization") String accessToken, @Body Profile profile);

    //The function that would call the server function for posting a recipe
    @Multipart
    @POST("/postRecipe")
    Call<Recipe> executePostRecipe(@Header("Authorization") String accessToken, @Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);

    //The function that would call the server function for getting a username
    @GET("/username/{id}")
    Call<Profile> executeGetUsername(@Path("id") String id);

    //The function that would call the server function for getting all recipes
    @GET("/getRecipes")
    Call<ArrayList<Recipe>> executeGetRecipes();

    //The function that would call the server function for a specific recipe
    @GET("/getRecipe/{id}")
    Call<Recipe> executeGetRecipe(@Path("id") String id);

    //The function that would call the server function for updating a recipe
    @POST("/updateRecipe")
    Call<Recipe> executeUpdateRecipe(@Header("Authorization") String accessToken, @Body Recipe recipe);

    //The function that would call the server function for editing a recipe image
    @Multipart
    @POST("/updateImage")
    Call<Recipe> executeUpdateImage(@Header("Authorization") String accessToken, @Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);

    //The function that would call the server function for searching a recipe given a query
    @GET("/searchRecipe/{search}")
    Call<ArrayList<Recipe>> executeSearchRecipe(@Path("search") String search);

    //The function that would call the server function for deleting a recipe
    @DELETE("/deleteRecipe/{id}")
    Call<Recipe> executeDeleteRecipe(@Path("id") String id);

    //The function that would call the server function for getting all reviews of a recipe
    @GET("/getReviews/{recipeId}")
    Call<ArrayList<Review>> executeGetReviews(@Path("recipeId") String recipeId);

    //The function that would call the server function for adding a review
    @POST("/addReview")
    Call<Review> executeAddReview(@Body Review review);

    //The function that would call the server function for editing a review
    @PUT("/editReview")
    Call<Review> executeEditReview(@Body Review review);

    //The function that would call the server function for deleting a review
    @DELETE("/deleteReview/{recipeId}/{reviewId}")
    Call<Recipe> executeDeleteReview(@Header("Authorization") String authToken, @Path("recipeId") String recipeId, @Path("reviewId") String reivewId);

}