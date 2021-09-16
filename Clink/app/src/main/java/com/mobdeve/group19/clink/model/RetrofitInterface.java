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

    @GET("/username/{id}")
    Call<Profile> executeGetUsername(@Path("id") String id);

    @GET("/getRecipes")
    Call<ArrayList<Recipe>> executeGetRecipes();

    @GET("/getRecipe/{id}")
    Call<Recipe> executeGetRecipe(@Path("id") String id);

    //@Multipart
    @POST("/updateRecipe")
    Call<Recipe> executeUpdateRecipe(@Header("Authorization") String accessToken, @Body Recipe recipe);
    //Call<Recipe> executeUpdateRecipe(@Header("Authorization") String accessToken, @PartMap Map<String, RequestBody> partMap);
    //@Part MultipartBody.Part filePart,

    @Multipart
    @POST("/updateImage")
    Call<Recipe> executeUpdateImage(@Header("Authorization") String accessToken, @Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);

    @GET("/searchRecipe/{search}")
    Call<ArrayList<Recipe>> executeSearchRecipe(@Path("search") String search);

    @DELETE("/deleteRecipe/{id}")
    Call<Recipe> executeDeleteRecipe(@Path("id") String id);

    @GET("/getReviews/{recipeId}")
    Call<ArrayList<Review>> executeGetReviews(@Path("recipeId") String recipeId);

    @POST("/addReview")
    Call<Review> executeAddReview(@Body Review review);

    @PUT("/editReview")
    Call<Review> executeEditReview(@Body Review review);

    @DELETE("/deleteReview/{recipeId}/{reviewId}")
    Call<Recipe> executeDeleteReview(@Header("Authorization") String authToken, @Path("recipeId") String recipeId, @Path("reviewId") String reivewId);

}