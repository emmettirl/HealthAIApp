package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class register extends AppCompatActivity {

    Button submitButton;
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

        submitButton = (Button)findViewById(R.id.buttonSubmitRegister);
        username = (EditText) findViewById(R.id.textInputEditTextUsernameRegister);
        password = (EditText) findViewById(R.id.textInputEditTextPasswordRegister);
        email = (EditText) findViewById(R.id.textInputEditTextEmailRegister);
        fname = (EditText) findViewById(R.id.textInputEditTextFNameRegister);
        lname = (EditText) findViewById(R.id.textInputEditTextLNameRegister);

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

                        uvm = new UserViewModel();
                        uvm.addUser(user);
                    }
                }
        );
    }
}