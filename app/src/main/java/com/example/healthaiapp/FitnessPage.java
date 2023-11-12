package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.healthaiapp.data.User;
import androidx.appcompat.app.AppCompatActivity;

public class FitnessPage extends AppCompatActivity {
    private User loggedInUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_page);

        //region Nav Buttons

        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(FitnessPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        //endregion

        ImageButton bodyOverviewPage = findViewById(R.id.fitnessOverviewButton);

        bodyOverviewPage.setOnClickListener(view -> {
            Intent intent = new Intent(FitnessPage.this, FitnessOverviewPage.class);
            intent.putExtra("loggedInUser", loggedInUser);
            startActivity(intent);
        });
    }
}
