package com.example.healthaiapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;

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
        String userGender = loggedInUser.getMedicalDetails().getGender();


        int age = Integer.parseInt(userAge);
        double weight = extractNumericValue(userWeightWithUnit);
        double height = extractNumericValue(userHeightWithUnit);
        double bmr = 0.0;

        // Harris-Benedict BMR Formula
        if ("Male".equalsIgnoreCase(userGender)) {
            // BMR for men
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else if ("Female".equalsIgnoreCase(userGender)) {
            // BMR for women
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        String bmrMessage = String.format(Locale.getDefault(), "%.2f", bmr);

        bmrValueText.setText(bmrMessage);
        //endregion

        //region BMI
        TextView bmiValueText = findViewById(R.id.bmiValue);
        double bmi;
        double bmiHeight;
        bmiHeight = height / 100;

        bmi = weight / (bmiHeight * bmiHeight);
        String bmiValue = String.format(Locale.getDefault(), "%.2f", bmi);

        bmiValueText.setText(bmiValue);
        setTextDisplay(bmi);
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
        // Remove non-numeric characters and convert to a double
        try {
            String numericString = stringWithUnit.replaceAll("[^\\d.]", "");
            return Double.parseDouble(numericString);
        } catch (NumberFormatException e) {
            Log.e("BMI_CALCULATION", "Error extracting numeric value: " + stringWithUnit, e);
            return 0.0;
        }
    }

    private void setTextDisplay(double bmi) {
        // Assuming you have a TextView named bmiLevelDisplay
        TextView bmiLevelDisplay = findViewById(R.id.bmiLevelDisplay);
        TextView caloricIntakeRecommendation = findViewById(R.id.caloricIntakeRecommendation);

        // Define BMI ranges for categories
        double underweightThreshold = 18.5;
        double normalWeightThreshold = 24.9;
        double overweightThreshold = 29.9;

        // Check BMI level and set text and color accordingly
        if (bmi < underweightThreshold) {
            bmiLevelDisplay.setText("Underweight");
            bmiLevelDisplay.setTextColor(Color.BLUE);
            caloricIntakeRecommendation.setText(R.string.recommended_caloric_intake_underweight);
        } else if (bmi <= normalWeightThreshold) {
            bmiLevelDisplay.setText("Normal");
            bmiLevelDisplay.setTextColor(Color.GREEN);
            caloricIntakeRecommendation.setText(R.string.recommended_caloric_intake_normal);
        } else if (bmi <= overweightThreshold) {
            bmiLevelDisplay.setText("Overweight");
            bmiLevelDisplay.setTextColor(Color.rgb(255, 165, 0));
            caloricIntakeRecommendation.setText(R.string.recommended_caloric_intake_overweight);
        } else {
            bmiLevelDisplay.setText("Obese");
            bmiLevelDisplay.setTextColor(Color.RED);
            caloricIntakeRecommendation.setText(R.string.recommended_caloric_intake_overweight);
        }
    }
}
