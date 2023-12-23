package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthaiapp.data.ApiCall;
import com.example.healthaiapp.data.ApiCall.ApiResponse;
import com.example.healthaiapp.data.SymptomListItem;
import com.example.healthaiapp.data.SymptomRecyclerAdapter;
import com.example.healthaiapp.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AIPredictMainPage extends AppCompatActivity {
    private User loggedInUser;
    private static final String TAG = AIPredictMainPage.class.getSimpleName();
    List<String>symptomsList;
    String prediction;
    RecyclerView recyclerView;
    SymptomRecyclerAdapter adapter;
    TextView predictionTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_predict_main);
        predictionTextView = findViewById(R.id.tv_predict);


        recyclerView = findViewById(R.id.chatbotRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        symptomsList = new ArrayList<>(); // Initialize as empty list
        adapter = new SymptomRecyclerAdapter(symptomsList);
        recyclerView.setAdapter(adapter);

        // ... rest of your onCreate method ...

        if (getIntent().hasExtra("loggedInUser")) {
            loggedInUser = (User) getIntent().getSerializableExtra("loggedInUser");
            symptomLabelsApiRequest();

        }

        //region Nav Buttons
        ImageButton userProfileButton = findViewById(R.id.userProfileNavButton);
        ImageButton fitnessPageButton = findViewById(R.id.FitnessNavButton);
        ImageButton homePageButton = findViewById(R.id.homeNavButton);
        Button chatbotSendButton = findViewById(R.id.chatbotSendButton);

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

        chatbotSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SymptomListItem> selectedSymptomsItems = adapter.getSelectedItems();
                List<String> selectedSymptoms = new ArrayList<>();

                for (SymptomListItem item : selectedSymptomsItems) {
                    selectedSymptoms.add(item.getName());
                    Log.d(TAG, "Selected Item: " + item.getName());
                }

                // Now pass this list to the method that makes the API call
                sendPredictionApiRequest(selectedSymptoms);
            }
        });

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
                    String response = result.getStringValue();
                    Log.d(TAG, "onApiCallComplete: " + response);

                    try {
                        JSONObject responseObject = new JSONObject(response);
                        JSONArray symptomsJsonArray = responseObject.getJSONArray("symptoms");
                        List<String> symptomsList = new ArrayList<>();

                        for (int i = 0; i < symptomsJsonArray.length(); i++) {
                            symptomsList.add(symptomsJsonArray.getString(i));
                        }

                        // Update adapter data
                        runOnUiThread(() -> {
                            adapter.updateData(symptomsList);
                        });

                    } catch (Exception e) {
                        Log.e(TAG, "Error in parsing response", e);
                    }
                } else {
                    String error = result.getError();
                    Log.d(TAG, "onApiCallComplete: " + error);
                }
            }
        });
        apiCall.execute();

    }

    private void sendPredictionApiRequest(List<String> selectedSymptoms) {
        String apiUrl = "http://10.0.2.2:5000/predictAI";
        JSONObject requestBody = createRequestBody(selectedSymptoms);
        Log.d(TAG, "sendApiRequest: " + requestBody);


        ApiCall apiCall = new ApiCall(apiUrl, requestBody, new ApiCall.ApiCallback() {
            @Override
            public void onApiCallComplete(ApiResponse result) {
                if (result.getError() == null) {
                    String response = result.getStringValue();
                    try {
                        JSONObject responseObject = new JSONObject(response);
                        Log.d(TAG, "onApiCallComplete: " + response);
                        prediction = responseObject.getString("prediction");
                        predictionTextView.setText("Prediction: " + prediction);
                    } catch (JSONException e) {
                        Log.d(TAG, "onApiCallComplete: " + "error in parsing response");
                    }

                } else {
                    String error = result.getError();
                    Log.d(TAG, "onApiCallComplete: " + error);
                }
            }
        });
        apiCall.execute();
    }

    private JSONObject createRequestBody(List<String> selectedSymptoms) {
        try {
            JSONObject requestBody = new JSONObject();
            JSONArray symptomsJsonArray = new JSONArray();

            for (String symptom : selectedSymptoms) {
                symptomsJsonArray.put(symptom);
            }

            requestBody.put("symptomsList", symptomsJsonArray);
            return requestBody;
        } catch (Exception e) {
            Log.e(TAG, "Error in creating request body", e);
            return null;
        }
    }

}
