package com.app.payoneertest.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Networks {
    @SerializedName("applicable")
    @Expose
    private List<ApplicableNetwork> applicable = null;

    public List<ApplicableNetwork> getApplicableNetworks() {
        return applicable;
    }

    public void setApplicableNetworks(List<ApplicableNetwork> applicable) {
        this.applicable = applicable;
    }

}
