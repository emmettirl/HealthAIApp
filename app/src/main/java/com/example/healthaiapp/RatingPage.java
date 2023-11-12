package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RatingPage  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_overview_page);

        //region Nav Bar
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button FitnessPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(RatingPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        //endregion

        Button leaveRatingButton = findViewById(R.id.leave_rating_button);

        leaveRatingButton.setOnClickListener(view -> {
            Intent intent = new Intent(RatingPage.this, LeaveRatingPage.class);
            startActivity(intent);
        });
    }
}
