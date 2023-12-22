package com.example.healthaiapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;

public class FitnessPlanPage extends AppCompatActivity {
    private User loggedInUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_plans);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }
    }
}
