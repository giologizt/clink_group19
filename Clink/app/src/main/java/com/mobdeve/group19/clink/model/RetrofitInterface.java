package com.mobdeve.group19.clink.model;

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

    @POST("/postRecipe")
    Call<Recipe> executePostRecipe(@Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);
                                   //@Part("name") RequestBody name,
                                   //@Part("prepTime") RequestBody prepTime,
                                   //@Part("author") RequestBody author,
                                   //@Part("steps") RequestBody steps,
                                   //@Part("reviews") RequestBody reviews,
                                   //@Part("ingredients") RequestBody ingredients);

    @GET("/getRecipes")
    Call<Recipe> executeGetRecipes();

    @GET("/getRecipe")
    Call<Recipe> executeGetRecipe(@Body String id);

    @PUT("/updateRecipe")
    Call<Recipe> executeUpdateRecipe(@Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> partMap);

    @GET("/searchRecipe")
    Call<Recipe> executeSearchRecipe(@Body String searchQuery);

    @DELETE("/deleteRecipe")
    Call<Recipe> executeDeleteRecipe(@Body String id);

    @POST("/addReview")
    Call<Recipe> executeAddReview(@Body Recipe recipe);

    @PUT("/editReview")
    Call<Recipe> executeEditReview(@Body Recipe recipe);

    @DELETE("/deleteReview")
    Call<Recipe> executeDeleteReview(@Body Recipe recipe);

 }
