package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthaiapp.data.User;

public class LandingPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");
            assert loggedInUser != null;
            String name = loggedInUser.getUsername();

            TextView welcomeText = findViewById(R.id.LandingPageWelcomeText);
            String greeting = getResources().getString(R.string.landing_greeting, name);
            welcomeText.setText(greeting);
        }

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button FitnessPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        //endregion

        //region Quick Access Buttons
        ImageButton healthAIButtonPLACEHOLDER = findViewById(R.id.healthAIQuickAccessImageButton);
        ImageButton ratingButton = findViewById(R.id.RatingQuickAccessImageButton);
        ImageButton subscriptionButton = findViewById(R.id.subscriptionQuickAccessImageButton);
        ImageButton supportButton = findViewById(R.id.supportQuickAccessImageButton);

        ratingButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, RatingPage.class);
            startActivity(intent);
        });

        subscriptionButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, SubscriptionPage.class);
            startActivity(intent);
        });

        supportButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, ContactPage.class);
            startActivity(intent);
        });

        //endregion

    }
}