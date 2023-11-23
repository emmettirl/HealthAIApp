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

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        Button paymentPageButton = findViewById(R.id.SubscriptionPaymentButton);

        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, FitnessPage.class);
            startActivity(intent);
        });

        paymentPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, PaymentPage.class);
            startActivity(intent);
        });
        //endregion

    }
}
