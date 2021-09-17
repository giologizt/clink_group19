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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfileActivity extends AppCompatActivity {

    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    private Button btnEdit;
    private TextView etFullname, etEmail, etBirthDay, etBirthMonth, etBirthYear;

    private String fullname, email, birthdate;
    private int day, month, year;

    private ApiHelper helper;
    private SharedPreferences sp;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.executorService = Executors.newSingleThreadExecutor();

        this.helper = new ApiHelper();

        etFullname = findViewById(R.id.et_signupFullname);
        etEmail = findViewById(R.id.et_signupEmail);

        etBirthDay = findViewById(R.id.et_signupDay);
        etBirthMonth = findViewById(R.id.et_signupMonth);
        etBirthYear = findViewById(R.id.et_signupYear);

        this.Update();
    }

    private ActivityResultLauncher Launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    //function to edit the profile of the user
    private void Update() {
        this.btnEdit = findViewById(R.id.updateprofileBtn);
        this.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditProfileActivity.this, ProfileActivity.class);

                fullname = etFullname.getText().toString();
                email = etEmail.getText().toString();
                birthdate = "";
                Integer error = 0;

                //check whether there is at least one text field that is not empty
                if (fullname.equals("") && email.equals("") && etBirthDay.getText().toString().equals("")
                        && etBirthMonth.getText().toString().equals("") && etBirthYear.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Error: Please fill up at least one text field.", Toast.LENGTH_SHORT).show();
                } else {
                    //check to ensure that all text fields about the birthdate is complete
                    if(!(etBirthDay.getText().toString().equals("") || etBirthMonth.getText().toString().equals("") || etBirthYear.getText().toString().equals(""))) {
                        day = Integer.parseInt(etBirthDay.getText().toString());
                        month = Integer.parseInt(etBirthMonth.getText().toString());
                        year = Integer.parseInt(etBirthYear.getText().toString());
                        //check to ensure the given date is valid
                        if (year < 0 || month < 1 || month > 12 || day < 1 || ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) &&
                                day > 31) || ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) || (month == 2 && day > 29)) {
                            Toast.makeText(getApplicationContext(), "Error: Please input a valid date.", Toast.LENGTH_SHORT).show();
                            error = 1;
                        } else {
                            birthdate = etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + etBirthYear.getText().toString();
                            Integer legalyear = (year + 18);
                            Date now = Calendar.getInstance().getTime();

                            String legalDate = etBirthMonth.getText().toString() + "/" + etBirthDay.getText().toString() + "/" + legalyear.toString();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            SimpleDateFormat todayFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                            Date birth = null;
                            Date legal = null;

                            try {
                                legal = dateFormat.parse(legalDate);
                                birth = dateFormat.parse(birthdate);
                                //check whether date given is legal
                                if (legal.compareTo(now) > 0) {
                                    Toast.makeText(getApplicationContext(), "Error: Please enter a legal birthdate.", Toast.LENGTH_SHORT).show();
                                    error = 1;
                                //check whether date given is valid
                                } else if(birth.compareTo(now) > 0) {
                                    Toast.makeText(getApplicationContext(), "Error: You entered an invalid birthdate.", Toast.LENGTH_SHORT).show();
                                    error = 1;
                                } else {
                                    error = 0;
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Incomplete date", Toast.LENGTH_SHORT);
                        error = 1;
                    }

                    if(error == 0) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                String authToken = sp.getString(JSON_TOKEN_KEY, "");
                                helper.editProfile(email, fullname, birthdate, authToken, new CustomCallback() {
                                    //Successfully edited profile
                                    @Override
                                    public void success(Message message) {
                                        Toast.makeText(getApplicationContext(), "Successfully changed your profile", Toast.LENGTH_SHORT).show();
                                        Launcher.launch(intent);
                                    }

                                    //Unsuccessfully edited profile
                                    @Override
                                    public void error(Message message) {
                                        if(message.getCode() == 404) {
                                            Toast.makeText(getApplicationContext(), "Error: An error occurred.", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }
}