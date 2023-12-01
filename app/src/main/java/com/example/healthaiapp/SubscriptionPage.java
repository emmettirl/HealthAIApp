// To test a subscription:
// card number: 4242 4242 4242 4242
// expiry: Any future date
// cvv: 000
// post code: 000000

package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.User;
import com.example.healthaiapp.data.UserViewModel;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class SubscriptionPage extends AppCompatActivity {

    UserViewModel uvm;


    private static final String TAG = "SubscriptionPage";
    User loggedInUser;
    String stripeID;
    PaymentSheet paymentSheet;
    private String paymentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    Boolean premiumCheck = Boolean.FALSE;
    Button paymentPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_page);

        uvm = new UserViewModel();
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");

            assert loggedInUser != null;
            this.loggedInUser = loggedInUser;
        }

        //region Nav Buttons
        Button userProfileButton = findViewById(R.id.userProfileNavButton);
        Button AIPredictPLACEHOLDER = findViewById(R.id.AIPredictNavButton);
        Button FitnessPageButton = findViewById(R.id.FitnessNavButton);
        this.paymentPageButton = findViewById(R.id.SubscriptionPaymentButton);

        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, UserProfilePage.class);
            startActivity(intent);
        });

        FitnessPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubscriptionPage.this, FitnessPage.class);
            startActivity(intent);
        });

        this.stripeID = this.loggedInUser.getStripeID();
        checkActiveSubscription(stripeID, new SubscriptionCallback(){
            @Override
            public void onSubscriptionCheckCompleted(boolean hasActiveSubscription) {
                if (hasActiveSubscription) {
                    premiumCheck = Boolean.TRUE;
                    paymentPageButton.setText(R.string.already_subscribed);
                    paymentPageButton.setOnClickListener(null);
                }
                else {
                    paymentPageButton.setText(getString(R.string.subscribe));

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

            }
        });



    }

    public interface SubscriptionCallback {
        void onSubscriptionCheckCompleted(boolean hasActiveSubscription);
    }

    private void checkActiveSubscription(String stripeID, SubscriptionCallback callback) {
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
            loggedInUser.setStripeID(customerConfig.getId());
            loggedInUser.setPremium(Boolean.TRUE);
            premiumCheck = Boolean.TRUE;
            paymentPageButton.setText(R.string.already_subscribed);
            paymentPageButton.setOnClickListener(null);
            uvm.updateUser(loggedInUser);
            Log.d(TAG, "Completed");
        }
    }
}