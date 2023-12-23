package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class Register extends AppCompatActivity {

    ImageButton submitButton;
    ImageButton registerToLogin;
    EditText username;
    EditText password;
    EditText email;
    EditText fname;
    EditText lname;
    User user;
    UserViewModel uvm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submitButton = (ImageButton) findViewById(R.id.buttonSubmitRegister);
        registerToLogin = findViewById(R.id.registerToLogin);

        username = (EditText) findViewById(R.id.textInputEditTextUsernameRegister);
        password = (EditText) findViewById(R.id.textInputEditTextPasswordRegister);
        email = (EditText) findViewById(R.id.textInputEditTextEmailRegister);
        fname = (EditText) findViewById(R.id.textInputEditTextFNameRegister);
        lname = (EditText) findViewById(R.id.textInputEditTextLNameRegister);

        uvm = new UserViewModel();

        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStart = new Intent(Register.this, LogIn.class);
                startActivity(intentStart);
            }
        });

        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(
                                "myDebug",
                                username.getText().toString() + " "
                                        + password.getText().toString() + " "
                                        + email.getText().toString() + " "
                                        + fname.getText().toString() + " "
                                        + lname.getText().toString());


                        if (user == null) {
                            user = new User();
                        }
                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setfName(fname.getText().toString());
                        user.setlName(lname.getText().toString());
                        uvm.addUser(user);
                    }
                }
        );

        uvm.getRegistrationSuccess().observe(this, registeredUser -> {
            if (registeredUser != null) {
                // Registration is successful, navigate to LoginActivity
                Intent intent = new Intent(Register.this, LogIn.class);
                intent.putExtra("registeredUser", registeredUser);
                Log.d("mydebug", registeredUser.getUsername());
                startActivity(intent);
                finish(); // Close the RegisterActivity
            }

        });
    }

}