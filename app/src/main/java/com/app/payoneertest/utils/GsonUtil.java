package com.app.payoneertest.utils;

import com.app.payoneertest.data.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

    private final Gson gson;

    public GsonUtil() {
        gson = new GsonBuilder().create();
    }


}
