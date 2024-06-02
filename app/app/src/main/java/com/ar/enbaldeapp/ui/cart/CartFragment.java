package com.ar.enbaldeapp.ui.cart;

import static com.ar.enbaldeapp.ui.IntentConstants.ACCESS_TOKEN_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER_FOR_PAYMENT;
import static com.ar.enbaldeapp.ui.IntentConstants.DETAIL_MESSAGE_FOR_CATALOGUE;
import static com.ar.enbaldeapp.ui.IntentConstants.PAYMENT_MESSAGE_FOR_CART;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.databinding.FragmentCartBinding;
import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.services.adapters.CartAdapter;
import com.ar.enbaldeapp.ui.payment.PaymentActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private final ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    updateCart(getView());

                    String message = o.getData().getStringExtra(PAYMENT_MESSAGE_FOR_CART);
                    Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        updateCart(root);

        return root;
    }

    private void updateCart(View root) {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        long cartId = sharedPreferencesManager.getCurrentCartId();
        User user = sharedPreferencesManager.loadCurrentUser();
        String accessToken = sharedPreferencesManager.getAccessToken();

        AtomicReference<Cart> cart = new AtomicReference<>();

        IApiServices apiServices = new ApiServices();
        AtomicReference<CartViewModel> cartViewModel = new AtomicReference<>();
        apiServices.getCart(accessToken, cartId, c -> {
                    cart.set(c);
                    cartViewModel.set(new CartViewModel(c)); // new ViewModelProvider(this).get(CartViewModel.class);
                },
                e -> {
                    cartViewModel.set(new CartViewModel(new Cart(0, new ArrayList<>())));
                });

        RecyclerView recyclerView = root.findViewById(R.id.cartRecyclerView);
        TextView emptyMessage = root.findViewById(R.id.empty_cart_view);
        Button button = root.findViewById(R.id.checkoutButton);

        if (! cart.get().getSelections().isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            CartAdapter adapter = new CartAdapter(getActivity(), cartViewModel.get(), accessToken);
            recyclerView.setAdapter(adapter);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                intent.putExtra(CURRENT_USER_FOR_PAYMENT, user);
                intent.putExtra(CURRENT_CART_FOR_PAYMENT, cart.get());
                intent.putExtra(ACCESS_TOKEN_FOR_PAYMENT, accessToken);

                intentLauncher.launch(intent);
            });

            recyclerView.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        }

    }
}