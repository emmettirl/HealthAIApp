package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

public class ContactPage extends AppCompatActivity {

    private ToggleButton contactToggleButton;
    private Button sendEmailButton;
    private TextInputEditText emailTitleInput;
    private TextInputEditText emailContentInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        //region NAVBAR

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

        // Fetching TextInputEditText from TextInputLayout
        TextInputLayout emailTitleInputLayout = findViewById(R.id.emailTitleInput);
        TextInputLayout emailContentInputLayout = findViewById(R.id.emailContentInput);

        // Retrieve TextInputEditText from TextInputLayout
        emailTitleInput = (TextInputEditText) emailTitleInputLayout.getEditText();
        emailContentInput = (TextInputEditText) emailContentInputLayout.getEditText();

        sendEmailButton.setOnClickListener(v -> {
            String recipientEmail;
            String emailTitle = emailTitleInput.getText().toString();
            String emailContent = emailContentInput.getText().toString();

            if (contactToggleButton.isChecked()) {
                recipientEmail = "patryk.dudek@mycit.ie";
            } else {
                recipientEmail = "patryk.dudek@mycit.ie";
            }

            composeEmail(recipientEmail, emailTitle, emailContent);
        });
    }

    private void composeEmail(String recipient, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        startActivity(Intent.createChooser(emailIntent, "Send email using:"));
    }
}