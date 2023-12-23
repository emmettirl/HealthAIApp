package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.ApiCall;
import com.example.healthaiapp.data.ApiCall.ApiResponse;
import com.example.healthaiapp.data.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;


public class AIPredictMainPage extends AppCompatActivity {
    private User loggedInUser;
    private static final String TAG = AIPredictMainPage.class.getSimpleName();
    String symptomLabels;
    String prediction;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_predict_main);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
            symptomLabelsApiRequest();
            sendPredictionApiRequest();
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
                            Intent intent = new Intent(AIPredictMainPage.this, FitnessPage.class);
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
                            Intent intent = new Intent(AIPredictMainPage.this, LandingPage.class);
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
                            Intent intent = new Intent(AIPredictMainPage.this, UserProfilePage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        //endregion
    }

    private void symptomLabelsApiRequest() {
        String apiUrl = "http://10.0.2.2:5000/symptomsList";
        JSONObject requestBody = new JSONObject(); // Create your request body here
        Log.d(TAG, "sendApiRequest: " + requestBody);

        // Create an instance of ApiCall
        ApiCall apiCall = new ApiCall(apiUrl, requestBody, new ApiCall.ApiCallback() {
            @Override
            public void onApiCallComplete(ApiResponse result) {
                if (result.getError() == null) {
                    // Handle the successful response here
                    String response = result.getStringValue();
                    // Do something with the response
                    Log.d(TAG, "onApiCallComplete: " + response);
                    symptomLabels = response;
                } else {
                    // Handle the error here
                    String error = result.getError();
                    // Do something with the error
                    Log.d(TAG, "onApiCallComplete: " + error);
                }
            }
        });
        apiCall.execute();

    }

    private void sendPredictionApiRequest() {
        String apiUrl = "http://10.0.2.2:5000/predictAI";
        JSONObject requestBody = createRequestBody(); // Create your request body here
        Log.d(TAG, "sendApiRequest: " + requestBody);

        // Create an instance of ApiCall
        ApiCall apiCall = new ApiCall(apiUrl, requestBody, new ApiCall.ApiCallback() {
            @Override
            public void onApiCallComplete(ApiResponse result) {
                if (result.getError() == null) {
                    // Handle the successful response here
                    String response = result.getStringValue();
                    // Do something with the response
                    Log.d(TAG, "onApiCallComplete: " + response);
                    prediction = response;
                } else {
                    // Handle the error here
                    String error = result.getError();
                    // Do something with the error
                    Log.d(TAG, "onApiCallComplete: " + error);
                }
            }
        });
        apiCall.execute();
    }

    private JSONObject createRequestBody() {
        try {
            // Create a JSON object
            JSONObject requestBody = new JSONObject();

            // Add the symptoms list to the JSON object
            JSONArray symptomsJsonArray = new JSONArray(Arrays.asList("redness_of_eyes", "restlessness", "runny_nose"));

            requestBody.put("symptomsList", symptomsJsonArray);

            return requestBody;
        } catch (Exception e) {
            Log.e(TAG, "Error in creating request body", e);
            return null;
        }
    }
}
