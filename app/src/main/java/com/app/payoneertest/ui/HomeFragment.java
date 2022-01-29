package com.app.payoneertest.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.payoneertest.R;
import com.app.payoneertest.data.Resource;
import com.app.payoneertest.utils.NetworkConnectionMonitor;

public class HomeFragment  extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private SharedViewModel mViewModel;
    private NetworkConnectionMonitor networkConnectionMonitor;

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

        mViewModel.dataResult().observe(getViewLifecycleOwner(), dataResultObserver());

        networkConnectionMonitor.observe(getViewLifecycleOwner(), networkConnectionObserver());
    }

    @NonNull
    private Observer<Resource<String>> dataResultObserver() {
        return result -> {

            switch (result.status) {

                case LOADING:
                    //show progress bar
                    break;

                case SUCCESS:
                    //show input field ui
                    break;

                case ERROR:
                    //show toast with message
                    break;
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
