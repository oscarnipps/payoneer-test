package com.app.payoneertest.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

public class NetworkConnectionMonitor extends LiveData<Boolean> {

    public static final String TAG = NetworkConnectionMonitor.class.getSimpleName();
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private Context context;
    private NetworkMonitorReceiver networkMonitorReceiver = new NetworkMonitorReceiver();


    public NetworkConnectionMonitor(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpNetworkMonitor();
            return;
        }

        context.registerReceiver(
                networkMonitorReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        );
    }

    private void updateNetworkMonitor() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.d(TAG, "network info : " + networkInfo);

        if (networkInfo != null) {

            switch (networkInfo.getType()) {

                case ConnectivityManager.TYPE_WIFI:
                    Log.d(TAG, "network info wifi is connected");
                    postValue(true);
                    break;

                case ConnectivityManager.TYPE_MOBILE:
                    Log.d(TAG, "network info mobile is connected");
                    postValue(true);
                    break;

                case ConnectivityManager.TYPE_VPN:
                    Log.d(TAG, "network info vpn is connected");
                    postValue(true);
                    break;
            }

            return;
        }

        postValue(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpNetworkMonitor() {
        networkCallback = getNetworkCallback();

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ConnectivityManager.NetworkCallback getNetworkCallback() {

        return new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull android.net.Network network) {
                super.onAvailable(network);
                Log.d(TAG, "network available...");
                postValue(true);
            }

            @Override
            public void onLost(@NonNull android.net.Network network) {
                super.onLost(network);
                Log.d(TAG, "network lost...");
                postValue(false);
            }

            @Override
            public void onCapabilitiesChanged(@NonNull android.net.Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                //Log.d(TAG, "capability changed : " + networkCapabilities.toString());
            }
        };
    }


    @Override
    protected void onInactive() {
        super.onInactive();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
            networkCallback = null;
            return;
        }

        context.unregisterReceiver(networkMonitorReceiver);

        context = null;
    }

    private  class NetworkMonitorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNetworkMonitor();
        }
    }

}
