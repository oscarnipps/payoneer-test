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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.payoneertest.R;
import com.app.payoneertest.data.Resource;
import com.app.payoneertest.utils.NetworkConnectionMonitor;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment  extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private SharedViewModel mViewModel;
    private NetworkConnectionMonitor networkConnectionMonitor;
    private ProgressBar progressBar;
    private LinearLayout inputLayout;
    private LinearLayout errorLayout;
    private MaterialButton findButton;
    private TextInputEditText inputEditText;
    private String codeInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        networkConnectionMonitor = new NetworkConnectionMonitor(requireContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        mViewModel.dataResult().observe(getViewLifecycleOwner(), dataResultObserver());



        networkConnectionMonitor.observe(getViewLifecycleOwner(), networkConnectionObserver());


        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG , "code input : " + editable.toString());
                codeInput = editable.toString();
            }
        });

        findButton.setOnClickListener(v -> {

            if (mViewModel.isValidInput(codeInput)) {

                mViewModel.findNetworkWithCode(codeInput);

                return;
            }

            Toast.makeText(requireActivity(), "invalid input", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);

        inputLayout = view.findViewById(R.id.input_layout);

        errorLayout = view.findViewById(R.id.error_layout);

        findButton = view.findViewById(R.id.find_network_btn);

        inputEditText = (TextInputEditText) view.findViewById(R.id.code_input);
    }



    @NonNull
    private Observer<Resource<String>> dataResultObserver() {
        return result -> {

            switch (result.status) {

                case LOADING:
                    errorLayout.setVisibility(View.GONE);

                    inputLayout.setVisibility(View.GONE);

                    progressBar.setVisibility(View.VISIBLE);

                    break;

                case SUCCESS:
                    errorLayout.setVisibility(View.GONE);

                    inputLayout.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);

                    break;

                case ERROR:
                    errorLayout.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);

                    inputLayout.setVisibility(View.GONE);

                    break;
            }
        };
    }

    @NonNull
    private Observer<Boolean> networkConnectionObserver() {
        return isConnected -> {
            Log.d(TAG, "is network connected : " + isConnected);

            if (isConnected) {
                mViewModel.getDataFromApi();
                return;
            }

            Toast.makeText(requireActivity(), "no network connection", Toast.LENGTH_SHORT).show();
        };
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
