package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.CustomDialog.SweetAlertDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.GerminationResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveTestResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CheckConnectivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GerminationTest extends RootActivity implements DatePickerDialog.OnDateSetListener {
    Context mContext;

    LinearLayout lnt_issue_date, lnt_date_receipt, int_date_putting, int_date_putting2, int_date_report;
    TextView txt_date_receipt, txt_date_putting, txt_date_report, txt_crop_Name, txt_variety;

    Button btn_submit;
    EditText edt_av_abnormal_seed, edt_av_dead_seed, edt_av_hard_fus, edt_final_germination, edt_initial_analyst, edt_av_normal_seed, edt_totel_seeds, edt_seed_class, edt_lab_ref, edt_variety;
    Shimmer shimmer;
    ShimmerTextView txt_header;

    Spinner spnr_method;
    ArrayList<String> ListMethod;
    ArrayAdapter Spinner_method_adapter;

    DatePickerDialog datePickerDialog;

    android.app.AlertDialog alertDialog;


    enum DateType {DR, DI, DP, DP2, DOR}

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    DateType DT;

    String operationType = "", labRef_No = "";
    String crop_id, seed_id;

    int updatevalue = -1;

    public static boolean isDateValid(String date) {
        final String DATE_FORMAT = "dd-MM-yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_germination_test);
        mContext = GerminationTest.this;
        createIDS();
        setSpinnerData();
        clickevent();
        getDetails();
    }

    private void setSpinnerData() {

        ListMethod = new ArrayList<>();
        ListMethod.add("Select Method");
        ListMethod.add("TP");
        ListMethod.add("BP");
        ListMethod.add("SAND");

        Spinner_method_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, ListMethod);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_method_adapter.setDropDownViewResource(R.layout.spinner_layout_white);

        spnr_method.setAdapter(Spinner_method_adapter);

    }

    private void getDetails() {


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            operationType = "";
        } else {
            operationType = extras.getString("operationType");
            labRef_No = extras.getString("Labrefrence");
        }
        if (!operationType.equalsIgnoreCase("")) {
            getLabegermination_data(operationType, labRef_No);


        } else {
            Toast.makeText(mContext, "List id is Not Get", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLabegermination_data(String operationType, String labRef_no) {


        JSONObject js = new JSONObject();

        try {
            js.put("operationType", operationType);
            js.put("labReferenceNumber", labRef_no);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Param ", "Param : " + js.toString());
        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().getGerminationTestDetail(body).enqueue(new Callback<GerminationResponse>() {
            @Override
            public void onResponse(Call<GerminationResponse> call, Response<GerminationResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                if (new Gson().toJson(response) != null) {
                    Log.e("my Response  : ", "Rajesh ppppp :  " + new Gson().toJson(response));
                    alertDialog.dismiss();
                    Log.e("my Response  : ", response.body().getMessage().toString());
                    if (response.body().getStatusCode() == 0) {
                        JSONObject js = null;
                        setBasedetail(response);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<GerminationResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });

    }

    private void setBasedetail(Response<GerminationResponse> response) {
        // Common Value
        edt_lab_ref.setText(response.body().getList().get(0).getLabReferenceCode());
        txt_crop_Name.setText(response.body().getList().get(0).getCrop());
        edt_seed_class.setText(response.body().getList().get(0).getSeedClass());
        txt_variety.setText(response.body().getList().get(0).getVariety());
        txt_date_receipt.setText(getfillValue(response.body().getList().get(0).getDateOfReceipt(), ""));
        seed_id = response.body().getList().get(0).getSeedClassId();
        crop_id = response.body().getList().get(0).getCropId();
        updatevalue = response.body().getList().get(0).getUpdate();
        if (updatevalue == 1) {

            edt_av_dead_seed.setText(getfillValue(response.body().getList().get(0).getAvDeadSeedlings(), ""));

            txt_date_putting.setText(getfillValue(response.body().getList().get(0).getDateOfPutting(), ""));
            String method_value = getfillValue(response.body().getList().get(0).getMethod(), "");
            setSelectedMethod(method_value);
            edt_totel_seeds.setText(getfillValue(response.body().getList().get(0).getTotalSeeds(), ""));
            edt_av_normal_seed.setText(getfillValue(response.body().getList().get(0).getAvNormalSeedlings(), ""));
            edt_av_abnormal_seed.setText(getfillValue(response.body().getList().get(0).getAvAbnormalSeedllings(), ""));
            edt_av_dead_seed.setText(getfillValue(response.body().getList().get(0).getAvDeadSeedlings(), ""));
            edt_av_hard_fus.setText(getfillValue(response.body().getList().get(0).getAvHardFUS(), ""));
            edt_final_germination.setText(getfillValue(response.body().getList().get(0).getFinalGermination(), ""));
            txt_date_report.setText(getfillValue(response.body().getList().get(0).getDateOfReport(), ""));
            edt_initial_analyst.setText(getfillValue(response.body().getList().get(0).getInitialsOfAnalyst(), ""));
        }


    }

    private void setSelectedMethod(String method_value) {
        if (!method_value.equalsIgnoreCase("")) {
            int spinnerPosition = Spinner_method_adapter.getPosition(method_value);
            spnr_method.setSelection(spinnerPosition);
        }
    }

    private String getfillValue(String strValue, String defaultValue) {
        if (strValue == null) {
            return defaultValue;
        } else {
            return strValue;
        }

    }


    private void clickevent() {
        lnt_issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DI;
                datePickerDialog.show();

            }
        });
        lnt_date_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DR;
                datePickerDialog.show();
            }
        });
        int_date_putting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DP;
                datePickerDialog.show();
            }
        });
        int_date_putting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DP2;
                datePickerDialog.show();
            }
        });
        int_date_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DOR;
                datePickerDialog.show();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();

            }
        });
    }

    private void checkValidation() {
        if (edt_lab_ref.getText().toString().trim().equalsIgnoreCase("")) {
            Log.e("Lab", edt_lab_ref.getText().toString().trim());
            edt_lab_ref.setError("Requried");
            return;

        } else if (txt_date_receipt.getText().toString().equalsIgnoreCase("")) {
            txt_date_receipt.setError("Please Select Date");
            return;
        } else if (txt_date_putting.getText().toString().equalsIgnoreCase("")) {
            txt_date_putting.setError("Please Select Date");
            return;
        } else if (spnr_method.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Method", Toast.LENGTH_LONG).show();
            return;


        } else if (edt_seed_class.getText().toString().trim().equalsIgnoreCase("")) {
            edt_seed_class.setError("Requried");
            return;


        } else if (edt_totel_seeds.getText().toString().trim().equalsIgnoreCase("")) {
            edt_totel_seeds.setError("Requried");
            return;


        } else if (edt_av_normal_seed.getText().toString().trim().equalsIgnoreCase("")) {
            edt_av_normal_seed.setError("Requried");
            return;


        } else if (edt_av_abnormal_seed.getText().toString().trim().equalsIgnoreCase("")) {
            edt_av_abnormal_seed.setError("Requried");
            return;


        } else if (edt_av_dead_seed.getText().toString().trim().equalsIgnoreCase("")) {
            edt_av_dead_seed.setError("Requried");
            return;


        } else if (edt_av_hard_fus.getText().toString().trim().equalsIgnoreCase("")) {
            edt_av_hard_fus.setError("Requried");
            return;


        } else if (edt_final_germination.getText().toString().trim().equalsIgnoreCase("")) {
            edt_final_germination.setError("Requried");
            return;


        } else if (txt_date_report.getText().toString().equalsIgnoreCase("")) {
            txt_date_report.setError("Please Select Date");
            return;
        } else if (edt_initial_analyst.getText().toString().trim().equalsIgnoreCase("")) {
            edt_initial_analyst.setError("Requried");
            return;


        } else {
            String labReferenceCode, dateOfReceipt, dateOfPutting, method, variety, seedClass, dateOfPutting1, totalSeeds, avNormalSeedlings, avAbnormalSeedllings, avDeadSeedlings, avHardFUS, finalGermination, dateOfReport, initialsOfAnalyst;
            labReferenceCode = edt_lab_ref.getText().toString().trim();
            dateOfReceipt = txt_date_receipt.getText().toString().trim();
            dateOfPutting = txt_date_putting.getText().toString().trim();
            method = spnr_method.getSelectedItem().toString();
            variety = txt_variety.getText().toString().trim();
            seedClass = edt_seed_class.getText().toString().trim();
            totalSeeds = edt_totel_seeds.getText().toString().trim();
            avNormalSeedlings = edt_av_normal_seed.getText().toString().trim();
            avAbnormalSeedllings = edt_av_abnormal_seed.getText().toString().trim();
            avDeadSeedlings = edt_av_dead_seed.getText().toString().trim();
            avHardFUS = edt_av_hard_fus.getText().toString().trim();
            finalGermination = edt_final_germination.getText().toString().trim();
            dateOfReport = txt_date_report.getText().toString().trim();
            initialsOfAnalyst = edt_initial_analyst.getText().toString().trim();

            if (CheckConnectivity.isConnected(mContext)) {
                if (updatevalue == 0) {
                    SaveDatage(labReferenceCode, dateOfReceipt, dateOfPutting, method, variety, seedClass, totalSeeds, avNormalSeedlings, avAbnormalSeedllings, avDeadSeedlings, avHardFUS, finalGermination, dateOfReport, initialsOfAnalyst);

                } else if (updatevalue == 1) {
                    Log.e("i have to update", "i have to update");
                    UpdateData(labReferenceCode, dateOfReceipt, dateOfPutting, method, variety, seedClass, totalSeeds, avNormalSeedlings, avAbnormalSeedllings, avDeadSeedlings, avHardFUS, finalGermination, dateOfReport, initialsOfAnalyst);


                } else {
                    Log.e("Value is not come", "Value is not come");

                }

            }

        }
    }

    // update Data
    private void UpdateData(String labReferenceCode, String dateOfReceipt, String dateOfPutting, String method, String variety, String seedClass, String totalSeeds, String avNormalSeedlings, String avAbnormalSeedllings, String avDeadSeedlings, String avHardFUS, String finalGermination, String dateOfReport, String initialsOfAnalyst) {
        Log.e("seedid", seed_id);
        JSONObject js = new JSONObject();

        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfPutting", dateOfPutting);
            js.put("crop", crop_id);
            js.put("method", method);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            //  js.put("dateOfPutting1", dateOfPutting1);
            js.put("totalSeeds", totalSeeds);
            js.put("avNormalSeedlings", avNormalSeedlings);
            js.put("avAbnormalSeedllings", avAbnormalSeedllings);
            js.put("avDeadSeedlings", avDeadSeedlings);
            js.put("avHardFUS", avHardFUS);
            js.put("finalGermination", finalGermination);
            js.put("dateOfReport", dateOfReport);
            js.put("initialsOfAnalyst", initialsOfAnalyst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().updateGerminationTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
            @Override
            public void onResponse(Call<SaveTestResponse> call, Response<SaveTestResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                //        alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    Log.e("my Response  : ", response.body().getMessage().toString());

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Succesfull!").setContentText(response.body().getMessage().toString()).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }


                    }).show();

                    //clearData();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<SaveTestResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }

    private void SaveDatage(String labReferenceCode, String dateOfReceipt, String dateOfPutting, String method, String variety, String seedClass, String totalSeeds, String avNormalSeedlings, String avAbnormalSeedllings, String avDeadSeedlings, String avHardFUS, String finalGermination, String dateOfReport, String initialsOfAnalyst) {

        Log.e("seedid", seed_id);
        JSONObject js = new JSONObject();

        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfPutting", dateOfPutting);
            js.put("crop", crop_id);
            js.put("method", method);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            //  js.put("dateOfPutting1", dateOfPutting1);
            js.put("totalSeeds", totalSeeds);
            js.put("avNormalSeedlings", avNormalSeedlings);
            js.put("avAbnormalSeedllings", avAbnormalSeedllings);
            js.put("avDeadSeedlings", avDeadSeedlings);
            js.put("avHardFUS", avHardFUS);
            js.put("finalGermination", finalGermination);
            js.put("dateOfReport", dateOfReceipt);
            js.put("initialsOfAnalyst", initialsOfAnalyst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().saveGerminationTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
            @Override
            public void onResponse(Call<SaveTestResponse> call, Response<SaveTestResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                //        alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    Log.e("my Response  : ", response.body().getMessage().toString());

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Succesfull!").setContentText(response.body().getMessage().toString()).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }


                    }).show();

                    //clearData();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<SaveTestResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }

    private void clearData() {
        edt_lab_ref.setText("");
        // txt_date_putting2.setText("DATE OF PUTTING");
        edt_totel_seeds.setText("");
        edt_av_normal_seed.setText("");
        edt_av_abnormal_seed.setText("");
        edt_av_dead_seed.setText("");
        edt_av_hard_fus.setText("");
        edt_final_germination.setText("");
        txt_date_report.setText("" );
        edt_initial_analyst.setText("");

    }


    private void createIDS() {
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);


        lnt_issue_date = (LinearLayout) findViewById(R.id.lnt_issue_date);
        lnt_date_receipt = (LinearLayout) findViewById(R.id.lnt_date_receipt);
        int_date_putting = (LinearLayout) findViewById(R.id.int_date_putting);
        int_date_putting2 = (LinearLayout) findViewById(R.id.int_date_putting2);
        int_date_report = (LinearLayout) findViewById(R.id.int_date_report);

        txt_date_receipt = (TextView) findViewById(R.id.txt_date_receipt);
        txt_date_report = (TextView) findViewById(R.id.txt_date_report);
        txt_date_putting = (TextView) findViewById(R.id.txt_date_putting);
        // txt_date_putting2 = (TextView) findViewById(R.id.txt_date_putting2);
        txt_crop_Name = (TextView) findViewById(R.id.txt_crop_Name);
        txt_variety = (TextView) findViewById(R.id.txt_variety);

        btn_submit = (Button) findViewById(R.id.btn_submit);


        edt_seed_class = (EditText) findViewById(R.id.edt_seed_class);
        edt_lab_ref = (EditText) findViewById(R.id.edit_lab_ref);
        //   edt_mathod = (EditText) findViewById(R.id.edt_method);
        spnr_method = (Spinner) findViewById(R.id.spnr_method);
        edt_totel_seeds = (EditText) findViewById(R.id.edt_total_seeds);
        edt_av_normal_seed = (EditText) findViewById(R.id.edt_av_normal_seeds);
        edt_av_abnormal_seed = (EditText) findViewById(R.id.edt_av_abnormal_seeds);
        edt_av_dead_seed = (EditText) findViewById(R.id.edt_dead_seeding);
        edt_av_hard_fus = (EditText) findViewById(R.id.edt_av_hard_fus);
        edt_final_germination = (EditText) findViewById(R.id.edt_final_germination);
        edt_initial_analyst = (EditText) findViewById(R.id.edt_analyst);

        shimmer = new Shimmer();
        shimmer.start(txt_header);

        alertDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.spot_custom).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");
        setCurrentDate();

    }

    private void setCurrentDate() {
        String month1, day1;
        if (String.valueOf(month + 1).length() == 1) month1 = "0" + (month + 1);
        else month1 = (month + 1) + "";

        if (String.valueOf(day).length() == 1) {
            day1 = "0" + (day);
        } else {
            day1 = (day) + "";
        }
        txt_date_putting.setText(year + "-" + month1 + "-" + day1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String month1 = "", day1 = "";
        if (String.valueOf(month + 1).length() == 1) month1 = "0" + (month + 1);
        else month1 = (month + 1) + "";
        if (String.valueOf(dayOfMonth).length() == 1) day1 = "0" + (dayOfMonth);
        else day1 = (dayOfMonth) + "";
        if (DT == DateType.DR) {
            txt_date_receipt.setText(year + "-" + month1 + "-" + day1);

        }
        if (DT == DateType.DP) {
            txt_date_putting.setText(year + "-" + month1 + "-" + day1);

        }
        if (DT == DateType.DP2) {
            //    txt_date_putting2.setText(year + "-" + month1 + "-" + day1);

        }
        if (DT == DateType.DOR) {
            txt_date_report.setText(year + "-" + month1 + "-" + day1);

        } else if (DT == DateType.DI) {

        }
    }
}

