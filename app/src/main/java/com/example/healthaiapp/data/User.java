package com.example.healthaiapp.data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    @Exclude
    String username;
    String password;
    String fName;
    String lName;
    String email;
    MedicalDetails medicalDetails;

    public User(){
    }


    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String fName, String lName, String email){
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public User(String username, String password, String fName, String lName, String email, MedicalDetails medicalDetails){
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.medicalDetails = medicalDetails;
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

    public void setMedicalDetailsList(MedicalDetails medicalDetails) {
        this.medicalDetails = medicalDetails;
    }


}
