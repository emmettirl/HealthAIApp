package com.example.healthaiapp.data;

import static com.example.healthaiapp.data.Constants.NODE_USERS;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewViewModel extends ViewModel {
    private DatabaseReference reviewsReference;

    public ReviewViewModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reviewsReference = database.getReference().child("Reviews");
    }

    public void addReview(float rating, String title, String content, String userEmail) {
        Log.d("ReviewViewModel", "User Email in ReviewViewModel: " + userEmail);
        String reviewId = reviewsReference.push().getKey();
        if (reviewId != null) {
            Review review = new Review(rating, title, content, userEmail);
            reviewsReference.child(reviewId).setValue(review)
                    .addOnSuccessListener(aVoid -> Log.d("ReviewViewModel", "Review added successfully"))
                    .addOnFailureListener(e -> Log.e("ReviewViewModel", "Failed to add review: " + e.getMessage()));
        }
    }
}
