package com.example.healthaiapp.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.healthaiapp.data.Constants.NODE_USERS;

import android.util.Log;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {

    FirebaseDatabase database;
    DatabaseReference dbUsers;


    public UserViewModel() {
        database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        dbUsers = database.getReference(NODE_USERS);
    }

    private MutableLiveData<User> registeredUser = new MutableLiveData<>();
    public LiveData<User> getRegistrationSuccess() {
        return registeredUser;
    };

    private MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    public LiveData<User> getLoginSuccess() {
        return loggedInUser;
    };


    public void addUser(User newUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbUsers = database.getReference(NODE_USERS);


        Query usernameQuery = dbUsers.orderByChild("username").equalTo(newUser.username);
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("myDebug", "addUser: username already exists");
                } else {
                    dbUsers.child(newUser.getUsername()).setValue(newUser);
                    Log.d("MyDebug", "addUser: wrote to " + dbUsers.getRoot().toString() );

                    registeredUser.postValue(newUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MyDebug", "Something went wrong adding the user to the database");
            }
        });
    }

    public void updateUser(User updatedUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbUsers = database.getReference(NODE_USERS);

        Query usernameQuery = dbUsers.orderByChild("username").equalTo(updatedUser.getUsername());
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        dbUsers.child(userId).setValue(updatedUser);
                        Log.d("MyDebug", "updateUser: updated user with ID " + userId);

                    }
                } else {
                    Log.d("MyDebug", "updateUser: user not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MyDebug", "Something went wrong updating the user in the database");
            }
        });
    }


    public void logIn(User loginUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbUsers = database.getReference(NODE_USERS);

        Query usernameQuery = dbUsers.orderByChild("username").equalTo(loginUser.username);
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User with the given username exists
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Get the user object
                        User user = userSnapshot.getValue(User.class);

                        // Check if the password matches
                        if (user != null && user.getPassword().equals(loginUser.password)) {
                            Log.d("MyDebug", "Password matches, proceed with user authentication");
                            loggedInUser.postValue(user);
                        } else {
                            Log.d("MyDebug", "Password doesn't match");
                        }
                    }
                } else {
                    Log.d("MyDebug", "Login: User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });

    }


}
