package com.app.payoneertest.utils;

import android.net.Uri;

import com.app.payoneertest.data.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static URL buildUrl() throws MalformedURLException {
        Uri uri = Uri.parse(Constants.API_URL);

        URL url = new URL(uri.toString());

        return url;
    }


    public static String getJsonResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setReadTimeout(10000);

            urlConnection.setConnectTimeout(15000);

            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            }

            return null;

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
