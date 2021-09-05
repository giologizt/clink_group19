package com.mobdeve.group19.clink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    // JSON Token  Key
    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    // Login and Signup Button
    private Button btnLogin;
    private TextView tvSignup;

    // API Helper
    ApiHelper helper;

    // Login Form Fields
    private TextView loginUsername;
    private TextView loginPassword;

    // Process Management
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new ApiHelper();

        loginUsername = findViewById(R.id.pt_username);
        loginPassword = findViewById(R.id.ps_password);

        executorService = Executors.newSingleThreadExecutor();

        this.Login();
        this.Signup();
    }

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    private void Login() {
        // Shared Preferences (For storing JSON Web Tokens)
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        this.btnLogin = findViewById(R.id.loginBtn);

        // When the login button is pressed
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username;
                String password;

                username = loginUsername.getText().toString();
                password = loginPassword.getText().toString();

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Message message = helper.login(username, password);
                        Log.d("Login Frontend: ", message.getMessage());
                    }
                });

            }
        });
    }

    private void Signup() {
        this.tvSignup = findViewById(R.id.signupTv);

        // When the signup button is pressed
        this.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, SignupActivity.class);

                Launcher.launch(intent);
            }
        });
    }



}
