package com.example.healthaiapp.data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {
    @Exclude
    String username;
    String password;
    String fName;
    String lName;
    String email;
    MedicalDetails medicalDetails;
    Boolean isPremium;
    String stripeID;

    public User(){
        this.medicalDetails = new MedicalDetails();

    }


    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.medicalDetails = new MedicalDetails();
    }

    public User(String username, String password, String fName, String lName, String email){
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.medicalDetails = new MedicalDetails();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public MedicalDetails getMedicalDetails() {
        return medicalDetails;
    }

    public String getStripeID() {
        return stripeID;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMedicalDetails(MedicalDetails medicalDetails) {
        this.medicalDetails = medicalDetails;
    }
    public void setStripeID(String stripeID){
        this.stripeID = stripeID;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }
}
