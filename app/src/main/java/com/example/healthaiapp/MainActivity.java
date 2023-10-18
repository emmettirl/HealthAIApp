package com.example.healthaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //the following code is for Firebase testing purposes only
//        User user = new User("Test", "Test", "John", "Doe", "john.doe@test.com");
//        User user2 = new User("Test2", "Test2", "Jane", "Doe", "jane.doe@test.com");
//        UserViewModel userViewModel = new UserViewModel();
//        userViewModel.addUser(user);
//        userViewModel.addUser(user2);
//
//
//        userViewModel.readUser(user);
//        userViewModel.readUsername("Test");
//        //End test code

    }
}