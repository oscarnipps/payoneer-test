package com.app.payoneertest.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResult {
    @SerializedName("networks")
    @Expose
    private Networks networks;

    public Networks getNetworks() {
        return networks;
    }

    public void setNetworks(Networks networks) {
        this.networks = networks;
    }
}
