package com.example.healthaiapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class StripeModel {

    private static final String TAG = "SubscriptionManager";

    public interface SubscriptionCallback {
        void onSubscriptionCheckCompleted(boolean hasActiveSubscription);
    }

    public void checkActiveSubscription(String stripeID, SubscriptionCallback callback) {
        Fuel.INSTANCE.post("http://10.0.2.2:4567/check-subscription", null)
                .body("customerId=" + stripeID, StandardCharsets.UTF_8)
                .responseString(new Handler<String>() {
                    @Override
                    public void success(String s) {
                        try {
                            final JSONObject result = new JSONObject(s);
                            boolean hasActiveSubscription = result.getBoolean("hasActiveSubscription");
                            callback.onSubscriptionCheckCompleted(hasActiveSubscription);
                        } catch (JSONException e) {
                            Log.d(TAG, e.toString());
                            callback.onSubscriptionCheckCompleted(false);
                        }
                    }

                    @Override
                    public void failure(@NonNull FuelError fuelError) {
                        Log.d(TAG, "failure: " + fuelError);
                        callback.onSubscriptionCheckCompleted(false);
                    }
                });
    }
}
