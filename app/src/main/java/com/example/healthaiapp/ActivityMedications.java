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

public class ActivityMedications extends AppCompatActivity {


    User user;
    UserViewModel uvm;

    Button submitButton;
    Button backButton;
    EditText medication;
    TextView updatedStatus;
    TextView displayMedications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);


        submitButton = (Button) findViewById(R.id.buttonSubmit);
        backButton = (Button) findViewById(R.id.buttonBack);

        medication = (EditText) findViewById(R.id.textInputEditTextMedication);

        updatedStatus = (TextView) findViewById(R.id.updatedStatus);
        displayMedications = (TextView)  findViewById(R.id.TextViewDisplayMedications);

        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");
            }

        displayMedications.setText(user.getMedicalDetails().getMedications().toString());


        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.getMedicalDetails().getMedications().add(medication.getText().toString());

                        uvm.updateUser(user);
                        updatedStatus.setText("User Updated");
                        medication.setText("");

                        displayMedications.setText(user.getMedicalDetails().getMedications().toString());


                    }
                }
        );

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityMedications.this, ActivityLandingPage.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}