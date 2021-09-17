package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordActivity extends AppCompatActivity {

    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    private Button btnUpdate;
    private TextView etOldPassword, etNewPassword;

    private ApiHelper helper;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        this.etNewPassword = findViewById(R.id.et_newpassword);
        this.etOldPassword = findViewById(R.id.et_oldpassword);

        this.btnUpdate = findViewById(R.id.btn_updatePassword);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        Intent intent = new Intent(PasswordActivity.this, ProfileActivity.class);

        // When the Update Password is Pressed
        this.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Convert Text to String
                String newPassword = etNewPassword.getText().toString();
                String oldPassword = etOldPassword.getText().toString();

                // Chceks if the new password or old password are empty fields.
                if(newPassword.equals("") || oldPassword.equals(""))
                    Toast.makeText(getApplicationContext(), "Error: You left a field blank.", Toast.LENGTH_SHORT).show();
                else {

                    // Runs the API Helper through a Thread.
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            String authToken = sp.getString(JSON_TOKEN_KEY, "");

                            // Calls the API Helper
                            helper.changePassword(newPassword, oldPassword, authToken, new CustomCallback() {
                                @Override
                                public void success(Message message) {
                                    Toast.makeText(getApplicationContext(), "Successfully changed your password", Toast.LENGTH_SHORT).show();
                                    Launcher.launch(intent);
                                }

                                @Override
                                public void error(Message message) {
                                    // Shows error if the server returns an error code.
                                    if(message.getCode() == 401) {
                                        Toast.makeText(getApplicationContext(), "Error: Old password does not match.", Toast.LENGTH_SHORT).show();
                                    } else if(message.getCode() == 404) {
                                        Toast.makeText(getApplicationContext(), "Error: User not found.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                // Failure Callback
                                @Override
                                public void failure(Throwable t) {
                                    Toast.makeText(getApplicationContext(), "A server error occurred.", Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }
        });

    }
    // For the use of intent.
    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

}