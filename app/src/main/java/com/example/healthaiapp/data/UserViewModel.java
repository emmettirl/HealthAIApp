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
    private static final String TAG = "UserViewModel";


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
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
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
                // Handle errors TO DO
            }
        });

    }

    private MutableLiveData<String> gpUserPhoneNumber = new MutableLiveData<>();

    public LiveData<String> getGPUserPhoneNumber() {
        return gpUserPhoneNumber;
    }

    public void getGPUserPhoneNumberById(String userId) {
        DatabaseReference dbGPUsers = database.getReference("GPusers");

        dbGPUsers.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GPUser gpUser = dataSnapshot.getValue(GPUser.class);
                    if (gpUser != null) {
                        gpUserPhoneNumber.postValue(gpUser.getPhonenumber());
                        Log.d(TAG, "onDataChange: GPUser phone number is " + gpUser.getPhonenumber());
                    } else {
                        Log.d("MyDebug", "GPUser data is null");
                    }
                } else {
                    Log.d("MyDebug", "GPUser with ID " + userId + " does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MyDebug", "Error fetching GPUser phone number");
            }
        });
    }


    private MutableLiveData<String> gpUserIdByEmail = new MutableLiveData<>();

    public LiveData<String> getGPUserIdByEmail() {
        return gpUserIdByEmail;
    }

    public void fetchGPUserIdByEmail(String email) {
        DatabaseReference dbGPUsers = database.getReference("GPusers");

        Query query = dbGPUsers.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot gpUserSnapshot : dataSnapshot.getChildren()) {
                        gpUserIdByEmail.postValue(gpUserSnapshot.getKey());
                        break;
                    }
                } else {
                    Log.d("MyDebug", "No GPUser found with the email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MyDebug", "Error fetching GPUser ID by email");
            }
        });
    }

}
