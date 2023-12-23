// To test a subscription:
// card number: 4242 4242 4242 4242
// expiry: Any future date
// cvv: 000
// post code: 000000

package com.example.healthaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthaiapp.data.StripeModel;
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


public class SubscriptionPage extends AppCompatActivity {

    UserViewModel uvm;
    StripeModel sm;


    private static final String TAG = "SubscriptionPage";
    User loggedInUser;
    String stripeID;
    PaymentSheet paymentSheet;
    private String paymentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    Button paymentPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_page);

        uvm = new UserViewModel();
        sm = new StripeModel();
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        paymentPageButton = findViewById(R.id.SubscriptionPaymentButton);

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("loggedInUser")) {
            User loggedInUser = (User) thisIntent.getSerializableExtra("loggedInUser");

            assert loggedInUser != null;
            this.loggedInUser = loggedInUser;
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
                            Intent intent = new Intent(SubscriptionPage.this, FitnessPage.class);
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
                            Intent intent = new Intent(SubscriptionPage.this, LandingPage.class);
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
                            Intent intent = new Intent(SubscriptionPage.this, UserProfilePage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            Log.d("mydebug", loggedInUser.getUsername());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        );

        //endregion

        this.stripeID = this.loggedInUser.getStripeID();
        if (stripeID != null) {

            sm.checkActiveSubscription(stripeID, new StripeModel.SubscriptionCallback() {
                @Override
                public void onSubscriptionCheckCompleted(boolean hasActiveSubscription) {
                    if (hasActiveSubscription) {
                        paymentPageButton.setText(R.string.already_subscribed);
                        paymentPageButton.setOnClickListener(view -> {
                            Intent intent = new Intent(SubscriptionPage.this, LandingPage.class);
                            intent.putExtra("loggedInUser", loggedInUser);
                            startActivity(intent);
                        });
                    } else {
                        paymentPageButton.setText(getString(R.string.subscribe));
                        paymentPageButton.setOnClickListener(view -> {
                            createSubscription();
                        });
                    }

                }
            });
        } else {
            paymentPageButton.setText(getString(R.string.subscribe));
            paymentPageButton.setOnClickListener(view -> {
                createSubscription();
            });
        }
    }


    private void createSubscription(){
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
            paymentPageButton.setText(R.string.already_subscribed);
            paymentPageButton.setOnClickListener(view -> {
                Intent intent = new Intent(SubscriptionPage.this, LandingPage.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            });
            uvm.updateUser(loggedInUser);
            Log.d(TAG, "Completed");
        }
    }
}