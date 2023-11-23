package com.example.healthaiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stripe.android.paymentsheet.*;
import com.stripe.android.PaymentConfiguration;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentPage extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    private String paymentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);


        Fuel.INSTANCE.post("http://10.0.2.2:4567", null).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    final JSONObject result = new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentIntentClientSecret = result.getString("paymentIntent");
                    PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));
                } catch (JSONException e) { /* handle error */ }
            }

            @Override
            public void failure(@NonNull FuelError fuelError) { /* handle error */ }
        });

        presentPaymentSheet();
    }

    private void presentPaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Example, Inc.")
                .customer(customerConfig)
                // Set `allowsDelayedPaymentMethods` to true if your business handles payment methods
                // delayed notification payment methods like US bank accounts.
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
            // Display for example, an order confirmation screen
            Log.d(TAG, "Completed");
        }
    }
}
