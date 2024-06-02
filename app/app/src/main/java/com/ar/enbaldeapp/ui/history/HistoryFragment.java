package com.ar.enbaldeapp.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.databinding.FragmentHistoryBinding;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.services.adapters.HistoryAdapter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        String accessToken = sharedPreferencesManager.getAccessToken();
        User user = sharedPreferencesManager.loadCurrentUser();

        IApiServices apiServices = new ApiServices();
        AtomicReference<HistoryViewModel> historyViewModel = new AtomicReference<>();
        apiServices.getHistory(accessToken, user, c -> {
                    historyViewModel.set(new HistoryViewModel(c));
                },
                e -> {
                    historyViewModel.set(new HistoryViewModel(new ArrayList<>()));
                });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), historyViewModel.get(), accessToken);
        recyclerView.setAdapter(adapter);

        return root;
    }
}