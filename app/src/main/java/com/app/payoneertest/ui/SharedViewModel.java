package com.app.payoneertest.ui;

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
    public static final String TAG = SharedViewModel.class.getSimpleName();
    private MutableLiveData<Resource<String>> result = new MutableLiveData<>();
    private Repository repo = Repository.getInstance();
    private Map<String, List<InputElement>> resultMap = new HashMap<>();
    private List<InputElement> inputElements = new ArrayList<>();

    public void getDataFromApi() {
        result.setValue(Resource.loading());

        repo.getData( mapItems -> {

            if (mapItems.status == Resource.Status.SUCCESS) {

                result.setValue(Resource.success(null));

                resultMap = mapItems.data;

                return;
            }

            result.setValue(Resource.error(mapItems.message));
        });

    }

    public LiveData<Resource<String>> dataResult() {
        return result;
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        repo.stop();
    }
}
