package vikash.kumar.tvsautomobilesolutions.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableDataResponse {

    @SerializedName("TABLE_DATA")
    @Expose
    private String tABLEDATA;

    public String getTABLEDATA() {
        return tABLEDATA;
    }

    public void setTABLEDATA(String tABLEDATA) {
        this.tABLEDATA = tABLEDATA;
    }
}