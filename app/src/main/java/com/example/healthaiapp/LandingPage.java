package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.healthaiapp.data.User;

public class LandingPage extends AppCompatActivity {

    Button buttonToUserProfile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        this.buttonToUserProfile = findViewById(R.id.button4);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");
        }


        buttonToUserProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", user.getUsername());

                        if (user != null) {
                            Intent intent = new Intent(LandingPage.this, AdditionalInfo.class);
                            intent.putExtra("loggedInUser", user);
                            Log.d("mydebug", user.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );
    }
}