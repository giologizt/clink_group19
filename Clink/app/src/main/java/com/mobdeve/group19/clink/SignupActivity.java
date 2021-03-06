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

                //validate to ensure all text fields contains a value
                if (fullname.equals("") || password.equals("") || email.equals("") || username.equals("") || etBirthDay.getText().toString().equals("")
                        || etBirthMonth.getText().toString().equals("") || etBirthYear.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Error: Please fill up all text fields.", Toast.LENGTH_SHORT).show();
                } else {

                    day = Integer.parseInt(etBirthDay.getText().toString());
                    month = Integer.parseInt(etBirthMonth.getText().toString());
                    year = Integer.parseInt(etBirthYear.getText().toString());

                    //check whether the user inputted a valid date
                    if (year < 0 || month < 1 || month > 12 || day < 1 || ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) &&
                            day > 31) || ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) || (month == 2 && day > 29)) {
                        Toast.makeText(getApplicationContext(), "Error: Please input a valid date.", Toast.LENGTH_SHORT).show();
                    } else {

                        Integer legalyear = (year + 18);
                        Date now = Calendar.getInstance().getTime();

                        String birthday = etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + etBirthYear.getText().toString();
                        String legalDate = etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + legalyear.toString();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat todayFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        Date birth = null;
                        Date legal = null;

                        Log.d("MainActivity", now.toString());

                        try {
                            legal = dateFormat.parse(legalDate);
                            birth = dateFormat.parse(birthday);
                            //check whether the user is of legal age
                            if (legal.compareTo(now) > 0) {
                                Toast.makeText(getApplicationContext(), "Error: You're not legally allowed to use this app.", Toast.LENGTH_SHORT).show();
                            //check whether the birthdate is valid
                            } else if(birth.compareTo(now) > 0) {
                                Toast.makeText(getApplicationContext(), "Error: You entered an invalid birthdate.", Toast.LENGTH_SHORT).show();
                            } else {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        helper.register(username, fullname, email, birthday, password, new CustomCallback() {
                                            //successfully registered
                                            @Override
                                            public void success(Message message) {
                                                if (message.getCode() == 201) {
                                                    Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                                                    Launcher.launch(intent);
                                                }
                                            }

                                            //unsuccessfully registered
                                            @Override
                                            public void error(Message message) {
                                                if (message.getCode() == 400) {
                                                    Toast.makeText(getApplicationContext(), "Error: Something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            //error on the server side
                                            @Override
                                            public void failure(Throwable t) {
                                                Toast.makeText(getApplicationContext(), "A server error occurred.", Toast.LENGTH_SHORT).show();
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
            }
        });

    }
}