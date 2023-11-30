package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscriptionPage extends AppCompatActivity {

    private static final String TAG = "SubscriptionPage";
    PaymentSheet paymentSheet;
    private String paymentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_page);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");

            assert loggedInUser != null;
            String name = loggedInUser.getUsername();
        }

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        Button paymentPageButton = findViewById(R.id.SubscriptionPaymentButton);

        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, UserProfilePage.class);
            startActivity(intent);
        });
        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, FitnessPage.class);
            startActivity(intent);
        });

        paymentPageButton.setOnClickListener(view -> {
            Fuel.INSTANCE.post("http://10.0.2.2:4567/create-subscription", null).responseString(new Handler<String>() {
                @Override
                public void success(String s) {
                    try {
                        final JSONObject result = new JSONObject(s);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        // Change this to 'clientSecret' received from your server
                        paymentClientSecret = result.getString("clientSecret");

                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));
                        presentPaymentSheet();
                        Log.d(TAG, "success: ");
                    } catch (JSONException e) {
                        Log.d(TAG, e.toString());
                    }
                }

                @Override
                public void failure(@NonNull FuelError fuelError) {
                    Log.d(TAG, "failure: " + fuelError);
                }
            });
        });
    }
    private void presentPaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Example, Inc.")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentClientSecret,
                configuration
        );
    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d(TAG, "Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Log.d(TAG, "Completed");
        }
    }
}
