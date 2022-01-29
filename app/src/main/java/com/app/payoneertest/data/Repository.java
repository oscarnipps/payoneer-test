package com.app.payoneertest.data;

import com.app.payoneertest.utils.NetworkUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {
    private static Repository mInstance;
    private static ExecutorService mExecutor = Executors.newCachedThreadPool();


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

            result.get();

            callBack.onResultCallBack();

        } catch (InterruptedException | MalformedURLException | ExecutionException e) {
            e.printStackTrace();

            callBack.onResultCallBack();
        }
    }


    public void stop() {
        mExecutor.shutdown();
    }
}
