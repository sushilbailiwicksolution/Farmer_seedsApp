package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices;

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.AppVersionResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.CropResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.EmployeeDetailResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.GerminationResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.MoistureDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.PhysicalDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.RedRiceDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Regionresponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Report_List_For_AddResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Report_status_Response;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveLoginResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveTestResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SeedsResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.VarityResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("getAppVersion")
    Call<AppVersionResponse> getAppVersion(@Body RequestBody body);

    @POST("getCrop")
    Call<CropResponse> getCrop(@Body RequestBody body);

    @POST("getRegion")
    Call<Regionresponse> getregion(@Body RequestBody body);

    @POST("getVariety")
    Call<VarityResponse> getVarity(@Body RequestBody body);

    @POST("getSeedClass")
    Call<SeedsResponse> getSeeds(@Body RequestBody body);

    @POST("addSample")
    Call<Regionresponse> SaveSample(@Body RequestBody body);

    @POST("getLfrAndName")
    Call<Report_List_For_AddResponse> GetListingTestResult(@Body RequestBody body);

    @POST("getTestReportData")
    Call<GerminationResponse> getGerminationTestDetail(@Body RequestBody body);

    @POST("addGerminationTest")
    Call<SaveTestResponse> saveGerminationTestDetail(@Body RequestBody body);

    @POST("updateGerminationTest")
    Call<SaveTestResponse> updateGerminationTestDetail(@Body RequestBody body);


    @POST("getTestReportData")
    Call<PhysicalDataResponse> getPhysicalTestDetail(@Body RequestBody body);


    @POST("getTestReportData")
    Call<MoistureDataResponse> getMoistureTestDetail(@Body RequestBody body);

    @POST("getTestReportData")
    Call<RedRiceDataResponse> getRedRiceTestDetail(@Body RequestBody body);
// add redrice

    @POST("addRedRiceTest")
    Call<SaveTestResponse> saveRedRiceTestDetail(@Body RequestBody body);

    @POST("updateRedRice")
    Call<SaveTestResponse> updateRedRice(@Body RequestBody body);

// ADD Moisture Test
    @POST("addMoistureRecord")
    Call<SaveTestResponse> saveMoistureTestDetail(@Body RequestBody body);

    @POST("updateMoistureTest")
    Call<SaveTestResponse> updateMoistureTestDetail(@Body RequestBody body);

    // ADD Physical test Report
    @POST("addPhysicalPurity")
    Call<SaveTestResponse> savePhysicalTestDetail(@Body RequestBody body);

    @POST("updatePhysicalPurity")
    Call<SaveTestResponse> updatePhysicalTestDetail(@Body RequestBody body);

// Login task
    @POST("loginRequest")
    Call<SaveLoginResponse> LoginTask(@Body RequestBody body);
// get Employee detail
    @POST("getEmployeeDetail")
    Call<EmployeeDetailResponse> EmployeeDetailTask(@Body RequestBody body);

    @POST("getStatusOfTest")
    Call<Report_status_Response> ReportsStatusList(@Body RequestBody body);


    /*
    @FormUrlEncoded
    @POST("admin-ajax.php")
    Call<SyllabusResponse> getSyllabusDetails(@FieldMap(encoded = true) Map<String, String> fields);
*/


}
