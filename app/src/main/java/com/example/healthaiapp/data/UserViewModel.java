package com.example.healthaiapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.healthaiapp.data.Constants.NODE_USERS;

import android.util.Log;

public class UserViewModel extends ViewModel {
    public void addUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbUsers = database.getReference(NODE_USERS);

        dbUsers.child(user.getUsername()).setValue(user);

        Log.d("MyDebug", "addUser: wrote to " + dbUsers.getRoot().toString() );
    }
}
