package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Prince on 21-11-2018.
 */

public class GerminationResponse   {

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
    private List<GerminationReport> list = null;

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

    public List<GerminationReport> getList() {
        return list;
    }

    public void setList(List<GerminationReport> list) {
        this.list = list;
    }
    public class GerminationReport {

        @SerializedName("issueDate")
        @Expose
        private String issueDate;
        @SerializedName("labReferenceCode")
        @Expose
        private String labReferenceCode;
        @SerializedName("dateOfReceipt")
        @Expose
        private String dateOfReceipt;
        @SerializedName("dateOfPutting")
        @Expose
        private String dateOfPutting;
        @SerializedName("crop")
        @Expose
        private String crop;
        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("variety")
        @Expose
        private String variety;
        @SerializedName("seedClass")
        @Expose
        private String seedClass;
        @SerializedName("dateOfPutting1")
        @Expose
        private String dateOfPutting1;
        @SerializedName("totalSeeds")
        @Expose
        private String totalSeeds;
        @SerializedName("avNormalSeedlings")
        @Expose
        private String avNormalSeedlings;
        @SerializedName("avAbnormalSeedllings")
        @Expose
        private String avAbnormalSeedllings;
        @SerializedName("avDeadSeedlings")
        @Expose
        private String avDeadSeedlings;
        @SerializedName("avHardFUS")
        @Expose
        private String avHardFUS;
        @SerializedName("finalGermination")
        @Expose
        private String finalGermination;
        @SerializedName("dateOfReport")
        @Expose
        private String dateOfReport;
        @SerializedName("initialsOfAnalyst")
        @Expose
        private String initialsOfAnalyst;
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

        public String getDateOfReceipt() {
            return dateOfReceipt;
        }

        public void setDateOfReceipt(String dateOfReceipt) {
            this.dateOfReceipt = dateOfReceipt;
        }

        public String getDateOfPutting() {
            return dateOfPutting;
        }

        public void setDateOfPutting(String dateOfPutting) {
            this.dateOfPutting = dateOfPutting;
        }

        public String getCrop() {
            return crop;
        }

        public void setCrop(String crop) {
            this.crop = crop;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
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

        public String getDateOfPutting1() {
            return dateOfPutting1;
        }

        public void setDateOfPutting1(String dateOfPutting1) {
            this.dateOfPutting1 = dateOfPutting1;
        }

        public String getTotalSeeds() {
            return totalSeeds;
        }

        public void setTotalSeeds(String totalSeeds) {
            this.totalSeeds = totalSeeds;
        }

        public String getAvNormalSeedlings() {
            return avNormalSeedlings;
        }

        public void setAvNormalSeedlings(String avNormalSeedlings) {
            this.avNormalSeedlings = avNormalSeedlings;
        }

        public String getAvAbnormalSeedllings() {
            return avAbnormalSeedllings;
        }

        public void setAvAbnormalSeedllings(String avAbnormalSeedllings) {
            this.avAbnormalSeedllings = avAbnormalSeedllings;
        }

        public String getAvDeadSeedlings() {
            return avDeadSeedlings;
        }

        public void setAvDeadSeedlings(String avDeadSeedlings) {
            this.avDeadSeedlings = avDeadSeedlings;
        }

        public String getAvHardFUS() {
            return avHardFUS;
        }

        public void setAvHardFUS(String avHardFUS) {
            this.avHardFUS = avHardFUS;
        }

        public String getFinalGermination() {
            return finalGermination;
        }

        public void setFinalGermination(String finalGermination) {
            this.finalGermination = finalGermination;
        }

        public String getDateOfReport() {
            return dateOfReport;
        }

        public void setDateOfReport(String dateOfReport) {
            this.dateOfReport = dateOfReport;
        }

        public String getInitialsOfAnalyst() {
            return initialsOfAnalyst;
        }

        public void setInitialsOfAnalyst(String initialsOfAnalyst) {
            this.initialsOfAnalyst = initialsOfAnalyst;
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