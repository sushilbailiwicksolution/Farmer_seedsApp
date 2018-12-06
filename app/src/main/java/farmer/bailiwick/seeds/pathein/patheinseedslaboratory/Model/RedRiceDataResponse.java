package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 24-11-2018.
 */

public class RedRiceDataResponse {

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

        @SerializedName("issueDate")
        @Expose
        private String issueDate;
        @SerializedName("labRefrenceNumber")
        @Expose
        private String labRefrenceNumber;
        @SerializedName("dateOfReceipt")
        @Expose
        private String dateOfReceipt;
        @SerializedName("dateOfTest")
        @Expose
        private String dateOfTest;
        @SerializedName("cropName")
        @Expose
        private String cropName;
        @SerializedName("variety")
        @Expose
        private String variety;
        @SerializedName("seedClass")
        @Expose
        private String seedClass;
        @SerializedName("weightOfWorkingSample")
        @Expose
        private String weightOfWorkingSample;
        @SerializedName("numberRedRiceWorking")
        @Expose
        private String numberRedRiceWorking;
        @SerializedName("numberRedRicePer500")
        @Expose
        private String numberRedRicePer500;
        @SerializedName("seedStandard")
        @Expose
        private String seedStandard;
        @SerializedName("acceptable")
        @Expose
        private String acceptable;
        @SerializedName("cropId")
        @Expose
        private String cropId;
        @SerializedName("seedClassId")
        @Expose
        private String seedClassId;
        @SerializedName("update")
        @Expose
        private Integer update;
        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("analystSignature")
        @Expose
        private String analystSignature;
        @SerializedName("weightOfWorkingSampleR")
        @Expose
        private String weightOfWorkingSampleR;
        @SerializedName("numberRedRiceWorkingR")
        @Expose
        private String numberRedRiceWorkingR;

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getLabRefrenceNumber() {
            return labRefrenceNumber;
        }

        public void setLabRefrenceNumber(String labRefrenceNumber) {
            this.labRefrenceNumber = labRefrenceNumber;
        }

        public String getDateOfReceipt() {
            return dateOfReceipt;
        }

        public void setDateOfReceipt(String dateOfReceipt) {
            this.dateOfReceipt = dateOfReceipt;
        }

        public String getDateOfTest() {
            return dateOfTest;
        }

        public void setDateOfTest(String dateOfTest) {
            this.dateOfTest = dateOfTest;
        }

        public String getCropName() {
            return cropName;
        }

        public void setCropName(String cropName) {
            this.cropName = cropName;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getSeedClass() {
            return seedClass;
        }

        public void setSeedClass(String seedClass) {
            this.seedClass = seedClass;
        }

        public String getWeightOfWorkingSample() {
            return weightOfWorkingSample;
        }

        public void setWeightOfWorkingSample(String weightOfWorkingSample) {
            this.weightOfWorkingSample = weightOfWorkingSample;
        }

        public String getNumberRedRiceWorking() {
            return numberRedRiceWorking;
        }

        public void setNumberRedRiceWorking(String numberRedRiceWorking) {
            this.numberRedRiceWorking = numberRedRiceWorking;
        }

        public String getNumberRedRicePer500() {
            return numberRedRicePer500;
        }

        public void setNumberRedRicePer500(String numberRedRicePer500) {
            this.numberRedRicePer500 = numberRedRicePer500;
        }

        public String getSeedStandard() {
            return seedStandard;
        }

        public void setSeedStandard(String seedStandard) {
            this.seedStandard = seedStandard;
        }

        public String getAcceptable() {
            return acceptable;
        }

        public void setAcceptable(String acceptable) {
            this.acceptable = acceptable;
        }

        public String getCropId() {
            return cropId;
        }

        public void setCropId(String cropId) {
            this.cropId = cropId;
        }

        public String getSeedClassId() {
            return seedClassId;
        }

        public void setSeedClassId(String seedClassId) {
            this.seedClassId = seedClassId;
        }

        public Integer getUpdate() {
            return update;
        }

        public void setUpdate(Integer update) {
            this.update = update;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getAnalystSignature() {
            return analystSignature;
        }

        public void setAnalystSignature(String analystSignature) {
            this.analystSignature = analystSignature;
        }

        public String getWeightOfWorkingSampleR() {
            return weightOfWorkingSampleR;
        }

        public void setWeightOfWorkingSampleR(String weightOfWorkingSampleR) {
            this.weightOfWorkingSampleR = weightOfWorkingSampleR;
        }

        public String getNumberRedRiceWorkingR() {
            return numberRedRiceWorkingR;
        }

        public void setNumberRedRiceWorkingR(String numberRedRiceWorkingR) {
            this.numberRedRiceWorkingR = numberRedRiceWorkingR;
        }

    }
}