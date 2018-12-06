package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 05-11-2018.
 */

public class AppVersionResponse {
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
    @SerializedName("errormsg")
    @Expose
    private Object errormsg;

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

    public Object getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(Object errormsg) {
        this.errormsg = errormsg;
    }

   public class ListData{


        @SerializedName("appversion")
        @Expose
        private String appversion;
        @SerializedName("employeeStatus")
        @Expose
        private Integer employeeStatus;
        @SerializedName("moduleName")
        @Expose
        private String moduleName;
        @SerializedName("lastUpdateDateAndTine")
        @Expose
        private String lastUpdateDateAndTine;
        @SerializedName("versionCode")
        @Expose
        private String versionCode;

        public String getAppversion() {
            return appversion;
        }

        public void setAppversion(String appversion) {
            this.appversion = appversion;
        }

        public Integer getEmployeeStatus() {
            return employeeStatus;
        }

        public void setEmployeeStatus(Integer employeeStatus) {
            this.employeeStatus = employeeStatus;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public String getLastUpdateDateAndTine() {
            return lastUpdateDateAndTine;
        }

        public void setLastUpdateDateAndTine(String lastUpdateDateAndTine) {
            this.lastUpdateDateAndTine = lastUpdateDateAndTine;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

    }
}
