package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import android.net.Uri;

import java.util.Objects;

public class ContactPage extends AppCompatActivity {

    private ToggleButton contactToggleButton;
    private Button sendEmailButton;
    private TextInputEditText emailTitleInput;
    private TextInputEditText emailContentInput;
    private Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        //region Nav Buttons

        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.FitnessNavButton);
        Button FitnessPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ContactPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        //endregion

        contactToggleButton = findViewById(R.id.contactToggleButton);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        TextInputLayout emailTitleInputLayout = findViewById(R.id.emailTitleInput);
        TextInputLayout emailContentInputLayout = findViewById(R.id.emailContentInput);
        callButton = findViewById(R.id.callButton);

        // Retrieve TextInputEditText from TextInputLayout
        emailTitleInput = (TextInputEditText) emailTitleInputLayout.getEditText();
        emailContentInput = (TextInputEditText) emailContentInputLayout.getEditText();

        sendEmailButton.setOnClickListener(v -> {
            String recipientEmail;
            String emailTitle = Objects.requireNonNull(emailTitleInput.getText()).toString();
            String emailContent = Objects.requireNonNull(emailContentInput.getText()).toString();

            if (contactToggleButton.isChecked()) {
                recipientEmail = "patryk.dudek@mycit.ie";
            } else {
                recipientEmail = "patryk.dudek@mycit.ie";
            }

            composeEmail(recipientEmail, emailTitle, emailContent);
        });

        callButton.setOnClickListener(v -> initiatePhoneCall());
    }

    private void composeEmail(String recipient, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        startActivity(Intent.createChooser(emailIntent, "Send email using:"));
    }

    private void initiatePhoneCall() {
        String phoneNumber = contactToggleButton.isChecked() ? "0871642511" : "0871656573";
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneUri);
        startActivity(callIntent);
    }
}