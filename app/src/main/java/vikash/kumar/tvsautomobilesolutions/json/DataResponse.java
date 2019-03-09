package vikash.kumar.tvsautomobilesolutions.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;

public class DataResponse {

    @SerializedName("data")
    @Expose
    private List<List<String>> data = null;

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
