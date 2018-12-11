package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 28-11-2018.
 */

public class Report_status_Response {

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
    private List<ListData> list = null;

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

    public List<ListData> getList() {
        return list;
    }

    public void setList(List<ListData> list) {
        this.list = list;
    }

    public class ListData {

        @SerializedName("germinationTest")
        @Expose
        private String germinationTest;
        @SerializedName("moistureTest")
        @Expose
        private String moistureTest;
        @SerializedName("physicalTest")
        @Expose
        private String physicalTest;
        @SerializedName("redRiceTest")
        @Expose
        private String redRiceTest;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("labReferenceCode")
        @Expose
        private String labReferenceCode;

        @SerializedName("date")
        @Expose
        private String date;

        public String getGerminationTest() {
            return germinationTest;
        }

        public void setGerminationTest(String germinationTest) {
            this.germinationTest = germinationTest;
        }

        public String getMoistureTest() {
            return moistureTest;
        }

        public void setMoistureTest(String moistureTest) {
            this.moistureTest = moistureTest;
        }

        public String getPhysicalTest() {
            return physicalTest;
        }

        public void setPhysicalTest(String physicalTest) {
            this.physicalTest = physicalTest;
        }

        public String getRedRiceTest() {
            return redRiceTest;
        }

        public void setRedRiceTest(String redRiceTest) {
            this.redRiceTest = redRiceTest;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabReferenceCode() {
            return labReferenceCode;
        }

        public void setLabReferenceCode(String labReferenceCode) {
            this.labReferenceCode = labReferenceCode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
