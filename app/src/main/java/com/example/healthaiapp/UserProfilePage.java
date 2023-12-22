package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;

public class UserProfilePage extends AppCompatActivity {

    private User loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");

            assert loggedInUser != null;
            String name = loggedInUser.getUsername();

            TextView userPageNameText = findViewById(R.id.userPageNameText);
            String greeting = getResources().getString(R.string.user_name_header, name);
            userPageNameText.setText(greeting);
        }


        //region Nav Buttons
        Button editUserProfileButton = findViewById(R.id.editUserDetailsButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.homeNavButton);
        Button fitnessPageButton = findViewById(R.id.FitnessNavButton);

        fitnessPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(UserProfilePage.this, FitnessPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        editUserProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(UserProfilePage.this, AdditionalInfo.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        //endregion
    }
}