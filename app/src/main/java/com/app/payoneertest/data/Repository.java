package com.app.payoneertest.data;

import android.util.Log;

import com.app.payoneertest.data.remote.ApplicableNetwork;
import com.app.payoneertest.data.remote.InputElement;
import com.app.payoneertest.data.remote.ListResult;
import com.app.payoneertest.utils.GsonUtil;
import com.app.payoneertest.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {
    public static final String TAG = Repository.class.getSimpleName();
    private static Repository mInstance;
    private static final ExecutorService mExecutor = Executors.newCachedThreadPool();
    private final Gson gson = new Gson();


    public static Repository getInstance() {
        if (mInstance == null) {

            synchronized (Repository.class) {

                mInstance = new Repository();
            }
        }
        return mInstance;
    }


    public void getData(ResultCallback callBack) {
        try {
            URL url = NetworkUtils.buildUrl();

            Future<String> result = mExecutor.submit(() -> NetworkUtils.getJsonResponseFromUrl(url));

            String response = result.get();

            Log.d(TAG, "response from api : " + response);

            Map<String, List<InputElement>> mapItems = getMappedDataFromJsonResponse(response);

            callBack.onResultCallBack(Resource.success(mapItems));

        } catch (InterruptedException | MalformedURLException | ExecutionException e) {

            e.printStackTrace();

            callBack.onResultCallBack(Resource.error(e.getLocalizedMessage()));
        }
    }

    private Map<String, List<InputElement>> getMappedDataFromJsonResponse(String response) {
        Map<String, List<InputElement>> data = new HashMap<>();

        ListResult listResult = null;

        try {
            listResult = gson.fromJson(response, ListResult.class);

            List<ApplicableNetwork> applicableNetworks = listResult.getNetworks().getApplicableNetworks();

            Log.d(TAG, "applicable size : " + applicableNetworks.size());

            for (ApplicableNetwork item : applicableNetworks) {
                Log.d(TAG, "code : " + item.getCode());

                Log.d(TAG, "input element size : " + (item.getInputElements() == null ? 0 : item.getInputElements().size()));

                data.put(
                        item.getCode(),
                        item.getInputElements() == null ? Collections.emptyList() : item.getInputElements()
                );

            }

            return data;

        } catch (NullPointerException | JsonSyntaxException exception) {

            exception.printStackTrace();

            return null;
        }
    }


    public void stop() {
        mExecutor.shutdown();
    }
}
