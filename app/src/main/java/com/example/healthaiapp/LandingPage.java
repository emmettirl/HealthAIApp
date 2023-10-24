package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button FitnessPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, UserProfilePage.class);
            startActivity(intent);
        });


    }
}