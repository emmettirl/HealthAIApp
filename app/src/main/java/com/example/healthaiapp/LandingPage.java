package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.healthaiapp.data.User;
import android.view.View;

public class LandingPage extends AppCompatActivity {
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");

            assert loggedInUser != null;
            String name = loggedInUser.getUsername();

            TextView welcomeText = findViewById(R.id.LandingPageWelcomeText);
            String greeting = getResources().getString(R.string.landing_greeting, name);
            welcomeText.setText(greeting);
        }

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button fitnessPageButton = findViewById(R.id.FitnessNavButton);

        fitnessPageButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("myDebug", "onClick: ");
                    Log.d("TAG", loggedInUser.getUsername());

                    if (loggedInUser != null) {
                        Intent intent = new Intent(LandingPage.this, FitnessPage.class);
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
                        Intent intent = new Intent(LandingPage.this, UserProfilePage.class);
                        intent.putExtra("loggedInUser", loggedInUser);
                        Log.d("mydebug", loggedInUser.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
            }
        );

        //endregion

        //region Quick Access Buttons
        ImageButton healthAIButtonPLACEHOLDER = findViewById(R.id.fitnessOverviewButton);
        ImageButton ratingButton = findViewById(R.id.RatingQuickAccessImageButton);
        ImageButton subscriptionButton = findViewById(R.id.subscriptionQuickAccessImageButton);
        ImageButton supportButton = findViewById(R.id.supportQuickAccessImageButton);

        ratingButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, RatingPage.class);
            intent.putExtra("loggedInUser", loggedInUser);
            startActivity(intent);
        });

        subscriptionButton.setOnClickListener(view -> {
            Intent intent = new Intent(LandingPage.this, SubscriptionPage.class);
            intent.putExtra("loggedInUser", loggedInUser);
            startActivity(intent);
        });

        supportButton.setOnClickListener(view -> {
            if (loggedInUser != null) {
                Intent intent = new Intent(LandingPage.this, ContactPage.class);
                intent.putExtra("loggedInUser", loggedInUser);
                Log.d("mydebug", loggedInUser.getUsername());
                startActivity(intent);
                finish();
            }
        });

        //endregion

    }
}