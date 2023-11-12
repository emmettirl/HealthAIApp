package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class MedicalHistory extends AppCompatActivity {


    User user;
    UserViewModel uvm;

    Button submitButton;
    Button backButton;
    EditText history;
    TextView updatedStatus;
    TextView displayHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);


        submitButton = (Button) findViewById(R.id.buttonSubmit);
        backButton = (Button) findViewById(R.id.buttonBack);

        history = (EditText) findViewById(R.id.textInputEditTextHistory);

        updatedStatus = (TextView) findViewById(R.id.updatedStatus);
        displayHistory = (TextView)  findViewById(R.id.TextViewDisplayHistory);

        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");
        }

        displayHistory.setText(user.getMedicalDetails().getMedicalConditions().toString());


        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.getMedicalDetails().getMedicalConditions().add(history.getText().toString());

                        uvm.updateUser(user);
                        updatedStatus.setText("User Updated");
                        history.setText("");

                        displayHistory.setText(user.getMedicalDetails().getMedicalConditions().toString());


                    }
                }
        );

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MedicalHistory.this, LandingPage.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}