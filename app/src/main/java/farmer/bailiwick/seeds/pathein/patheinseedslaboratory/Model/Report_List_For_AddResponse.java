package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 20-11-2018.
 */

public class Report_List_For_AddResponse {

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
    private List<ListReoprtData> list = null;

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

    public List<ListReoprtData> getList() {
        return list;
    }

    public void setList(List<ListReoprtData> list) {
        this.list = list;
    }

    public class ListReoprtData {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("labReferenceNumber")
        @Expose
        private String labReferenceNumber;
        @SerializedName("date")
        @Expose
        private String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabReferenceNumber() {
            return labReferenceNumber;
        }

        public void setLabReferenceNumber(String labReferenceNumber) {
            this.labReferenceNumber = labReferenceNumber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}