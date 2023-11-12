package com.example.healthaiapp.data;

public class Review {
    private float rating;
    private String title;
    private String content;
    private String userEmail;


    public Review() {
    }

    public Review(float rating, String title, String content, String userEmail) {
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}