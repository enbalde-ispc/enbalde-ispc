package com.ar.enbaldeapp.ui.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.data.LoginDataSource;
import com.ar.enbaldeapp.data.Result;
import com.ar.enbaldeapp.databinding.FragmentProfileBinding;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiError;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.ui.Utilities;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        User user = sharedPreferencesManager.loadCurrentUser();
        String accessToken = sharedPreferencesManager.getAccessToken();

        binding.profileAddressTextView.setText(R.string.prompt_address);
        binding.profileEmailTextView.setText(R.string.prompt_email);
        binding.profilePhoneTextView.setText(R.string.prompt_phone);
        binding.profileUsernameTextView.setText(R.string.prompt_username);
        binding.profileFirstNameTextView.setText(R.string.prompt_fname);
        binding.profileLastNameTextView.setText(R.string.prompt_lname);
        binding.profileOldPasswordTextView.setText("Old password");
        binding.profileNewPasswordTextView.setText("New password");
        binding.profileRepeatPasswordTextView.setText("Repeat new password");

        refreshProfileInformation(user);

        binding.profileSaveButton.setText(R.string.save);

        final Button logoutButton = binding.profileLogoutButton;
        logoutButton.setOnClickListener(this::onLogout);

        binding.profileSaveButton.setOnClickListener(v -> {
            String oldPassword = binding.profileOldPasswordEditText.getText().toString();
            String newPassword = binding.profileNewPasswordEditText.getText().toString();
            String repeatPassword = binding.profileRepeatPasswordEditText.getText().toString();
            String address = binding.profileAddressEditText.getText().toString();
            String email = binding.profileEmailEditText.getText().toString();
            String phone = binding.profilePhoneEditText.getText().toString();

            IApiServices apiServices = new ApiServices();
            apiServices.modifyUser(accessToken, user, address, email, oldPassword, newPassword, repeatPassword, phone, u -> {
                        refreshProfileInformation(u);
                        sharedPreferencesManager.saveCurrentUser(u);
                        Snackbar.make(getView(), "Information updated", Snackbar.LENGTH_SHORT).show();
                    },
                    e -> {
                        Snackbar.make(getView(), "Could not update information: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    });
        });

        return root;
    }

    private void refreshProfileInformation(User user) {
        binding.profileAddressEditText.setText(user.getAddress());
        binding.profileEmailEditText.setText(user.getEmail());
        binding.profilePhoneEditText.setText(user.getPhone());
        binding.profileUsernameEditText.setText(user.getUsername());
        binding.profileUsernameEditText.setEnabled(false);
        binding.profileFirstNameEditText.setText(user.getFirstName());
        binding.profileFirstNameEditText.setEnabled(false);
        binding.profileLastNameEditText.setText(user.getLastName());
        binding.profileLastNameEditText.setEnabled(false);
        binding.profileOldPasswordEditText.setText(user.getPassword());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onLogout(View view) {
        Context context = getContext();
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        String accessToken = sharedPreferencesManager.getAccessToken();

        IApiServices apiServices = new ApiServices();
        apiServices.logout(accessToken,
                (String message) -> doLogout(context, message),
                (ApiError e) -> doLogout(context, e.getMessage()));
    }

    private void doLogout(Context context, String message) {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.deleteCurrentUser();

        Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_loginFragment);
        Utilities.changeToolbarTitleToLogin(getActivity());
        Utilities.changeBottomMenuToLogin(getView());
        Utilities.hideCartMenuItem(getView());

        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
}