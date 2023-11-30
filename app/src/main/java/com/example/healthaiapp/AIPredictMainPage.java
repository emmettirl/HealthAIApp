package com.example.healthaiapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;

public class AIPredictMainPage extends AppCompatActivity {
    private User loggedInUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_predict_main);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }


    }
}
