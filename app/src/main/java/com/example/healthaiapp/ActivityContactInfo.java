package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healthaiapp.data.ContactInfo;
import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class ActivityContactInfo extends AppCompatActivity {

    Button submitButton;
    Button registerToLogin;
    EditText fname;
    EditText lname;
    EditText phone;
    EditText email;
    EditText address;
    User user;
    UserViewModel uvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        submitButton = (Button) findViewById(R.id.buttonSubmitContactInfo);

        fname = (EditText) findViewById(R.id.textInputEditTextFNameContactInfo);
        lname = (EditText) findViewById(R.id.textInputEditTextLNameContactInfo);
        phone = (EditText) findViewById(R.id.textInputEditTextPhoneContactInfo);
        email = (EditText) findViewById(R.id.textInputEditTextEmailContactInfo);
        address = (EditText) findViewById(R.id.textInputEditTextAddressContactInfo);

        uvm = new UserViewModel();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            this.user = (User) thisIntent.getSerializableExtra("loggedInUser");

            fname.setText(user.getfName());
            lname.setText(user.getlName());
            email.setText(user.getEmail());
        }

        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(
                                "myDebug",
                                fname.getText().toString() + " "
                                        + lname.getText().toString() + " "
                                        + phone.getText().toString() + " "
                                        + email.getText().toString() + " "
                                        + address.getText().toString());


                        if (user == null) {
                            user = new User();
                        }

                        ContactInfo contactInfo = new ContactInfo();

                        contactInfo.setFname(fname.getText().toString());
                        contactInfo.setLname(lname.getText().toString());
                        contactInfo.setEmail(email.getText().toString());
                        contactInfo.setPhone(phone.getText().toString());
                        contactInfo.setAddress(address.getText().toString());

                        user.getMedicalDetails().setUserContact(contactInfo);


                        uvm.updateUser(user);
                    }
                }
        );


    }
}