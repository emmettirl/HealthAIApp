package com.example.healthaiapp.data;

public class SymptomListItem {
    private String name;
    private boolean isSelected;

    public SymptomListItem(String symptomLabel) {
        name = symptomLabel;
        isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean isChecked) {
        isSelected = isChecked;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
