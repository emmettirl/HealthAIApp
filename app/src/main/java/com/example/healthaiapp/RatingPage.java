package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthaiapp.data.Review;
import com.example.healthaiapp.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RatingPage  extends AppCompatActivity {
    private User loggedInUser;
    private RecyclerView reviewsRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private DatabaseReference reviewsReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_overview_page);

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(RatingPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(RatingPage.this, FitnessPage.class);
            startActivity(intent);
        });
        //endregion

        //region Pass user info
        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
            Log.d("RatingPage", "Intent has 'loggedInUser' extra: " + getIntent().hasExtra("loggedInUser"));
            if (loggedInUser != null) {
                Button leaveRatingButton = findViewById(R.id.leave_rating_button);
                leaveRatingButton.setOnClickListener(view -> {
                    Intent intent = new Intent(RatingPage.this, LeaveRatingPage.class);
                    Log.d("RatingPage", "User: " + loggedInUser.getUsername());
                    intent.putExtra("loggedInUser", loggedInUser);
                    startActivity(intent);
                });
            } else {
                Log.e("RatingPage", "User is null");
            }
        } else {
            Log.e("RatingPage", "User is not present in intent");
        }
        //endregion

        reviewsReference = FirebaseDatabase.getInstance().getReference().child("Reviews");
        reviewsRecyclerView = findViewById(R.id.ratingRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        fetchReviewsFromFirebase();
    }

    private void fetchReviewsFromFirebase() {
        reviewsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    if (review != null) {
                        reviewList.add(review);
                    }
                }
                updateRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Database Retrieval", "Failed to retrieve data");
            }
        });
    }

    private void updateRecyclerView() {
        reviewAdapter = new ReviewAdapter(reviewList);
        reviewsRecyclerView.setAdapter(reviewAdapter);
    }
}
