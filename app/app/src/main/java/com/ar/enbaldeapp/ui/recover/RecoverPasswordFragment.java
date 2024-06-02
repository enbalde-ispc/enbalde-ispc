package com.ar.enbaldeapp.ui.recover;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.ui.Utilities;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecoverPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecoverPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText emailEditText;
    private EditText tokenEditText;
    private EditText newPasswordEditText;

    public RecoverPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecoverPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecoverPasswordFragment newInstance(String param1, String param2) {
        RecoverPasswordFragment fragment = new RecoverPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recover_password, container, false);

        TextView textView = view.findViewById(R.id.recoverAlreadyAccountTextView);
        textView.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_recoverFragment_to_loginFragment);
            Utilities.changeToolbarTitleToLogin(getActivity());
        });

        emailEditText = view.findViewById(R.id.recoverEmailEditText);
        tokenEditText = view.findViewById(R.id.recoverTokenEditText);
        newPasswordEditText = view.findViewById(R.id.recoverPasswordEditText);

        Button button = view.findViewById(R.id.recoverButtonEmail);
        button.setOnClickListener(v -> {
            new ApiServices().sendRecoveryToken(emailEditText.getText().toString(),
                    r -> {
                        Snackbar.make (getView(), r != null && r.toUpperCase().equals("OK")
                                ? "Email sent successfully!"
                                : "Error sending email from server", Snackbar.LENGTH_LONG).show();
                    },
                    e -> {
                        Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                    });
        });

        button = view.findViewById(R.id.recoverResetPasswordButton);
        button.setOnClickListener(v -> {
            new ApiServices().resetPassword(tokenEditText.getText().toString(), newPasswordEditText.getText().toString(),
                    r -> {
                        if (r != null && r.toUpperCase().equals("OK")) {
                            Snackbar.make(getView(), "Clave cambiada con éxito, por favor ingrese con su nueva clave.", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Snackbar.make(getView(), r, Snackbar.LENGTH_SHORT).show();
                        }
                    },
                    e -> {
                        Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    });
        });

        Utilities.changeToolbarTitleToRecovery(getActivity());
        return view;
    }
}