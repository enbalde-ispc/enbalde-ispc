package com.ar.enbaldeapp.ui.payment;

import static com.ar.enbaldeapp.ui.IntentConstants.ACCESS_TOKEN_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.PAYMENT_MESSAGE_FOR_CART;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.PaymentType;
import com.ar.enbaldeapp.models.ShippingMethod;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiRequest;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.services.adapters.ShippingMethodSpinnerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.Token;
import com.stripe.android.paymentsheet.LinkHandler;
import com.stripe.android.view.CardInputWidget;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String accessToken;
    private User currentUser;
    private Cart currentCart;
    private ShippingMethod shippingMethod;
    private Spinner spinner;
    private PaymentType paymentType;
    private String stripeKey;
    private CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent intent = getIntent();
        currentUser = (User)intent.getSerializableExtra(CURRENT_USER_FOR_PAYMENT);
        currentCart = (Cart)intent.getSerializableExtra(CURRENT_CART_FOR_PAYMENT);
        accessToken = intent.getStringExtra(ACCESS_TOKEN_FOR_PAYMENT);
        paymentType = PaymentType.CASH_TO_PAY;

        RadioButton radioButton = this.findViewById(R.id.cashRadioButton);
        radioButton.setSelected(true);

        TextView totalTextView = this.findViewById(R.id.paymentTotalTextView);
        totalTextView.setText("Total");

        stripeKey = "pk_test_51PNOnNAI1OQ6xCK87d15hp031AIdnT1S0hudDQrCkhcmybaQkvym9CcL6l7ipxd7ghsvR6dAZjuENrRzmG7jy16300lvHWQmvt";

        PaymentConfiguration.init(getApplicationContext(), stripeKey);

        spinner = this.findViewById(R.id.paymentShipmentSpinner);
        spinner.setOnItemSelectedListener(this);

        View parentLayout = findViewById(android.R.id.content);

        IApiServices apiServices = new ApiServices();
        apiServices.getShippingMethods(accessToken, s -> {
                    ShippingMethodSpinnerAdapter<ShippingMethod> adapter = new ShippingMethodSpinnerAdapter<>(s, ShippingMethod::getId, ShippingMethod::getName);
                    spinner.setAdapter(adapter);
                },
                e -> {
                    Snackbar.make(parentLayout, "Could not retrieve shipping methods: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                });

        cardInputWidget = findViewById(R.id.paymentCardInputWidget);
        Button paymentButton = this.findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(v ->
        {
                if (paymentType == PaymentType.CASH_TO_PAY) {
                    apiServices.checkout(accessToken, currentCart, shippingMethod, paymentType, "",
                            s -> {
                                apiServices.replaceCart(accessToken, currentUser, i -> {
                                            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());
                                            sharedPreferencesManager.saveCurrentCartId(i);

                                            Intent result = new Intent();
                                            result.putExtra(PAYMENT_MESSAGE_FOR_CART, "Cart bought successfully");
                                            setResult(Activity.RESULT_OK, result);
                                            finish();
                                        },
                                        ee -> {
                                            Snackbar.make(parentLayout, "Could not get a new cart: " + ee.getMessage(), Snackbar.LENGTH_SHORT).show();
                                        });
                            },
                            e -> Snackbar.make(parentLayout, "Could not finish sale: " + e.getMessage(), Snackbar.LENGTH_SHORT).show());
                }
                else {
                    Stripe stripe = new Stripe(this, stripeKey);

                    PaymentMethodCreateParams.Card card = cardInputWidget.getPaymentMethodCard();
                    PaymentMethodCreateParams cardParams = cardInputWidget.getPaymentMethodCreateParams();
                    if (card != null) {
                        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.create(card);

                        stripe = new Stripe(getApplicationContext(), PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey());
                        stripe.createPaymentMethod(cardParams, new ApiResultCallback<PaymentMethod>() {
                            @Override
                            public void onSuccess(@NonNull PaymentMethod paymentMethod) {
                                apiServices.checkout(accessToken, currentCart, shippingMethod, PaymentType.STRIPE_PAID, "",
                                        s -> {
                                            apiServices.replaceCart(accessToken, currentUser, i -> {
                                                        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());
                                                        sharedPreferencesManager.saveCurrentCartId(i);

                                                        Intent result = new Intent();
                                                        result.putExtra(PAYMENT_MESSAGE_FOR_CART, "Cart bought successfully");
                                                        setResult(Activity.RESULT_OK, result);
                                                        finish();
                                                    },
                                                    ee -> {
                                                        Snackbar.make(parentLayout, "Could not get a new cart: " + ee.getMessage(), Snackbar.LENGTH_SHORT).show();
                                                    });
                                        },
                                        e -> Snackbar.make(parentLayout, "Could not finish sale: " + e.getMessage(), Snackbar.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onError(@NonNull Exception e) {
                                Snackbar.make(parentLayout, "Error pagando en Stripe", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                });

        RadioGroup radioGroup = this.findViewById(R.id.paymentRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cashRadioButton) {
                    paymentType = PaymentType.CASH_TO_PAY;
                    cardInputWidget.setVisibility(View.GONE);
                }
                else if (checkedId == R.id.enbaldePagoRadioButton) {
                    paymentType = PaymentType.STRIPE_TO_PAY;
                    cardInputWidget.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void refreshTotal() {
        double total = currentCart.getSelections().stream().mapToDouble(s -> s.getQuantity() * s.getProduct().getPrice()).sum();
        if (shippingMethod != null) {
            total += shippingMethod.getCost();
        }

        TextView totalTextView = this.findViewById(R.id.paymentTotalValueTextView);
        totalTextView.setText("$" + total);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        shippingMethod = (ShippingMethod)parent.getItemAtPosition(position);
        refreshTotal();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}