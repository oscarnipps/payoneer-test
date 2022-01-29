package com.app.payoneertest.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplicableNetwork {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("inputElements")
    @Expose
    private List<InputElement> inputElements = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InputElement> getInputElements() {
        return inputElements;
    }

    public void setInputElements(List<InputElement> inputElements) {
        this.inputElements = inputElements;
    }


}
