package com.mobdeve.group19.clink.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.CookieHandler;
import java.net.CookieManager;

public class ApiHelper {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static String BASE_URL = "http://localhost:3000";
    private static String authToken;

    private Message message;

    public ApiHelper() {

        CookieHandler cookieHandler = new CookieManager();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);

    }

    public void login(String username, String password) {
        Login loginInformation = new Login(username, password);
        Call<Login> call = retrofitInterface.executeLogin(loginInformation);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().getMessage());
                    System.out.println(response.body().getAccessToken());
                    authToken = "bearer " + response.body().getAccessToken();

                } else {
                    System.out.println("Invalid Credentials");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                t.printStackTrace();
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
                    System.out.println(response.body().getMessage());
                    System.out.println(response.body().getUsername());
                } else
                    System.out.println(response.errorBody());
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

    public void getProfile(String id) {
        Call<Profile> call = retrofitInterface.executeGetProfile(authToken);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().getUsername());
                    System.out.println(response.body().getEmail());
                    System.out.println(response.body().getFullName());
                    System.out.println(response.body().getBirthday());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

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
                    System.out.println("Profile Edited.");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
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
                    System.out.println("Password Changed.");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

