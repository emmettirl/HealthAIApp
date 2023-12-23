package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthaiapp.data.User;

public class ReviewSuccess extends AppCompatActivity {

    private User loggedInUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_success_page);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }

        Button userProfileButton = findViewById(R.id.returnHomeButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewSuccess.this, LandingPage.class);
            startActivity(intent);
        });
    }
}
