package com.app.payoneertest.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.payoneertest.R;
import com.app.payoneertest.data.Resource;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private SharedViewModel viewModel;
    private ProgressBar progressBar;
    private LinearLayout inputLayout;
    private ConstraintLayout inputLayoutContainer;
    private MaterialButton findButton;
    private TextInputEditText inputEditText;
    private String codeInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        initViewListeners();

        viewModel.dataResult().observe(getViewLifecycleOwner(), dataResultObserver());
    }

    private void initViewListeners() {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                codeInput = editable.toString();
            }
        });

        findButton.setOnClickListener(v -> {
            if (viewModel.isValidInput(codeInput)) {
                findNetworkWithCode();
                return;
            }

            Toast.makeText(v.getContext(), getString(R.string.invalid_code_input), Toast.LENGTH_SHORT).show();
        });
    }

    private void findNetworkWithCode() {
        if (viewModel.isCodeAvailable(codeInput)) {

            viewModel.setInputElementsForNetworkCode(codeInput);

            navigateToSecondFragment();

            return;
        }

        Toast.makeText(requireContext(), getString(R.string.code_not_available), Toast.LENGTH_SHORT).show();
    }

    private void navigateToSecondFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, DetailsFragment.class, null);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);

        inputLayout = view.findViewById(R.id.input_layout);

        inputLayoutContainer = view.findViewById(R.id.input_layout_container);

        findButton = view.findViewById(R.id.find_network_btn);

        inputEditText = view.findViewById(R.id.code_input);
    }


    @NonNull
    private Observer<Resource<String>> dataResultObserver() {
        return result -> {

            switch (result.status) {

                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);

                    inputLayout.setVisibility(View.GONE);

                    break;

                case SUCCESS:
                    inputLayout.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);

                    break;

                case ERROR:
                    showSnackBar(result.message);

                    progressBar.setVisibility(View.GONE);

                    break;
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getDataFromApi();
    }

    private void showSnackBar(String message) {
        Snackbar.make(inputLayoutContainer, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> viewModel.retryDataCall())
                .show();
    }


}
