package com.example.healthaiapp.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicalDetails implements Serializable {
    ContactInfo gpContact;
    ContactInfo userContact;
    ContactInfo emergencyContact;
    String insurance;

    ArrayList<String> medicalConditions;
    ArrayList<String> medications;

    Boolean smoker;

    public ContactInfo getGpContact() {
        return gpContact;
    }

    public void setGpContact(ContactInfo gpContact) {
        this.gpContact = gpContact;
    }

    public ContactInfo getUserContact() {
        return userContact;
    }

    public void setUserContact(ContactInfo userContact) {
        this.userContact = userContact;
    }

    public ContactInfo getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(ContactInfo emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public ArrayList<String> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(ArrayList<String> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public void addMedicalConditions(String medicalCondition) {
        this.medicalConditions.add(medicalCondition);
    }

    public ArrayList<String> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<String> medications) {
        this.medications = medications;
    }

    public void addMedications(String medication){
        this.medications.add(medication);
    }

    public Boolean getSmoker() {
        return smoker;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }
}
