package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthaiapp.data.User;

public class RatingPage  extends AppCompatActivity {
    private User loggedInUser;

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

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
            Log.d("RatingPage", "Intent has 'loggedInUser' extra: " + getIntent().hasExtra("loggedInUser"));
            if (loggedInUser != null) {
                Button leaveRatingButton = findViewById(R.id.leave_rating_button);
                leaveRatingButton.setOnClickListener(view -> {
                    Intent intent = new Intent(RatingPage.this, LeaveRatingPage.class);
                    Log.d("RatingPage", "User: " + loggedInUser.getUsername());
                    intent.putExtra("loggedInUser", loggedInUser);
                    startActivity(intent);
                });
            } else {
                Log.e("RatingPage", "User is null");
            }
        } else {
            Log.e("RatingPage", "User is not present in intent");
        }
    }
}
