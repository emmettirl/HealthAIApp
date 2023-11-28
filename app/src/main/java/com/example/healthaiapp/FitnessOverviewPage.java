package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;

import java.text.BreakIterator;
import java.util.Locale;

public class FitnessOverviewPage extends AppCompatActivity {
    private User loggedInUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_overview_page);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }

        //region BMR
        TextView bmrValueText = findViewById(R.id.bmrValueText);
        String userAge = loggedInUser.getMedicalDetails().getAge();
        String userWeightWithUnit = loggedInUser.getMedicalDetails().getWeight();
        String userHeightWithUnit = loggedInUser.getMedicalDetails().getHeight();

        int age = Integer.parseInt(userAge);
        double weight = extractNumericValue(userWeightWithUnit);
        double height = extractNumericValue(userHeightWithUnit) / 100.0;

        // Harris-Benedict BMR Formula
        double bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        String bmrMessage = String.format(Locale.getDefault(), "%.2f", bmr);

        bmrValueText.setText(bmrMessage);
        //endregion

        //region Nav Buttons

        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(FitnessOverviewPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(FitnessOverviewPage.this, FitnessPage.class);
            startActivity(intent);
        });
        //endregion
    }
    private double extractNumericValue(String stringWithUnit) {
        return Double.parseDouble(stringWithUnit.replaceAll("[^\\d.]", ""));
    }
}
