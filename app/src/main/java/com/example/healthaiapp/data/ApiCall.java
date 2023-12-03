package com.example.healthaiapp.data;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class ApiCall extends AsyncTask<Void, Void, ApiCall.ApiResponse> {

    private ApiCallback callback;
    private String apiUrl;
    private Object requestBody;

    public ApiCall(String apiUrl, Object requestBody, ApiCallback callback) {
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
                outputStream.write(convertObjectToJson(requestBody).getBytes());
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
        private String stringValue;
        private int intValue;
        private String error;

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
}

//region USAGE EXAMPLE!
//String apiUrl = "";
//
//    YourObject yourObject = new YourObject();
//    Replace YourObject with the actual class of the object you want to send
//
//    ApiCall apiCall = new ApiCall(apiUrl, yourObject, new ApiCall.ApiCallback() {
//        @Override
//        public void onApiCallComplete(ApiCall.ApiResponse result) {
//            // API response handling
//            if (result != null) {
//                if (result.getError() != null) {
//                    // If error occurs
//                    // result.getError()
//                } else {
//                    // Either result.getStringValue() or result.getIntValue()
//                    String stringValue = result.getStringValue();
//                    int intValue = result.getIntValue();
//
//                    // Further handling based on what's needed
//                }
//            }
//        }
//    });
//
//apiCall.execute();
//endregion