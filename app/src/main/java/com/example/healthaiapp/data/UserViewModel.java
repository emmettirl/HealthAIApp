package com.example.healthaiapp.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.healthaiapp.data.Constants.NODE_USERS;

import android.util.Log;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {

    FirebaseDatabase database;
    DatabaseReference dbUsers;
    ArrayList<User> userArrayList;


    public UserViewModel(){
        database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        dbUsers = database.getReference(NODE_USERS);
        userArrayList = new ArrayList<User>();

         ValueEventListener valueEventListener =  (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    User user = childSnapshot.getValue(User.class);

                    if (userArrayList.isEmpty() ) {
                        userArrayList.add(user);
                    } else if (!userArrayList.contains((user))) {
                        userArrayList.add(user);
                    }
                }
                Log.d("myDebug", userArrayList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("myDebug", "loadPost:onCancelled", error.toException());
            }
        });

         dbUsers.addValueEventListener(valueEventListener);
    }

    public void addUser(User newUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthai-group-project-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbUsers = database.getReference(NODE_USERS);

        int userNameExists = 0;
        for (User listUser: userArrayList) {
            if (listUser.getUsername().equals(newUser.getUsername())){
                userNameExists = 1;
                Log.d("myDebug", "addUser: username already exists");
            }
        }
        if (userNameExists == 0) {
            dbUsers.child(newUser.getUsername()).setValue(newUser);
            Log.d("MyDebug", "addUser: wrote to " + dbUsers.getRoot().toString() );
        }


    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }
}
