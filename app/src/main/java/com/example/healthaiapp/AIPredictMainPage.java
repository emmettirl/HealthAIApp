package com.example.healthaiapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.ApiCall;
import com.example.healthaiapp.data.ApiCall.ApiResponse;
import com.example.healthaiapp.data.ApiCall.SymptomsRequestBody;
import com.example.healthaiapp.data.User;

import java.util.Arrays;
import java.util.List;


public class AIPredictMainPage extends AppCompatActivity {
    private User loggedInUser;
    private static final String TAG = AIPredictMainPage.class.getSimpleName();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_predict_main);

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
        }
    }

    private void sendApiRequest() {
        String apiUrl = "http://127.0.0.1:5000/predictAI";
        Object requestBody = createRequestBody(); // Create your request body here

        // Create an instance of ApiCall
        ApiCall apiCall = new ApiCall(apiUrl, requestBody, new ApiCall.ApiCallback() {
            @Override
            public void onApiCallComplete(ApiResponse result) {
                if (result.getError() == null) {
                    // Handle the successful response here
                    String response = result.getStringValue();
                    // Do something with the response
                    Log.d(TAG, "onApiCallComplete: " + response);
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

    private Object createRequestBody() {
        // Create and return your request body object here
        // Example: new YourRequestBodyClass(parameters);
        List<String> symptomsList = Arrays.asList("redness_of_eyes", "restlessness", "runny_nose");
        return new SymptomsRequestBody(symptomsList);
    }
}
