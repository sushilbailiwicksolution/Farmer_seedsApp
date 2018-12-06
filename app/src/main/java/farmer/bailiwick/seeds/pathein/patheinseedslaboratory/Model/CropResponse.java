package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 16-11-2018.
 */

public class CropResponse{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("list")
    @Expose
    private List<CropData> list = null;
    @SerializedName("listSeedReport")
    @Expose
    private Object listSeedReport;
    @SerializedName("listAnalysisReport")
    @Expose
    private Object listAnalysisReport;
    @SerializedName("listSeedRecord")
    @Expose
    private Object listSeedRecord;
    @SerializedName("listMoistureRecord")
    @Expose
    private Object listMoistureRecord;
    @SerializedName("listPhysicalRecord")
    @Expose
    private Object listPhysicalRecord;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<CropData> getList() {
        return list;
    }

    public void setList(List<CropData> list) {
        this.list = list;
    }

    public Object getListSeedReport() {
        return listSeedReport;
    }

    public void setListSeedReport(Object listSeedReport) {
        this.listSeedReport = listSeedReport;
    }

    public Object getListAnalysisReport() {
        return listAnalysisReport;
    }

    public void setListAnalysisReport(Object listAnalysisReport) {
        this.listAnalysisReport = listAnalysisReport;
    }

    public Object getListSeedRecord() {
        return listSeedRecord;
    }

    public void setListSeedRecord(Object listSeedRecord) {
        this.listSeedRecord = listSeedRecord;
    }

    public Object getListMoistureRecord() {
        return listMoistureRecord;
    }

    public void setListMoistureRecord(Object listMoistureRecord) {
        this.listMoistureRecord = listMoistureRecord;
    }

    public Object getListPhysicalRecord() {
        return listPhysicalRecord;
    }

    public void setListPhysicalRecord(Object listPhysicalRecord) {
        this.listPhysicalRecord = listPhysicalRecord;
    }
    public class CropData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("crop")
        @Expose
        private String crop;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCrop() {
            return crop;
        }

        public void setCrop(String crop) {
            this.crop = crop;
        }

    }
}