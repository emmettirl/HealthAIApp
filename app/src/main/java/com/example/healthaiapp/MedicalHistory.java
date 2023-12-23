package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class MedicalHistory extends AppCompatActivity {


    User user;
    UserViewModel uvm;

    Button submitButton;
    Button backButton;
    EditText history;
    TextView updatedStatus;
    TextView displayHistory;

    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }

        //region Nav Buttons
        ImageButton userProfileButton = findViewById(R.id.userProfileNavButton);
        ImageButton fitnessPageButton = findViewById(R.id.FitnessNavButton);
        ImageButton homePageButton = findViewById(R.id.homeNavButton);

        fitnessPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(MedicalHistory.this, FitnessPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        homePageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(MedicalHistory.this, LandingPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        userProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(MedicalHistory.this, UserProfilePage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        //endregion


        submitButton = (Button) findViewById(R.id.buttonSubmit);
        backButton = (Button) findViewById(R.id.buttonBack);

        history = (EditText) findViewById(R.id.textInputEditTextHistory);

        updatedStatus = (TextView) findViewById(R.id.updatedStatus);
        displayHistory = (TextView)  findViewById(R.id.TextViewDisplayHistory);

        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");
        }

        displayHistory.setText(user.getMedicalDetails().getMedicalConditions().toString());


        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.getMedicalDetails().getMedicalConditions().add(history.getText().toString());

                        uvm.updateUser(user);
                        updatedStatus.setText("User Updated");
                        history.setText("");

                        displayHistory.setText(user.getMedicalDetails().getMedicalConditions().toString());


                    }
                }
        );

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MedicalHistory.this, LandingPage.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}