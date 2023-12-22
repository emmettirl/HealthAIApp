package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import com.example.healthaiapp.data.ReviewViewModel;
import com.example.healthaiapp.data.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

public class LeaveRatingPage extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RatingBar ratingBar;
    private Button sendReviewButton;
    private ReviewViewModel reviewViewModel;
    private User loggedInUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_rating_page);
        FirebaseApp.initializeApp(this);


        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.homeNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(LeaveRatingPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(LeaveRatingPage.this, FitnessPage.class);
            startActivity(intent);
        });
        //endregion

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Reviews");

        ratingBar = findViewById(R.id.ratingBar);
        sendReviewButton = findViewById(R.id.sendReviewButton);
        TextInputLayout reviewTitleInputLayout = findViewById(R.id.reviewTitleInput);
        TextInputLayout reviewContentInputLayout = findViewById(R.id.reviewContentInput);

        TextInputEditText reviewTitleInput = (TextInputEditText) reviewTitleInputLayout.getEditText();
        TextInputEditText reviewContentInput = (TextInputEditText) reviewContentInputLayout.getEditText();

        sendReviewButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String reviewTitle = reviewTitleInput.getText().toString();
            String reviewContent = reviewContentInput.getText().toString();
            String userEmail = loggedInUser.getEmail();

            User loggedInUser = null;
            Intent intent = getIntent();
            if (intent.hasExtra("loggedInUser")) {
                loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
            }

            if (loggedInUser != null && isValidReview(rating, reviewTitle, reviewContent, userEmail)) {
                reviewViewModel.addReview(rating, reviewTitle, reviewContent, userEmail);

                Intent successIntent = new Intent(LeaveRatingPage.this, ReviewSuccess.class);
                startActivity(successIntent);
                finish();
            } else {
                TextView errorTextView = findViewById(R.id.invalidReviewText);
                TextView errorExplanationTextView = findViewById(R.id.invalidReviewExplanationText);
                errorTextView.setVisibility(View.VISIBLE);
                errorExplanationTextView.setVisibility(View.VISIBLE);
            }
        });

    }
        private boolean isValidReview ( float rating, String title, String content, String userEmail){
            return (rating > 0 && !title.isEmpty() && !content.isEmpty() && !userEmail.isEmpty());
        }
    }
