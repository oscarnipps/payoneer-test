package com.app.payoneertest.data;

import com.app.payoneertest.data.remote.InputElement;

import java.util.List;
import java.util.Map;

public interface ResultCallback {

     void onResultCallBack(Resource<Map<String, List<InputElement>>> resource);
}
