package com.app.payoneertest.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.payoneertest.R;
import com.app.payoneertest.data.remote.InputElement;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class DetailsFragment extends Fragment {

    public static final String TAG = DetailsFragment.class.getSimpleName();
    private SharedViewModel mViewModel;
    private List<InputElement> inputElements;
    private LinearLayout inputElementsContainer;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputElements = mViewModel.getInputElements();

        initViews(view);
    }

    private void initViews(View view) {
        inputElementsContainer = view.findViewById(R.id.input_element_container);

        emptyView = view.findViewById(R.id.empty_view);

        if (inputElements == null || inputElements.isEmpty()) {
            showEmptyInputElementView();
        } else {
            setUpInputElements();
        }

    }

    private void showEmptyInputElementView() {
        String code = mViewModel.getCurrentNetworkCode();

        emptyView.setVisibility(View.VISIBLE);

        emptyView.setText(getResources().getString(R.string.empty_elements, code));
    }

    private void setUpInputElements() {
        for (InputElement item : inputElements) {

            TextInputLayout textInputLayout = getTextInputLayout();

            TextInputEditText textInputEditText = getTextInputEditText(textInputLayout, item.getName());

            textInputLayout.addView(textInputEditText);

            inputElementsContainer.addView(textInputLayout);
        }
    }

    private TextInputEditText getTextInputEditText(TextInputLayout textInputLayout, String hint) {
        TextInputEditText textInputEditText = new TextInputEditText(textInputLayout.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        textInputEditText.setLayoutParams(params);

        textInputEditText.setHint(hint);

        return textInputEditText;
    }

    private TextInputLayout getTextInputLayout() {
        TextInputLayout textInputLayout = new TextInputLayout(requireContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(
                getMarginValue(R.dimen.text_input_margin_left),
                getMarginValue(R.dimen.text_input_margin_top),
                getMarginValue(R.dimen.text_input_margin_right),
                getMarginValue(R.dimen.text_input_margin_bottom)
        );

        textInputLayout.setLayoutParams(params);

        return textInputLayout;
    }

    private int getMarginValue(int dimenValue) {
        //get the actual dimension value , since dp value is multiplied by density of current phone
        return (int) (getResources().getDimension(dimenValue) / getResources().getDisplayMetrics().density);
    }
}
