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

            String newPassword = etNewPassword.getText().toString();
            String oldPassword = etOldPassword.getText().toString();

            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String authToken = sp.getString(JSON_KEY_TOKEN, "");

                        helper.changePassword(oldPassword, newPassword, authToken, new CustomCallback() {
                            @Override
                            public void success(Message message) {

                            }

                            @Override
                            public void failure(Throwable t) {

                            }
                        });
                    }
                });
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