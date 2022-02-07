package com.app.payoneertest.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.payoneertest.R;
import com.app.payoneertest.data.remote.ApplicableNetwork;
import com.app.payoneertest.data.remote.InputElement;
import com.app.payoneertest.data.remote.ListResult;
import com.app.payoneertest.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Repository {
    public static final String TAG = Repository.class.getSimpleName();
    private static Repository mInstance;
    private ExecutorService mExecutor = Executors.newCachedThreadPool();
    private final Gson gson = new Gson();
    private String message;


    public static Repository getInstance() {
        if (mInstance == null) {

            synchronized (Repository.class) {
                mInstance = new Repository();
            }
        }
        return mInstance;
    }


    @SuppressLint("NewApi")
    public void getData(ResultCallback callBack) {
            CompletableFuture
                    .supplyAsync(this::fetchData)
                    .thenAccept(result -> handleDataResult(callBack, result));
    }

    private void handleDataResult(ResultCallback callBack, Map<String, List<InputElement>> result) {
        if (result == null) {
            //todo: use actual error message using 'message' instance field
            callBack.onResultCallBack(Resource.error("Error connecting to server"));

            return;
        }

        callBack.onResultCallBack(Resource.success(result));
    }

    @Nullable
    private Map<String, List<InputElement>> fetchData() {
        try {
            URL url = NetworkUtils.buildUrl();

            String response = NetworkUtils.getJsonResponseFromUrl(url);

            return getMappedDataFromJsonResponse(response);

        } catch (IOException e) {

            e.printStackTrace();

            message = e.getLocalizedMessage();

            return null;
        }
    }

    private Map<String, List<InputElement>> getMappedDataFromJsonResponse(String response) {
        Map<String, List<InputElement>> data = new HashMap<>();

        ListResult listResult = null;

        try {
            listResult = gson.fromJson(response, ListResult.class);

            List<ApplicableNetwork> applicableNetworks = listResult.getNetworks().getApplicableNetworks();

            for (ApplicableNetwork item : applicableNetworks) {
                data.put(
                        item.getCode(),
                        item.getInputElements()
                );
            }

            return data;

        } catch (NullPointerException | JsonSyntaxException exception) {

            exception.printStackTrace();

            return null;
        }
    }

    public void stopService() {
        mExecutor.shutdown();
        mInstance = null;
    }
}
