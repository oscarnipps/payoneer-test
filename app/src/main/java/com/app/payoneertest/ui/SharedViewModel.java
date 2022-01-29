package com.app.payoneertest.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.payoneertest.data.Repository;
import com.app.payoneertest.data.Resource;
import com.app.payoneertest.data.remote.ApplicableNetwork;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Resource<ApplicableNetwork>> data = new MutableLiveData<>();

    public void getDataFromApi() {
        data.setValue(Resource.loading());

        Repository repo = Repository.getInstance();

        repo.getData(()-> {
            data.setValue(Resource.success(null));
        });

    }

    public LiveData<Resource<ApplicableNetwork>> dataResult() {
        return data;
    }

}
