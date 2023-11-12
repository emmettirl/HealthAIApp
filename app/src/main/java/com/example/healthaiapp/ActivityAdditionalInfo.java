package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.healthaiapp.data.User;

public class ActivityAdditionalInfo extends AppCompatActivity {

    Button buttonToUserContactInfo;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        this.buttonToUserContactInfo = findViewById(R.id.ButtonContactInfo);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");
        }

        buttonToUserContactInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", user.getUsername());

                        if (user != null) {
                            Intent intent = new Intent(ActivityAdditionalInfo.this, ActivityContactInfo.class);
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