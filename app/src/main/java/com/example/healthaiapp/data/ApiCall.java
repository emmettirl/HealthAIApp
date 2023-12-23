package com.example.healthaiapp.data;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;

import org.json.JSONObject;

public class ApiCall extends AsyncTask<Void, Void, ApiCall.ApiResponse> {

    private final ApiCallback callback;
    private final String apiUrl;
    private final Object requestBody;

    public ApiCall(String apiUrl, JSONObject requestBody, ApiCallback callback) {
        this.apiUrl = apiUrl;
        this.requestBody = requestBody;
        this.callback = callback;
    }

    @Override
    protected ApiResponse doInBackground(Void... voids) {
        ApiResponse result = null;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                // Request method + header
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setDoOutput(true);

                // Request Body
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(requestBody.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                result = new ApiResponse(stringBuilder.toString(), null);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = new ApiResponse(null, e.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(ApiResponse result) {
        if (callback != null) {
            callback.onApiCallComplete(result);
        }
    }

    private String convertObjectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public interface ApiCallback {
        void onApiCallComplete(ApiResponse result);
    }

    public static class ApiResponse {
        private final String stringValue;
        private int intValue;
        private final String error;

        public ApiResponse(String stringValue, String error) {
            this.stringValue = stringValue;
            this.error = error;

            try {
                this.intValue = Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                // If the string can't be parsed as an integer
                this.intValue = -1337; // an error indicator
            }
        }

        public String getStringValue() {
            return stringValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public String getError() {
            return error;
        }
    }

    public static class SymptomsRequestBody {
        private List<String> symptoms;

        public SymptomsRequestBody(List<String> symptoms) {
            this.symptoms = symptoms;
        }

        // Getter and Setter
        public List<String> getSymptoms() {
            return symptoms;
        }

        public void setSymptoms(List<String> symptoms) {
            this.symptoms = symptoms;
        }
    }
}