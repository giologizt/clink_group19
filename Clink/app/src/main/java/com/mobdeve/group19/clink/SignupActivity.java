package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignup;
    private TextView etFullname, etEmail, etPassword, etBirthDay, etBirthMonth, etBirthYear;

    private String fullname, email, password;
    private int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = findViewById(R.id.signupBtn);

        etFullname = findViewById(R.id.et_signupFullname);
        etEmail = findViewById(R.id.et_signupEmail);
        etPassword = findViewById(R.id.et_signupPassword);

        etBirthDay = findViewById(R.id.et_signupDay);
        etBirthMonth = findViewById(R.id.et_signupMonth);
        etBirthYear = findViewById(R.id.et_signupYear);

        this.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get text values
                fullname = etFullname.getText().toString();
                password = etPassword.getText().toString();
                email = etEmail.getText().toString();

                day = Integer.parseInt(etBirthDay.getText().toString());
                month = Integer.parseInt(etBirthMonth.getText().toString());
                year = Integer.parseInt(etBirthYear.getText().toString());

                if(fullname.equals("") || password.equals("") || email.equals("") || day == null ){

                } else {

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