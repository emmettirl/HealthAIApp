package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SubscriptionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_page);

        //region Nav Bar
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button FitnessPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        //endregion

    }
}
