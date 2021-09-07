package com.mobdeve.group19.clink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignup;
    private TextView etFullname, etEmail, etPassword, etUsername, etBirthDay, etBirthMonth, etBirthYear;

    private String fullname, email, password, username;
    private int day, month, year;

    ApiHelper helper;

    ExecutorService executorService;

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        helper = new ApiHelper();
        executorService = Executors.newSingleThreadExecutor();

        Intent intent = new Intent (SignupActivity.this, LoginActivity.class);

        btnSignup = findViewById(R.id.signupBtn);

        etFullname = findViewById(R.id.et_signupFullname);
        etEmail = findViewById(R.id.et_signupEmail);
        etPassword = findViewById(R.id.et_signupPassword);
        etUsername = findViewById(R.id.et_signupUsername);

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
                username = etUsername.getText().toString();

                if(fullname.equals("") || password.equals("") || email.equals("") || username.equals("") || etBirthDay.getText().toString().equals("")
                    || etBirthMonth.getText().toString().equals("") || etBirthYear.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Error: Please fill up all text fields.", Toast.LENGTH_SHORT).show();
                } else {

                    day = Integer.parseInt(etBirthDay.getText().toString());
                    month = Integer.parseInt(etBirthMonth.getText().toString());
                    year = Integer.parseInt(etBirthYear.getText().toString());

                    Integer legalyear = (year + 18);
                    Date now = Calendar.getInstance().getTime();

                    String birthday =  etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + etBirthYear.getText().toString();
                    String legalDate =  etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + legalyear.toString();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    SimpleDateFormat todayFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    Date today = null;
                    Date legal = null;

                    Log.d("MainActivity", now.toString());

                    try {
                        //today = dateFormat.parse(now.toString());
                        legal = dateFormat.parse(legalDate);

                        if(legal.compareTo(now) > 0){
                            Toast.makeText(getApplicationContext(), "Error: You're not legally allowed to use this app.", Toast.LENGTH_SHORT).show();
                        } else {
                            executorService.execute(new Runnable(){
                                @Override
                                public void run() {
                                    helper.register(username, fullname, email, birthday, password, new CustomCallback() {
                                        @Override
                                        public void success(Message message) {
                                            if(message.getCode() == 200){
                                                Launcher.launch(intent);
                                            }
                                        }

                                        @Override
                                        public void error(Message message) {
                                            if(message.getCode() == 400){
                                                Toast.makeText(getApplicationContext(), "Error: Something went wrong", Toast.LENGTH_SHORT).show();
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
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}