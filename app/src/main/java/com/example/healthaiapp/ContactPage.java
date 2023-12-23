package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthaiapp.data.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import android.net.Uri;

import java.util.Objects;

public class ContactPage extends AppCompatActivity {

    private ToggleButton contactToggleButton;
    private ImageButton sendEmailButton;
    private TextInputEditText emailTitleInput;
    private TextInputEditText emailContentInput;
    private ImageButton callButton;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }

        //region Nav Buttons
        ImageButton userProfileButton = findViewById(R.id.userProfileNavButton);
        ImageButton fitnessPageButton = findViewById(R.id.FitnessNavButton);
        ImageButton homePageButton = findViewById(R.id.homeNavButton);

        fitnessPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(ContactPage.this, FitnessPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        homePageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(ContactPage.this, LandingPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        userProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myDebug", "onClick: ");
                        Log.d("TAG", loggedInUser.getUsername());

                        if (loggedInUser != null) {
                            Intent intent = new Intent(ContactPage.this, UserProfilePage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        //endregion

        contactToggleButton = findViewById(R.id.contactToggleButton);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        TextInputLayout emailTitleInputLayout = findViewById(R.id.emailTitleInput);
        TextInputLayout emailContentInputLayout = findViewById(R.id.emailContentInput);
        callButton = findViewById(R.id.callButton);

        emailTitleInput = (TextInputEditText) emailTitleInputLayout.getEditText();
        emailContentInput = (TextInputEditText) emailContentInputLayout.getEditText();

        sendEmailButton.setOnClickListener(v -> {
            String recipientEmail;
            String emailTitle = Objects.requireNonNull(emailTitleInput.getText()).toString();
            String emailContent = Objects.requireNonNull(emailContentInput.getText()).toString();

            if (contactToggleButton.isChecked()) {
                // Insurance Email
                recipientEmail = loggedInUser.getMedicalDetails().getInsurance().getEmail();
            } else {
                // GP Email
                recipientEmail = loggedInUser.getMedicalDetails().getGpEmail();
            }

            composeEmail(recipientEmail, emailTitle, emailContent);
        });

        callButton.setOnClickListener(v -> {
            String phoneNumber;
            if (contactToggleButton.isChecked()) {
                // Insurance Phone
                phoneNumber = loggedInUser.getMedicalDetails().getInsurance().getPhone();
            } else {
                // GP Phone
                phoneNumber = "000";
//                phoneNumber = loggedInUser.getMedicalDetails().getGpPhoneNumber();
            }
            makeCall(phoneNumber);
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

    private void makeCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}