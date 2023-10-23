package com.example.healthaiapp.data;

import androidx.annotation.NonNull;
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


        Query usernameQuery = dbUsers.orderByChild("username").equalTo(newUser.username);
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("myDebug", "addUser: username already exists");
                } else {
                    dbUsers.child(newUser.getUsername()).setValue(newUser);
                    Log.d("MyDebug", "addUser: wrote to " + dbUsers.getRoot().toString() );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }
}
