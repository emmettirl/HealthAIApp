package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewSuccess extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_success_page);

        Button userProfileButton = findViewById(R.id.returnHomeButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewSuccess.this, LandingPage.class);
            startActivity(intent);
        });
    }
}
