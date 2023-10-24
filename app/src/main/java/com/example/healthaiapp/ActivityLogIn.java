package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class ActivityLogIn extends AppCompatActivity {

    Button submitButton;
    Button loginToRegister;
    EditText username;
    EditText password;
    String usernameValue;
    String passwordValue;
    User user;
    UserViewModel uvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        submitButton = findViewById(R.id.buttonSubmitLogin);
        loginToRegister = findViewById(R.id.loginToRegister);
        username = findViewById(R.id.textInputEditTextUsernameLogin);
        password = findViewById(R.id.textInputEditTextPasswordLogin);

        uvm = new UserViewModel();



        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("registeredUser")) {
            User registeredUser = (User) thisIntent.getSerializableExtra("registeredUser");

            username.setText(registeredUser.getUsername());
            password.setText(registeredUser.getPassword());
        }


        loginToRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intentStart = new Intent(ActivityLogIn.this, ActivityRegister.class);
               startActivity(intentStart);
           }
       });


        submitButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("myDebug", "onClick: ");

                    usernameValue = username.getText().toString();
                    passwordValue = password.getText().toString();

                    if (!usernameValue.isEmpty() && !passwordValue.isEmpty()) {

                        Log.d("myDebug", usernameValue + " " + passwordValue);

                        user =  new User(usernameValue, passwordValue);

                        uvm.logIn(user);
                    } else {
                        Log.d("myDebug", "Please enter both username and password: ");
                    }
                }
            }
        );

        uvm.getLoginSuccess().observe(this, loggedInUser -> {
            if (loggedInUser != null) {
                // Login is successful, navigate to LoginActivity
                Intent intent = new Intent(ActivityLogIn.this, ActivityLandingPage.class);
                intent.putExtra("loggedInUser", loggedInUser);
                Log.d("mydebug", loggedInUser.getUsername());
                startActivity(intent);
                finish();
            }

        });
    }
}