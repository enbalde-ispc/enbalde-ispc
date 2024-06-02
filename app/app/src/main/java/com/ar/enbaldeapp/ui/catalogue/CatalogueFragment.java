package com.ar.enbaldeapp.ui.catalogue;

import static com.ar.enbaldeapp.ui.IntentConstants.ACCESS_TOKEN_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER_FOR_DETAIL;
import static com.ar.enbaldeapp.ui.IntentConstants.DETAIL_MESSAGE_FOR_CATALOGUE;
import static com.ar.enbaldeapp.ui.IntentConstants.PRODUCT_FOR_DETAIL;

import android.app.Activity;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.databinding.FragmentCatalogueBinding;
import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.adapters.CatalogueAdapter;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.ui.details.ProductDetailActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CatalogueFragment extends Fragment {
    private FragmentCatalogueBinding binding;
    private final ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    String message = o.getData().getStringExtra(DETAIL_MESSAGE_FOR_CATALOGUE);
                    Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //CatalogueViewModel catalogueViewModel = new ViewModelProvider(this).get(CatalogueViewModel.class);

        binding = FragmentCatalogueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewCatalogue);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CatalogueAdapter adapter = new CatalogueAdapter(getActivity(), getCatalogue());
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListeners((position, product) -> {
            Context context = getActivity();

            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
            User user = sharedPreferencesManager.loadCurrentUser();

            if (user != null) {
                long cartId = sharedPreferencesManager.getCurrentCartId();
                String accessToken = sharedPreferencesManager.getAccessToken();

                new ApiServices().getCart(accessToken, cartId,
                        c -> {
                            Intent intent = new Intent(context, ProductDetailActivity.class);
                            intent.putExtra(PRODUCT_FOR_DETAIL, product);
                            intent.putExtra(CURRENT_USER_FOR_DETAIL, user);
                            intent.putExtra(CURRENT_CART_FOR_DETAIL, c);
                            intent.putExtra(ACCESS_TOKEN_FOR_DETAIL, accessToken);

                            intentLauncher.launch(intent);
                        },
                        e -> {
                            Snackbar.make(getView(), "Error retrieving cart from server: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        });
            }
            else {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(PRODUCT_FOR_DETAIL, product);
                intentLauncher.launch(intent);
            }
        });

        TextView emptyView = root.findViewById(R.id.empty_catalogue_view);
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        return root;
    }

    private List<Product> getCatalogue() {
        AtomicReference<List<Product>> result = new AtomicReference<>();

        IApiServices apiServices = new ApiServices();
        apiServices.getCatalogue(
                result::set,
                e -> {
                    result.set(new ArrayList<>());
                    Snackbar.make(getParentFragment().getView(), "Error obtaining catalogue: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
        );

        return result.get();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}