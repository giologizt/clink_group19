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

    private static final String JSON_KEY_TOKEN = "JSON_KEY_TOKEN";

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

        this.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newPassword = etNewPassword.getText().toString();
                String oldPassword = etOldPassword.getText().toString();
                if(newPassword.equals("") || oldPassword.equals(""))
                    Toast.makeText(getApplicationContext(), "Error: You left a field blank.", Toast.LENGTH_SHORT).show();
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            String authToken = sp.getString(JSON_KEY_TOKEN, "");
                            helper.changePassword(newPassword, oldPassword, authToken, new CustomCallback() {
                                @Override
                                public void success(Message message) {
                                    Toast.makeText(getApplicationContext(), "Successfully changed your password", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void error(Message message) {
                                    if(message.getCode() == 401) {
                                        Toast.makeText(getApplicationContext(), "Error: Old password does not match.", Toast.LENGTH_SHORT).show();
                                    } else if(message.getCode() == 404) {
                                        Toast.makeText(getApplicationContext(), "Error: User not found.", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void failure(Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

}