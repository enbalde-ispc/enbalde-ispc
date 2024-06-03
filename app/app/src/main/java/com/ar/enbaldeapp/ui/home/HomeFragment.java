package com.ar.enbaldeapp.ui.home;

import static com.ar.enbaldeapp.ui.IntentConstants.ACCESS_TOKEN_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.DETAIL_MESSAGE_FOR_CATALOGUE;
import static com.ar.enbaldeapp.ui.IntentConstants.PRODUCT_FOR_DETAIL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ar.enbaldeapp.databinding.FragmentHomeBinding;
import com.ar.enbaldeapp.ui.contact.ContactActivity;
import com.ar.enbaldeapp.ui.details.ProductDetailActivity;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    Snackbar.make(getView(), "Mensaje enviado con éxito", Snackbar.LENGTH_SHORT).show();
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.contactUs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ContactActivity.class);
            intentLauncher.launch(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}