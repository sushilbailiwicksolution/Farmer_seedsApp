package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince on 23-11-2018.
 */

public class PhysicalDataResponse {

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
        @SerializedName("pureSeed")
        @Expose
        private String pureSeed;
        @SerializedName("weightOfSample")
        @Expose
        private String weightOfSample;
        @SerializedName("inertMatter")
        @Expose
        private String inertMatter;
        @SerializedName("otherCropSeeds")
        @Expose
        private String otherCropSeeds;
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
        @SerializedName("pureSeedR")
        @Expose
        private String pureSeedR;
        @SerializedName("weightOfSampleR")
        @Expose
        private String weightOfSampleR;
        @SerializedName("inertMatterR")
        @Expose
        private String inertMatterR;
        @SerializedName("otherCropSeedsR")
        @Expose
        private String otherCropSeedsR;
        @SerializedName("avWtOfSample")
        @Expose
        private String avWtOfSample;
        @SerializedName("avWtOfPureSeed")
        @Expose
        private String avWtOfPureSeed;
        @SerializedName("avWtOfInert")
        @Expose
        private String avWtOfInert;
        @SerializedName("avOtherCrop")
        @Expose
        private String avOtherCrop;
        @SerializedName("physicalPurity")
        @Expose
        private String physicalPurity;
        @SerializedName("otherCropPer")
        @Expose
        private String otherCropPer;
        @SerializedName("inertPer")
        @Expose
        private String inertPer;

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

        public String getPureSeed() {
            return pureSeed;
        }

        public void setPureSeed(String pureSeed) {
            this.pureSeed = pureSeed;
        }

        public String getWeightOfSample() {
            return weightOfSample;
        }

        public void setWeightOfSample(String weightOfSample) {
            this.weightOfSample = weightOfSample;
        }

        public String getInertMatter() {
            return inertMatter;
        }

        public void setInertMatter(String inertMatter) {
            this.inertMatter = inertMatter;
        }

        public String getOtherCropSeeds() {
            return otherCropSeeds;
        }

        public void setOtherCropSeeds(String otherCropSeeds) {
            this.otherCropSeeds = otherCropSeeds;
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

        public String getPureSeedR() {
            return pureSeedR;
        }

        public void setPureSeedR(String pureSeedR) {
            this.pureSeedR = pureSeedR;
        }

        public String getWeightOfSampleR() {
            return weightOfSampleR;
        }

        public void setWeightOfSampleR(String weightOfSampleR) {
            this.weightOfSampleR = weightOfSampleR;
        }

        public String getInertMatterR() {
            return inertMatterR;
        }

        public void setInertMatterR(String inertMatterR) {
            this.inertMatterR = inertMatterR;
        }

        public String getOtherCropSeedsR() {
            return otherCropSeedsR;
        }

        public void setOtherCropSeedsR(String otherCropSeedsR) {
            this.otherCropSeedsR = otherCropSeedsR;
        }

        public String getAvWtOfSample() {
            return avWtOfSample;
        }

        public void setAvWtOfSample(String avWtOfSample) {
            this.avWtOfSample = avWtOfSample;
        }

        public String getAvWtOfPureSeed() {
            return avWtOfPureSeed;
        }

        public void setAvWtOfPureSeed(String avWtOfPureSeed) {
            this.avWtOfPureSeed = avWtOfPureSeed;
        }

        public String getAvWtOfInert() {
            return avWtOfInert;
        }

        public void setAvWtOfInert(String avWtOfInert) {
            this.avWtOfInert = avWtOfInert;
        }

        public String getAvOtherCrop() {
            return avOtherCrop;
        }

        public void setAvOtherCrop(String avOtherCrop) {
            this.avOtherCrop = avOtherCrop;
        }

        public String getPhysicalPurity() {
            return physicalPurity;
        }

        public void setPhysicalPurity(String physicalPurity) {
            this.physicalPurity = physicalPurity;
        }

        public String getOtherCropPer() {
            return otherCropPer;
        }

        public void setOtherCropPer(String otherCropPer) {
            this.otherCropPer = otherCropPer;
        }

        public String getInertPer() {
            return inertPer;
        }

        public void setInertPer(String inertPer) {
            this.inertPer = inertPer;
        }

    }
}