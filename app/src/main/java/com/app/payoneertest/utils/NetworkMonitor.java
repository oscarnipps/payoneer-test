package com.app.payoneertest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkMonitor {
    public static final String TAG = NetworkMonitor.class.getSimpleName();

    public static boolean isNetworkConnected(Context context) {
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.d(TAG, "network info : " + networkInfo);

        if (networkInfo != null) {

            switch (networkInfo.getType()) {

                case ConnectivityManager.TYPE_WIFI:
                    Log.d(TAG, "network type of wifi is connected");
                    return (true);

                case ConnectivityManager.TYPE_MOBILE:
                    Log.d(TAG, "network type of mobile is connected");
                    return (true);

                case ConnectivityManager.TYPE_VPN:
                    Log.d(TAG, "network type of vpn is connected");
                    return (true);
            }
        }

        return false;
    }
}
