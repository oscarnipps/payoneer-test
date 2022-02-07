package com.app.payoneertest.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.app.payoneertest.data.Resource;
import com.app.payoneertest.data.remote.InputElement;
import com.app.payoneertest.utils.LiveDataTestUtil;
import com.google.common.truth.Truth;
import com.google.common.truth.Truth.*;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class SharedViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public SharedViewModel sharedViewModel = new SharedViewModel();

    private Map<String, List<InputElement>> resultMap;

    @Before
    public void setUp() {
        List<InputElement> inputElements = new ArrayList<>();

        inputElements.add( new InputElement("number","numeric"));

        inputElements.add( new InputElement("expiryMonth","integer"));

        resultMap = new HashMap<>();

        resultMap.put("VISA", inputElements);

        resultMap.put("PAYPAL", inputElements);
    }

    @Test
    public void valid_input_returns_true() {
        String input = "Visa";

        boolean actual = sharedViewModel.isValidInput(input);

        Truth.assertThat(actual).isEqualTo(true);
    }

    @Test
    public void invalid_input_returns_false() {
        String input  = null;

        boolean actual = sharedViewModel.isValidInput(input);

        Truth.assertThat(actual).isEqualTo(false);

        input = "";

        actual = sharedViewModel.isValidInput(input);

        Truth.assertThat(actual).isEqualTo(false);
    }

}
