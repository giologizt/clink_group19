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

    private Button btnEdit;
    private TextView etFullname, etEmail, etBirthDay, etBirthMonth, etBirthYear;

    private String fullname, email, birthdate;
    private int day, month, year;

    private ApiHelper helper;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

                if (fullname.equals("") && email.equals("") && etBirthDay.getText().toString().equals("")
                        && etBirthMonth.getText().toString().equals("") && etBirthYear.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Error: Please fill up at least one text field.", Toast.LENGTH_SHORT).show();
                } else {

                    if(!(etBirthDay.getText().toString().equals("") && etBirthMonth.getText().toString().equals("") && etBirthYear.getText().toString().equals(""))) {
                        day = Integer.parseInt(etBirthDay.getText().toString());
                        month = Integer.parseInt(etBirthMonth.getText().toString());
                        year = Integer.parseInt(etBirthYear.getText().toString());
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
                            Date today = null;
                            Date legal = null;

                            try {
                                legal = dateFormat.parse(legalDate);
                                if (legal.compareTo(now) > 0) {
                                    Toast.makeText(getApplicationContext(), "Error: Please enter a legal birthdate.", Toast.LENGTH_SHORT).show();
                                    error = 1;
                                } else {
                                    error = 0;
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(error == 0) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                helper.editProfile(email, fullname, birthdate, new CustomCallback() {
                                    @Override
                                    public void success(Message message) {
                                        Toast.makeText(getApplicationContext(), "Successfully changed your profile", Toast.LENGTH_SHORT).show();
                                        Launcher.launch(intent);
                                    }

                                    @Override
                                    public void error(Message message) {
                                        if(message.getCode() == 404) {
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
            }
        });
    }
}