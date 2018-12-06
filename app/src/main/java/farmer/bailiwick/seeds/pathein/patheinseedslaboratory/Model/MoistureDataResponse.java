package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 24-11-2018.
 */

public class MoistureDataResponse {

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
        @SerializedName("labReferenceCode")
        @Expose
        private String labReferenceCode;
        @SerializedName("dateOfTest")
        @Expose
        private String dateOfTest;
        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("dateOfReceipt")
        @Expose
        private String dateOfReceipt;
        @SerializedName("cropName")
        @Expose
        private String cropName;
        @SerializedName("variety")
        @Expose
        private String variety;
        @SerializedName("seedClass")
        @Expose
        private String seedClass;
        @SerializedName("tempC")
        @Expose
        private String tempC;
        @SerializedName("rh")
        @Expose
        private String rh;
        @SerializedName("wtOfContainer")
        @Expose
        private String wtOfContainer;
        @SerializedName("wtOfSeedContainer")
        @Expose
        private String wtOfSeedContainer;
        @SerializedName("wtOfDrySeedContainer")
        @Expose
        private String wtOfDrySeedContainer;
        @SerializedName("wtOfContainerR1")
        @Expose
        private String wtOfContainerR1;
        @SerializedName("wtOfSeedContainerR1")
        @Expose
        private String wtOfSeedContainerR1;
        @SerializedName("wtOfDrySeedContainerR1")
        @Expose
        private String wtOfDrySeedContainerR1;
        @SerializedName("tempOven")
        @Expose
        private String tempOven;
        @SerializedName("meterReading1")
        @Expose
        private String meterReading1;
        @SerializedName("meterReading2")
        @Expose
        private String meterReading2;
        @SerializedName("meterReading3")
        @Expose
        private String meterReading3;
        @SerializedName("moistureContent")
        @Expose
        private String moistureContent;
        @SerializedName("inOfAnalyst")
        @Expose
        private String inOfAnalyst;
        @SerializedName("cropId")
        @Expose
        private String cropId;
        @SerializedName("seedClassId")
        @Expose
        private String seedClassId;
        @SerializedName("update")
        @Expose
        private Integer update;

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getLabReferenceCode() {
            return labReferenceCode;
        }

        public void setLabReferenceCode(String labReferenceCode) {
            this.labReferenceCode = labReferenceCode;
        }

        public String getDateOfTest() {
            return dateOfTest;
        }

        public void setDateOfTest(String dateOfTest) {
            this.dateOfTest = dateOfTest;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getDateOfReceipt() {
            return dateOfReceipt;
        }

        public void setDateOfReceipt(String dateOfReceipt) {
            this.dateOfReceipt = dateOfReceipt;
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

        public String getTempC() {
            return tempC;
        }

        public void setTempC(String tempC) {
            this.tempC = tempC;
        }

        public String getRh() {
            return rh;
        }

        public void setRh(String rh) {
            this.rh = rh;
        }

        public String getWtOfContainer() {
            return wtOfContainer;
        }

        public void setWtOfContainer(String wtOfContainer) {
            this.wtOfContainer = wtOfContainer;
        }

        public String getWtOfSeedContainer() {
            return wtOfSeedContainer;
        }

        public void setWtOfSeedContainer(String wtOfSeedContainer) {
            this.wtOfSeedContainer = wtOfSeedContainer;
        }

        public String getWtOfDrySeedContainer() {
            return wtOfDrySeedContainer;
        }

        public void setWtOfDrySeedContainer(String wtOfDrySeedContainer) {
            this.wtOfDrySeedContainer = wtOfDrySeedContainer;
        }

        public String getWtOfContainerR1() {
            return wtOfContainerR1;
        }

        public void setWtOfContainerR1(String wtOfContainerR1) {
            this.wtOfContainerR1 = wtOfContainerR1;
        }

        public String getWtOfSeedContainerR1() {
            return wtOfSeedContainerR1;
        }

        public void setWtOfSeedContainerR1(String wtOfSeedContainerR1) {
            this.wtOfSeedContainerR1 = wtOfSeedContainerR1;
        }

        public String getWtOfDrySeedContainerR1() {
            return wtOfDrySeedContainerR1;
        }

        public void setWtOfDrySeedContainerR1(String wtOfDrySeedContainerR1) {
            this.wtOfDrySeedContainerR1 = wtOfDrySeedContainerR1;
        }

        public String getTempOven() {
            return tempOven;
        }

        public void setTempOven(String tempOven) {
            this.tempOven = tempOven;
        }

        public String getMeterReading1() {
            return meterReading1;
        }

        public void setMeterReading1(String meterReading1) {
            this.meterReading1 = meterReading1;
        }

        public String getMeterReading2() {
            return meterReading2;
        }

        public void setMeterReading2(String meterReading2) {
            this.meterReading2 = meterReading2;
        }

        public String getMeterReading3() {
            return meterReading3;
        }

        public void setMeterReading3(String meterReading3) {
            this.meterReading3 = meterReading3;
        }

        public String getMoistureContent() {
            return moistureContent;
        }

        public void setMoistureContent(String moistureContent) {
            this.moistureContent = moistureContent;
        }

        public String getInOfAnalyst() {
            return inOfAnalyst;
        }

        public void setInOfAnalyst(String inOfAnalyst) {
            this.inOfAnalyst = inOfAnalyst;
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

    }
}