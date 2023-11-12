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

public class ActivityAdditionalInfo extends AppCompatActivity {

    Button submitButton;
    Button medicationsButton;
    Button historyButton;
    Button backButton;
    User user;
    UserViewModel uvm;

    EditText age;
    EditText height;
    EditText weight;
    EditText gender;
    EditText gpEmail;
    EditText insuranceEmail;
    EditText insurancePhone;
    EditText insuranceName;
    TextView updatedStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        submitButton = (Button) findViewById(R.id.buttonSubmit);
        medicationsButton = (Button) findViewById(R.id.buttonMedications);
        historyButton = (Button) findViewById(R.id.buttonHistory);
        backButton = (Button) findViewById(R.id.buttonBack);


        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");

        }


        age = (EditText) findViewById(R.id.textInputEditTextAge);
        height = (EditText) findViewById(R.id.textInputEditTextHeight);
        weight = (EditText) findViewById(R.id.textInputEditTextWeight);
        gender = (EditText) findViewById(R.id.textInputEditTextGender);
        gpEmail = (EditText) findViewById(R.id.textInputEditTextGpEmail);
        insuranceEmail = (EditText) findViewById(R.id.textInputEditTextInsuranceEmail);
        insurancePhone = (EditText) findViewById(R.id.textInputEditTextInsurancePhone);
        insuranceName = (EditText) findViewById(R.id.textInputEditTextInsuranceName);

        updatedStatus = (TextView) findViewById(R.id.updatedStatus);

        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.getMedicalDetails().setAge(age.getText().toString());
                        user.getMedicalDetails().setHeight(height.getText().toString());
                        user.getMedicalDetails().setWeight(weight.getText().toString());
                        user.getMedicalDetails().setGender(gender.getText().toString());
                        user.getMedicalDetails().setGpEmail(gpEmail.getText().toString());

                        user.getMedicalDetails().getInsurance().setName(
                                insuranceName.getText().toString());
                        user.getMedicalDetails().getInsurance().setEmail(
                                insuranceEmail.getText().toString());
                        user.getMedicalDetails().getInsurance().setPhone(
                                insurancePhone.getText().toString());

                        uvm.updateUser(user);
                        updatedStatus.setText("User Updated");
                    }
                }
        );

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityAdditionalInfo.this, ActivityLandingPage.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );

        medicationsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityAdditionalInfo.this, ActivityMedications.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );

        historyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityAdditionalInfo.this, ActivityMedicalHistory.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );


    }
}