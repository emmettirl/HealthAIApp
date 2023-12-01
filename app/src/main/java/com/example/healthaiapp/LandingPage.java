package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthaiapp.data.StripeModel;
import com.example.healthaiapp.data.User;
import android.view.View;

public class LandingPage extends AppCompatActivity {
    private User loggedInUser;
    StripeModel sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        sm = new StripeModel();




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
        Button FitnessPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);


        userProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(LandingPage.this, AdditionalInfo.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

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
            Intent intent = new Intent(LandingPage.this, ContactPage.class);
            intent.putExtra("loggedInUser", loggedInUser);
            startActivity(intent);
        });




        sm.checkActiveSubscription(this.loggedInUser.getStripeID(), new StripeModel.SubscriptionCallback(){
            @Override
            public void onSubscriptionCheckCompleted(boolean hasActiveSubscription) {
                if (hasActiveSubscription) {
                    healthAIButtonPLACEHOLDER.setOnClickListener(view -> {
                        // placeholder for intent to go to AIPredict Activity
                    });;

                    AIPredictPLACEHOLDER.setOnClickListener(view -> {
                        // placeholder for intent to go to AIPredict Activity
                    });;
                }
                else {
                    healthAIButtonPLACEHOLDER.setOnClickListener(view -> {
                        Intent intent = new Intent(LandingPage.this, SubscriptionPage.class);
                        intent.putExtra("loggedInUser", loggedInUser);
                        startActivity(intent);
                    });

                    AIPredictPLACEHOLDER.setOnClickListener(view -> {
                        Intent intent = new Intent(LandingPage.this, SubscriptionPage.class);
                        intent.putExtra("loggedInUser", loggedInUser);
                        startActivity(intent);
                    });
                }

            }
        });

        //endregion

    }
}