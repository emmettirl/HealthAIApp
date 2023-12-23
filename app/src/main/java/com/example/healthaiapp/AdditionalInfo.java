package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class AdditionalInfo extends AppCompatActivity {

    ImageButton submitButton;
    ImageButton medicationsButton;
    ImageButton historyButton;
    ImageButton backButton;
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

        submitButton = findViewById(R.id.buttonSubmit);
        medicationsButton = findViewById(R.id.buttonMedications);
        historyButton = findViewById(R.id.buttonHistory);
        backButton = findViewById(R.id.buttonBack);


        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");

        }


        age = findViewById(R.id.textInputEditTextAge);
        height = findViewById(R.id.textInputEditTextHeight);
        weight = findViewById(R.id.textInputEditTextWeight);
        gender = findViewById(R.id.textInputEditTextGender);
        gpEmail = findViewById(R.id.textInputEditTextGpEmail);
        insuranceEmail = findViewById(R.id.textInputEditTextInsuranceEmail);
        insurancePhone = findViewById(R.id.textInputEditTextInsurancePhone);
        insuranceName = findViewById(R.id.textInputEditTextInsuranceName);

        updatedStatus = findViewById(R.id.updatedStatus);

        age.setText(user.getMedicalDetails().getAge());
        height.setText(user.getMedicalDetails().getHeight());
        weight.setText(user.getMedicalDetails().getWeight());
        gender.setText(user.getMedicalDetails().getGender());
        gpEmail.setText(user.getMedicalDetails().getGpEmail());
        insuranceEmail.setText((user.getMedicalDetails().getInsurance().getEmail()));
        insurancePhone.setText((user.getMedicalDetails().getInsurance().getPhone()));
        insuranceName.setText((user.getMedicalDetails().getInsurance().getName()));

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
                        Intent intent = new Intent(AdditionalInfo.this, UserProfilePage.class);
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
                        Intent intent = new Intent(AdditionalInfo.this, Medications.class);
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
                        Intent intent = new Intent(AdditionalInfo.this, MedicalHistory.class);
                        intent.putExtra("loggedInUser", user);
                        Log.d("mydebug", user.getUsername());
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}