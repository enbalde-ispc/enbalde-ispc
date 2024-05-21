package com.ar.enbaldeapp.ui.details;

import static com.ar.enbaldeapp.ui.IntentConstants.ACCESS_TOKEN_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.DETAIL_MESSAGE_FOR_CATALOGUE;
import static com.ar.enbaldeapp.ui.IntentConstants.PRODUCT_FOR_DETAIL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.services.ApiServices;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicReference;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    private User currentUser;
    private Cart currentCart;
    private String accessToken;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        product = (Product)intent.getSerializableExtra(PRODUCT_FOR_DETAIL);
        currentUser = (User)intent.getSerializableExtra(CURRENT_USER_FOR_DETAIL);
        currentCart = (Cart)intent.getSerializableExtra(CURRENT_CART_FOR_DETAIL);
        accessToken = intent.getStringExtra(ACCESS_TOKEN_FOR_DETAIL);

        ImageView imageView = this.findViewById(R.id.imageViewDetail);
        Picasso.with(getApplicationContext()).load(product.getImage()).into(imageView);

        ((TextView)this.findViewById(R.id.textViewDetailName)).setText(product.getName());
        ((TextView)this.findViewById(R.id.textViewDetailDescription)).setText(product.getDescription());

        editText = this.findViewById(R.id.editNumberDetailAmount);

        this.findViewById(R.id.buttonDetailAdd).setOnClickListener(v -> {
            Intent result = new Intent();
            ApiServices apiServices = new ApiServices();
            apiServices.addToCart(accessToken, currentCart, product, Integer.parseInt(editText.getText().toString()),
                    c -> {
                        result.putExtra(DETAIL_MESSAGE_FOR_CATALOGUE, "Product added correctly");
                    },
                    e -> {
                        result.putExtra(DETAIL_MESSAGE_FOR_CATALOGUE, e.getMessage());
                    });

            setResult(Activity.RESULT_OK, result);
            finish();
        });

        if (currentUser == null) {
            EditText editText = this.findViewById(R.id.editNumberDetailAmount);
            editText.setVisibility(View.GONE);

            Button button = this.findViewById(R.id.buttonDetailAdd);
            button.setVisibility(View.GONE);
        }
    }
}