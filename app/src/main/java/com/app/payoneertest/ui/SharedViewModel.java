package com.app.payoneertest.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.payoneertest.data.Repository;
import com.app.payoneertest.data.Resource;
import com.app.payoneertest.data.remote.InputElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedViewModel extends ViewModel {
    private static final String TAG = SharedViewModel.class.getSimpleName();
    private MutableLiveData<Resource<String>> result = new MutableLiveData<>();
    private final Repository repo = Repository.getInstance();
    private Map<String, List<InputElement>> resultMap;
    private List<InputElement> codeInputElements = new ArrayList<>();
    private String currentNetworkCode;


    public void getDataFromApi() {
        if (resultMap == null) {

            result.setValue(Resource.loading());

            repo.getData(mapItems -> {

                if (mapItems.status == Resource.Status.SUCCESS) {

                    resultMap = mapItems.data;

                    result.postValue(Resource.success(null));

                    return;
                }

                result.postValue(Resource.error(mapItems.message));
            });
        }
    }

    public LiveData<Resource<String>> dataResult() {
        return result;
    }

    public boolean isValidInput(String codeValue) {
        return codeValue != null && !codeValue.isEmpty();
    }

    public boolean isCodeAvailable(String code) {
        String key = code.toUpperCase();
        return resultMap.containsKey(key);
    }

    public List<InputElement> getInputElements() {
        return codeInputElements;
    }

    public void setInputElementsForNetworkCode(String codeInput) {
        String key = codeInput.toUpperCase();

        currentNetworkCode = key;

        codeInputElements = resultMap.get(key);
    }

    public String getCurrentNetworkCode() {
        return currentNetworkCode;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.stopService();
    }

    public void retryDataCall() {
        resultMap = null;
        getDataFromApi();
    }
}
