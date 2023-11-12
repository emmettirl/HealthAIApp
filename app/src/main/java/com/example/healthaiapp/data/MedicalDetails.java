package com.example.healthaiapp.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicalDetails implements Serializable {

    String age;
    String height;
    String weight;
    String gender;
    String gpEmail;
    ContactInfo insurance;

    ArrayList<String> medicalConditions;
    ArrayList<String> medications;

    public MedicalDetails() {
        this.insurance = new ContactInfo();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGpEmail() {
        return gpEmail;
    }

    public void setGpEmail(String gpEmail) {
        this.gpEmail = gpEmail;
    }

    public ContactInfo getInsurance() {
        return insurance;
    }

    public void setInsurance(ContactInfo insurance) {
        this.insurance = insurance;
    }

    public ArrayList<String> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(ArrayList<String> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public ArrayList<String> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<String> medications) {
        this.medications = medications;
    }
}
