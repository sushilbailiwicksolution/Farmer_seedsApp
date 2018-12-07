package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.CustomDialog.SweetAlertDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.RedRiceDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveTestResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CalulateMethods;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CheckConnectivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedRiceTest extends RootActivity implements DatePickerDialog.OnDateSetListener {
    Context mContext;

    LinearLayout int_issue_date, int_date_receipt, int_date_test;
    TextView txt_date_receipt, txt_date_test, txt_crop_Name, txt_variety, txtRR_purity;

    Button btn_submit, btn_analys_calculate;
    EditText edt_lab, edt_seed_class, edt_anaylist_signature;

    // round 1
    EditText edt_working_sample, edt_number_red_rice_working_sample;
    TextView txt_avRed_rice;
    //Round 2
    EditText edt_working_sample1, edt_number_red_rice_working_sample1;
    TextView txt_avRed_rice1;

    Shimmer shimmer;
    ShimmerTextView txt_header;
    RadioButton rb_not_accept, rb_accept, rb_method1, rb_method2;
    RadioGroup RDGcrop_status, RDGcrop_method;

    DatePickerDialog datePickerDialog;

    int updatevalue = -1;


    enum DateType {DR, DI, DOT}

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    DateType DT;

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

    String operationType = "", labRef_No = "";
    android.app.AlertDialog alertDialog;
    String crop_id, seed_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_rice_test);
        mContext = RedRiceTest.this;
        createIDS();
        clickevent();

        AnalysisRound();
        AnalysisRound1();
        if (CheckConnectivity.isConnected(mContext)) {
            getDetails();

        }


    }

    private void AnalysisRound() {
        edt_number_red_rice_working_sample.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String seed_weight_WS = edt_working_sample.getText().toString().trim();
                String RR_wrkng_sample = edt_number_red_rice_working_sample.getText().toString().trim();
                if (!RR_wrkng_sample.equalsIgnoreCase("")) {
                    if (seed_weight_WS.equalsIgnoreCase("")) {
                        edt_working_sample.setError("Requried");
                        edt_working_sample.requestFocus();
                        return;
                    } else {
                        float rrAVG = CalulateMethods.getRED_RICEAverage(RR_wrkng_sample, seed_weight_WS);
                        txt_avRed_rice.setText("" + rrAVG);
                    }
                }
            }
        });

    }

    private void AnalysisRound1() {
        edt_number_red_rice_working_sample1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String seed_weight_WS = edt_working_sample1.getText().toString().trim();
                String RR_wrkng_sample = edt_number_red_rice_working_sample1.getText().toString().trim();
                if (!RR_wrkng_sample.equalsIgnoreCase("")) {
                    if (seed_weight_WS.equalsIgnoreCase("")) {
                        edt_working_sample1.setError("Requried");
                        edt_working_sample1.requestFocus();
                        return;
                    } else {
                        float rrAVG = CalulateMethods.getRED_RICEAverage(RR_wrkng_sample, seed_weight_WS);
                        txt_avRed_rice1.setText("" + rrAVG);
                    }
                }
            }
        });

    }

    private void CalculaterRound() {
        txtRR_purity.setText("");
        String seed_weight_WS = edt_working_sample.getText().toString().trim();
        String RR_wrkng_sample = edt_number_red_rice_working_sample.getText().toString().trim();

        String seed_weight_WS1 = edt_working_sample1.getText().toString().trim();
        String RR_wrkng_sample1 = edt_number_red_rice_working_sample1.getText().toString().trim();

        if (seed_weight_WS.equalsIgnoreCase("") || RR_wrkng_sample.equalsIgnoreCase("")) {
            edt_working_sample.setError("Requried");
            edt_number_red_rice_working_sample1.setError("Requried");
            edt_working_sample.requestFocus();
            return;

        } else if (seed_weight_WS1.equalsIgnoreCase("") || RR_wrkng_sample1.equalsIgnoreCase("")) {
            edt_working_sample1.setError("Requried");
            edt_number_red_rice_working_sample1.setError("Requried");
            edt_working_sample1.requestFocus();
            return;

        } else {

            float rrAVG = CalulateMethods.getRED_RICEAverage(RR_wrkng_sample, seed_weight_WS);
            float rrAVG1 = CalulateMethods.getRED_RICEAverage(RR_wrkng_sample1, seed_weight_WS1);


            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            float final_Replica1 = Float.valueOf(decimalFormat.format(rrAVG));
            float final_Replica2 = Float.valueOf(decimalFormat.format(rrAVG1));

            txt_avRed_rice.setText("" + final_Replica1);
            txt_avRed_rice1.setText("" + final_Replica2);

            float purity = (rrAVG + rrAVG1) / 2;
            float final_Purity = Float.valueOf(decimalFormat.format(purity));

            txtRR_purity.setText("" + final_Purity);

        }

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
            if (CheckConnectivity.isConnected(mContext)) {
                getLabRedRice_data(operationType, labRef_No);

            }


        } else {
            Toast.makeText(mContext, "List id is Not Get", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLabRedRice_data(String operationType, String labRef_no) {


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
        RetrofitApiClient.get().getRedRiceTestDetail(body).enqueue(new Callback<RedRiceDataResponse>() {
            @Override
            public void onResponse(Call<RedRiceDataResponse> call, Response<RedRiceDataResponse> response) {
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
//                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Some Thing goes Wrong " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            }


            @Override
            public void onFailure(Call<RedRiceDataResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                Toast.makeText(getApplicationContext(), "Some Thing goes Wrong ", Toast.LENGTH_SHORT).show();
                finish();

                alertDialog.dismiss();
            }
        });

    }

    private void setBasedetail(Response<RedRiceDataResponse> response) {


        // Common Value
        edt_lab.setText(response.body().getList().get(0).getLabRefrenceNumber());
        txt_crop_Name.setText(response.body().getList().get(0).getCropName());
        edt_seed_class.setText(response.body().getList().get(0).getSeedClass());
        txt_variety.setText(response.body().getList().get(0).getVariety());
        seed_id = response.body().getList().get(0).getSeedClassId();
        crop_id = response.body().getList().get(0).getCropId();
        txt_date_receipt.setText(response.body().getList().get(0).getDateOfReceipt());

        updatevalue = response.body().getList().get(0).getUpdate();

        if (updatevalue == 1) {
            txt_date_test.setText(getfillValue(response.body().getList().get(0).getDateOfTest(), ""));

            String radioValueMethod = getfillValue(response.body().getList().get(0).getMethod(), "");
            setRadioMethod(radioValueMethod);

            String radioValueAccept = getfillValue(response.body().getList().get(0).getAcceptable(), "");
            setRadioAccept(radioValueAccept);
            // replica 1
            edt_working_sample.setText(getfillValue(response.body().getList().get(0).getWeightOfWorkingSample(), ""));
            edt_number_red_rice_working_sample.setText(getfillValue(response.body().getList().get(0).getNumberRedRiceWorking(), ""));

            // replica 2
            edt_working_sample1.setText(getfillValue(response.body().getList().get(0).getWeightOfWorkingSampleR(), ""));
            edt_number_red_rice_working_sample1.setText(getfillValue(response.body().getList().get(0).getNumberRedRiceWorkingR(), ""));
            edt_anaylist_signature.setText(getfillValue(response.body().getList().get(0).getAnalystSignature(), ""));
            CalculaterRound();

        }

// Submiting Value
        // edt_number_red_rice_500g.setText(getfillValue(response.body().getList().get(0).getNumberRedRicePer500(), ""));


    }

    private void setRadioMethod(String radioValueMethod) {

        if (!radioValueMethod.equalsIgnoreCase("")) {
            if (radioValueMethod.equalsIgnoreCase("Satake Machine Dehulling")) {
                rb_method1.setChecked(true);
            } else if (radioValueMethod.equalsIgnoreCase("Water Soak")) {
                rb_method2.setChecked(true);
            }

        }

    }


    private void setRadioAccept(String radioValue) {
        if (!radioValue.equalsIgnoreCase("")) {
            if (radioValue.equalsIgnoreCase("Acceptable")) {
                rb_accept.setChecked(true);
            } else if (radioValue.equalsIgnoreCase("Not Acceptable")) {
                rb_not_accept.setChecked(true);
            }

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
        btn_analys_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaterRound();

            }
        });
        int_issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DI;
                datePickerDialog.show();

            }
        });
        int_date_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DR;
                datePickerDialog.show();
            }
        });
        int_date_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DOT;
                datePickerDialog.show();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_LONG).show();

                checkValidation();

            }
        });
    }


    private void checkValidation() {
        CalculaterRound();

        if (edt_lab.getText().toString().trim().equalsIgnoreCase("")) {
            edt_lab.setError("Requried");
            return;

        } else if (txt_date_receipt.getText().toString().equalsIgnoreCase("")) {
            txt_date_receipt.setError("Please Select Date");
            return;
        } else if (txt_date_test.getText().toString().equalsIgnoreCase("")) {
            txt_date_test.setError("Please Select Date");
            return;
        } else if (edt_seed_class.getText().toString().trim().equalsIgnoreCase("")) {
            edt_seed_class.setError("Requried");
            return;

        } else if (RDGcrop_method.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select Method", Toast.LENGTH_LONG).show();
            return;

        } else if (edt_working_sample.getText().toString().trim().equalsIgnoreCase("")) {
            edt_working_sample.setError("Requried");
            return;

        } else if (edt_number_red_rice_working_sample.getText().toString().trim().equalsIgnoreCase("")) {
            edt_number_red_rice_working_sample.setError("Requried");
            return;

        } else if (edt_working_sample1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_working_sample1.setError("Requried");
            return;

        } else if (edt_number_red_rice_working_sample1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_number_red_rice_working_sample1.setError("Requried");
            return;

        } else if (edt_anaylist_signature.getText().toString().trim().equalsIgnoreCase("")) {
            edt_anaylist_signature.setError("Requried");
            return;

        } else if (txtRR_purity.getText().toString().trim().equalsIgnoreCase("")) {
            txtRR_purity.setError("Purity is not analyse");
            return;

        } else if (RDGcrop_status.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select Product Quality", Toast.LENGTH_LONG).show();
            return;

        } else {
            Log.e("All Set ", "All Set ");
            Toast.makeText(getApplicationContext(), "All data is selected No save it", Toast.LENGTH_SHORT).show();
            String labRefrenceNumber, dateOfReceipt, dateOfTest, cropName = "", variety, seedClass = "", weightOfWorkingSample, numberRedRiceWorking, AVGnumberRedRicePer500, acceptable, method, analystSignature, weightOfWorkingSampleR, numberRedRiceWorkingR;

            labRefrenceNumber = edt_lab.getText().toString().trim();
            dateOfReceipt = txt_date_receipt.getText().toString().trim();
            dateOfTest = txt_date_test.getText().toString().trim();

            int idMethod = RDGcrop_method.getCheckedRadioButtonId();
            View radioButtonMethod = RDGcrop_method.findViewById(idMethod);
            int radioIdMethod = RDGcrop_method.indexOfChild(radioButtonMethod);

            RadioButton btnMethod = (RadioButton) RDGcrop_method.getChildAt(radioIdMethod);
            String selectionMethod = (String) btnMethod.getText();
            method = selectionMethod;
            variety = txt_variety.getText().toString().trim();

            weightOfWorkingSample = edt_working_sample.getText().toString().trim();
            numberRedRiceWorking = edt_number_red_rice_working_sample.getText().toString().trim();

            weightOfWorkingSampleR = edt_working_sample1.getText().toString().trim();
            numberRedRiceWorkingR = edt_number_red_rice_working_sample1.getText().toString().trim();

            AVGnumberRedRicePer500 = txtRR_purity.getText().toString().trim();
            analystSignature = edt_anaylist_signature.getText().toString().trim();

            int id = RDGcrop_status.getCheckedRadioButtonId();
            View radioButton = RDGcrop_status.findViewById(id);
            int radioId = RDGcrop_status.indexOfChild(radioButton);

            RadioButton btn = (RadioButton) RDGcrop_status.getChildAt(radioId);
            String selection = (String) btn.getText();
            acceptable = selection;


            if (CheckConnectivity.isConnected(mContext)) {
                if (updatevalue == 0) {
                    SaveDatage(labRefrenceNumber, dateOfReceipt, dateOfTest, cropName, variety, seedClass, weightOfWorkingSample, numberRedRiceWorking, AVGnumberRedRicePer500, acceptable, method, analystSignature, weightOfWorkingSampleR, numberRedRiceWorkingR);

                } else if (updatevalue == 1) {
                    Log.e("i have to update", "i have to update");
                    UpdateDatage(labRefrenceNumber, dateOfReceipt, dateOfTest, cropName, variety, seedClass, weightOfWorkingSample, numberRedRiceWorking, AVGnumberRedRicePer500, acceptable, method, analystSignature, weightOfWorkingSampleR, numberRedRiceWorkingR);


                } else {
                    Log.e("Value is not come", "Value is not come");

                }
            }
        }
    }

    private void UpdateDatage(String labRefrenceNumber, String dateOfReceipt, String dateOfTest, String cropName, String variety, String seedClass, String weightOfWorkingSample, String numberRedRiceWorking, String numberRedRicePer500, String acceptable, String method, String analystSignature, String weightOfWorkingSampleR, String numberRedRiceWorkingR) {
        alertDialog.show();
        Log.e("seedid", seed_id);
        JSONObject js = new JSONObject();

        try {
            js.put("labRefrenceNumber", labRefrenceNumber);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfTest", dateOfTest);
            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            js.put("weightOfWorkingSample", weightOfWorkingSample);
            js.put("numberRedRiceWorking", numberRedRiceWorking);
            js.put("numberRedRicePer500", numberRedRicePer500);
            //  js.put("seedStandard", "");
            js.put("acceptable", acceptable);
            js.put("method", method);
            js.put("analystSignature", analystSignature);
            js.put("weightOfWorkingSampleR", weightOfWorkingSampleR);
            js.put("numberRedRiceWorkingR", numberRedRiceWorkingR);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().updateRedRice(body).enqueue(new Callback<SaveTestResponse>() {
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

    private void SaveDatage(String labRefrenceNumber, String dateOfReceipt, String dateOfTest, String cropName, String variety, String seedClass, String weightOfWorkingSample, String numberRedRiceWorking, String numberRedRicePer500, String acceptable, String method, String analystSignature, String weightOfWorkingSampleR, String numberRedRiceWorkingR) {
        alertDialog.show();
        Log.e("seedid", seed_id);
        JSONObject js = new JSONObject();

        try {
            js.put("labRefrenceNumber", labRefrenceNumber);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfTest", dateOfTest);
            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            js.put("weightOfWorkingSample", weightOfWorkingSample);
            js.put("numberRedRiceWorking", numberRedRiceWorking);
            js.put("numberRedRicePer500", numberRedRicePer500);
            //  js.put("seedStandard", "");
            js.put("acceptable", acceptable);
            js.put("method", method);
            js.put("analystSignature", analystSignature);
            js.put("weightOfWorkingSampleR", weightOfWorkingSampleR);
            js.put("numberRedRiceWorkingR", numberRedRiceWorkingR);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().saveRedRiceTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
            @Override
            public void onResponse(Call<SaveTestResponse> call, Response<SaveTestResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                alertDialog.dismiss();
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
                }


            }


            @Override
            public void onFailure(Call<SaveTestResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }


    private void createIDS() {
        int_issue_date = (LinearLayout) findViewById(R.id.int_issue_date);
        int_date_receipt = (LinearLayout) findViewById(R.id.int_date_receipt);
        int_date_test = (LinearLayout) findViewById(R.id.int_date_test);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);


        txt_date_receipt = (TextView) findViewById(R.id.txt_date_receipt);
        txt_date_test = (TextView) findViewById(R.id.txt_date_test);
        txt_crop_Name = (TextView) findViewById(R.id.txt_crop_Name);
        txt_variety = (TextView) findViewById(R.id.txt_variety);
        txtRR_purity = (TextView) findViewById(R.id.txtRR_purity);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_analys_calculate = (Button) findViewById(R.id.btn_analys_calculate);


        edt_lab = (EditText) findViewById(R.id.edt_lab_ref);
        edt_seed_class = (EditText) findViewById(R.id.edt_seed_class);
        edt_working_sample = (EditText) findViewById(R.id.edt_weight_working_sample);
        edt_number_red_rice_working_sample = (EditText) findViewById(R.id.edit_number_red_rice_working_sample);
        edt_anaylist_signature = (EditText) findViewById(R.id.edt_anaylist_signature);

        // Round 1
        edt_working_sample = (EditText) findViewById(R.id.edt_weight_working_sample);
        edt_number_red_rice_working_sample = (EditText) findViewById(R.id.edit_number_red_rice_working_sample);

        txt_avRed_rice = (TextView) findViewById(R.id.txt_avRed_rice);

        // Round 2

        edt_working_sample1 = (EditText) findViewById(R.id.edt_weight_working_sample1);
        edt_number_red_rice_working_sample1 = (EditText) findViewById(R.id.edit_number_red_rice_working_sample1);

        txt_avRed_rice1 = (TextView) findViewById(R.id.txt_avRed_rice1);


        EditText edt_working_sample1, edt_number_red_rice_working_sample1;
        TextView txt_avRed_rice1;


        RDGcrop_status = (RadioGroup) findViewById(R.id.RDGcrop_status);
        rb_accept = (RadioButton) findViewById(R.id.rb_accept);
        rb_not_accept = (RadioButton) findViewById(R.id.rb_not_accept);

        RDGcrop_method = (RadioGroup) findViewById(R.id.RDGcrop_method);
        rb_method1 = (RadioButton) findViewById(R.id.rb_method1);
        rb_method2 = (RadioButton) findViewById(R.id.rb_method2);


        setCurrentDate();
        shimmer = new Shimmer();
        shimmer.start(txt_header);

        alertDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.spot_custom).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");

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
        txt_date_test.setText(year + "-" + month1 + "-" + day1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String month1 = "", day1 = "";
        if (String.valueOf(month + 1).length() == 1) month1 = "0" + (month + 1);
        else month1 = (month + 1) + "";
        if (String.valueOf(dayOfMonth).length() == 1) day1 = "0" + (day);
        else day1 = (dayOfMonth) + "";
        if (DT == DateType.DR) {
            txt_date_receipt.setText(year + "-" + month1 + "-" + day1);

        } else if (DT == DateType.DOT) {
            txt_date_test.setText(year + "-" + month1 + "-" + day1);

        } else if (DT == DateType.DI) {
            txt_date_test.setText(year + "-" + month1 + "-" + day1);

        }
    }
}