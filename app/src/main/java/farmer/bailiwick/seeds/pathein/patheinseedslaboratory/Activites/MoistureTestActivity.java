package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.CustomDialog.SweetAlertDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.MoistureDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveTestResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CalulateMethods;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoistureTestActivity extends RootActivity implements DatePickerDialog.OnDateSetListener {
    Context mContext;

    private LinearLayout int_issue_date, int_date_test, lnt_date_sample_receipt;
    private TextView txt_date_test, txt_crop_Name, txt_variety, txt_date_sample_receipt;

    private Button btn_submit, btn_oven_calculate;
    private EditText edt_lab, edt_temp, edt_rh, edt_container_temp, edt_moisture, edt_analyst, edt_seed_class;

    private EditText edt_wt_container, edt_wt_seed_container, edt_dry_seed_container;

    private EditText edt_wt_container1, edt_wt_seed_container1, edt_dry_seed_container1;
    // Oven
    private EditText edt_meter_reading1, edt_meter_reading2, edt_meter_reading3;
    private TextView txt_R_1, txt_R_2;
    Shimmer shimmer;
    ShimmerTextView txt_header;
    CardView crd_meter_reading, crd_containerTemp2, crd_container_oven_r1, crd_container_oven_r2;


    DatePickerDialog datePickerDialog;
    Spinner spnr_method;
    ArrayList<String> ListMethod;
    ArrayAdapter Spinner_method_adapter;

    enum DateType {DI, DOT, DSAMPLE}

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

    android.app.AlertDialog alertDialog;
    String operationType = "", labRef_No = "";
    String crop_id, seed_id;
    int updatevalue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture_test);
        mContext = MoistureTestActivity.this;
        createIDS();
        setSpinnerData();
        clickevent();
        getDetails();
        setfomula(false, false);
        spinnerClickEvent();
    }

    private void spinnerClickEvent() {
        spnr_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    setfomula(true, false);
                } else if (position == 2) {
                    setfomula(false, true);

                } else {
                    setfomula(false, false);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setfomula(boolean isMeter, boolean isOven) {
        if (isMeter) {
            crd_meter_reading.setVisibility(View.VISIBLE);
            crd_containerTemp2.setVisibility(View.GONE);
            crd_container_oven_r1.setVisibility(View.GONE);
            crd_container_oven_r2.setVisibility(View.GONE);

        } else if (isOven) {
            crd_meter_reading.setVisibility(View.GONE);
            crd_containerTemp2.setVisibility(View.VISIBLE);
            crd_container_oven_r1.setVisibility(View.VISIBLE);
            crd_container_oven_r2.setVisibility(View.VISIBLE);

        } else {
            crd_meter_reading.setVisibility(View.GONE);
            crd_containerTemp2.setVisibility(View.GONE);
            crd_container_oven_r1.setVisibility(View.GONE);
            crd_container_oven_r2.setVisibility(View.GONE);


        }

    }

    private void setSpinnerData() {

        ListMethod = new ArrayList<>();
        ListMethod.add("Select Method");
        ListMethod.add("Meter");
        ListMethod.add("Oven");
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
            getLabMoisture_data(operationType, labRef_No);


        } else {
            Toast.makeText(mContext, "List id is Not Get", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLabMoisture_data(String operationType, String labRef_no) {


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
        RetrofitApiClient.get().getMoistureTestDetail(body).enqueue(new Callback<MoistureDataResponse>() {
            @Override
            public void onResponse(Call<MoistureDataResponse> call, retrofit2.Response<MoistureDataResponse> response) {
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
            public void onFailure(Call<MoistureDataResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });

    }

    private void setBasedetail(retrofit2.Response<MoistureDataResponse> response) {


        // Common Value
        edt_lab.setText(response.body().getList().get(0).getLabReferenceCode());
        txt_crop_Name.setText(response.body().getList().get(0).getCropName());
        edt_seed_class.setText(response.body().getList().get(0).getSeedClass());
        txt_variety.setText(response.body().getList().get(0).getVariety());
        seed_id = response.body().getList().get(0).getSeedClassId();
        crop_id = response.body().getList().get(0).getCropId();
        txt_date_sample_receipt.setText(response.body().getList().get(0).getDateOfReceipt());
        updatevalue = response.body().getList().get(0).getUpdate();

        if (updatevalue == 1) {
            txt_date_test.setText(getfillValue(response.body().getList().get(0).getDateOfTest(), ""));
            String method_value = getfillValue(response.body().getList().get(0).getMethod(), "");
            setSelectedMethod(method_value);
            edt_temp.setText(getfillValue(response.body().getList().get(0).getTempC(), ""));
            edt_rh.setText(getfillValue(response.body().getList().get(0).getRh(), ""));
            // Meter Container
            edt_meter_reading1.setText(getfillValue(response.body().getList().get(0).getMeterReading1(), ""));
            edt_meter_reading2.setText(getfillValue(response.body().getList().get(0).getMeterReading2(), ""));
            edt_meter_reading3.setText(getfillValue(response.body().getList().get(0).getMeterReading3(), ""));
            // Oven Content
            edt_container_temp.setText(getfillValue(response.body().getList().get(0).getTempOven(), ""));
            // replica oven 1
            edt_wt_container.setText(getfillValue(response.body().getList().get(0).getWtOfContainer(), ""));
            edt_wt_seed_container.setText(getfillValue(response.body().getList().get(0).getWtOfSeedContainer(), ""));
            edt_dry_seed_container.setText(getfillValue(response.body().getList().get(0).getWtOfDrySeedContainer(), ""));

            // replica oven 2
            edt_wt_container1.setText(getfillValue(response.body().getList().get(0).getWtOfContainerR1(), ""));
            edt_wt_seed_container1.setText(getfillValue(response.body().getList().get(0).getWtOfSeedContainerR1(), ""));
            edt_dry_seed_container1.setText(getfillValue(response.body().getList().get(0).getWtOfDrySeedContainerR1(), ""));

            edt_moisture.setText(getfillValue(response.body().getList().get(0).getMoistureContent(), ""));
            edt_analyst.setText(getfillValue(response.body().getList().get(0).getInOfAnalyst(), ""));

            calculateMoisturePurity();
        }

    }

    private void setSelectedMethod(String method_value) {
        if (!method_value.equalsIgnoreCase("")) {
            int spinnerPosition = Spinner_method_adapter.getPosition(method_value);
            Log.e("position", "" + spinnerPosition);
            spnr_method.setSelection(spinnerPosition);
        }
        Log.e("method", method_value);
    }

    private String getfillValue(String strValue, String defaultValue) {
        if (strValue == null) {
            return defaultValue;
        } else {
            return strValue;
        }

    }

    private void clickevent() {
        // Meter Focus
        edt_meter_reading3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    validateMeterFormula();
                }
            }
        });
        edt_dry_seed_container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateOvenFormula(true, false);
                }
            }
        });
        edt_dry_seed_container1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateOvenFormula(false, true);
                }
            }
        });

        btn_oven_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMoisturePurity();

            }
        });
        int_issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DI;
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

        lnt_date_sample_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DSAMPLE;
             //   datePickerDialog.show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_LONG).show();
                checkValidation();

            }
        });
    }

    private void calculateMoisturePurity() {
        if (spnr_method.getSelectedItemPosition() == 2) {
            txt_R_1.setText("");
            txt_R_2.setText("");
            edt_moisture.setText("");
            validateOvenFormula(true, true);
        } else if (spnr_method.getSelectedItemPosition() == 1) {
            validateMeterFormula();
        }
    }

    private void validateMeterFormula() {
        String meter1, meter2, meter3;
        meter1 = edt_meter_reading1.getText().toString().trim();
        meter2 = edt_meter_reading2.getText().toString().trim();
        meter3 = edt_meter_reading3.getText().toString().trim();

        if (meter1.equalsIgnoreCase("")) {
            edt_meter_reading1.setError("Requried");
            edt_meter_reading1.requestFocus();
            return;
        } else if (meter2.equalsIgnoreCase("")) {
            edt_meter_reading2.setError("Requried");
            edt_meter_reading2.requestFocus();
            return;
        } else if (meter3.equalsIgnoreCase("")) {
            edt_meter_reading3.setError("Requried");
            edt_meter_reading3.requestFocus();
            return;

        } else {
            float moiture_purity = CalulateMethods.getMeterPercent(meter1, meter2, meter3);
            edt_moisture.setText("" + moiture_purity);
        }
    }

    private void validateOvenFormula(boolean isreplica1, boolean isReplica2) {
        float R1 = 0, R2;
        if (isreplica1) {
            if (edt_wt_container.getText().toString().trim().equalsIgnoreCase("")) {
                edt_wt_container.setError("Requried");
                edt_wt_container.requestFocus();
                return;
            } else if (edt_wt_seed_container.getText().toString().trim().equalsIgnoreCase("")) {
                edt_wt_seed_container.setError("Requried");
                edt_wt_seed_container.requestFocus();
                return;
            } else if (edt_dry_seed_container.getText().toString().trim().equalsIgnoreCase("")) {
                edt_dry_seed_container.setError("Requried");
                edt_dry_seed_container.requestFocus();
                return;
            } else {
                float wt_C = Float.parseFloat(edt_wt_container.getText().toString().trim());
                float wt_C_Seed = Float.parseFloat(edt_wt_seed_container.getText().toString().trim());
                float wt_C_dry_seed = Float.parseFloat(edt_dry_seed_container.getText().toString().trim());

                Log.e("all values ", "wt c  : " + wt_C + "" + "  wt seed  " + wt_C_Seed + "" + "  wt c  " + wt_C_dry_seed + "  ");
                float moiture_purity = CalulateMethods.getOvenPercent(wt_C, wt_C_Seed, wt_C_dry_seed);
                R1 = moiture_purity;


                txt_R_1.setText("Moisture Content % " + moiture_purity);

            }

        }
        if (isReplica2) {
            if (edt_wt_container1.getText().toString().trim().equalsIgnoreCase("")) {
                edt_wt_container1.setError("Requried");
                edt_wt_container1.requestFocus();
                return;
            } else if (edt_wt_seed_container1.getText().toString().trim().equalsIgnoreCase("")) {
                edt_wt_seed_container1.setError("Requried");
                edt_wt_seed_container1.requestFocus();
                return;
            } else if (edt_dry_seed_container1.getText().toString().trim().equalsIgnoreCase("")) {
                edt_dry_seed_container1.setError("Requried");
                edt_dry_seed_container1.requestFocus();
                return;
            } else {
                float wt_C = Float.parseFloat(edt_wt_container1.getText().toString().trim());
                float wt_C_Seed = Float.parseFloat(edt_wt_seed_container1.getText().toString().trim());
                float wt_C_dry_seed = Float.parseFloat(edt_dry_seed_container1.getText().toString().trim());

                Log.e("all values ", "wt c  : " + wt_C + "" + "  wt seed  " + wt_C_Seed + "" + "  wt c  " + wt_C_dry_seed + "  ");
                float moiture_purity = CalulateMethods.getOvenPercent(wt_C, wt_C_Seed, wt_C_dry_seed);

                txt_R_2.setText("Moisture Content % " + moiture_purity);
                R2 = moiture_purity;

                if (isreplica1 && isReplica2) {
                    if (!txt_R_1.getText().toString().equalsIgnoreCase("") && !txt_R_2.getText().toString().equalsIgnoreCase("")) {
                        float cal = (R1 + R2) / 2;
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        float final_result = Float.valueOf(decimalFormat.format(cal));
                        edt_moisture.setText("" + final_result);

                    } else {
                        txt_R_1.setText("");
                        txt_R_2.setText("");
                        edt_moisture.setText("");
                    }
                }
            }
        }

    }

    private void checkValidation() {
        calculateMoisturePurity();

        if (edt_lab.getText().toString().trim().equalsIgnoreCase("")) {

            edt_lab.setError("Requried");
            return;

        } else if (txt_date_sample_receipt.getText().toString().equalsIgnoreCase("")) {
            txt_date_sample_receipt.setError("Please Select Date");
            return;
        } else if (txt_date_test.getText().toString().equalsIgnoreCase("")) {
            txt_date_test.setError("Please Select Date");
            return;
        } else if (spnr_method.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Method", Toast.LENGTH_LONG).show();
            return;

        } else if (edt_temp.getText().toString().trim().equalsIgnoreCase("")) {
            edt_temp.setError("Requried");
            return;

        } else if (edt_rh.getText().toString().trim().equalsIgnoreCase("")) {
            edt_rh.setError("Requried");
            return;

        } else if (edt_moisture.getText().toString().trim().equalsIgnoreCase("")) {
            edt_moisture.setError("Requried");
            return;

        } else if (edt_analyst.getText().toString().trim().equalsIgnoreCase("")) {
            edt_analyst.setError("Requried");
            return;

        } else if (spnr_method.getSelectedItemPosition() == 2 && edt_container_temp.getText().toString().trim().equalsIgnoreCase("")) {
            edt_container_temp.setError("Requried");
            edt_container_temp.requestFocus();
            return;

        } else {
            Log.e("All Set ", "All Set ");

          //  Toast.makeText(getApplicationContext(), "All data is selected No save it", Toast.LENGTH_SHORT).show();
            String labReferenceCode, dateOfTest, method, dateOfReceipt, cropName = "", variety, seedClass = "", tempC, rh, meterReading1, meterReading2, meterReading3, tempOven, wtOfContainer, wtOfSeedContainer, wtOfDrySeedContainer, wtOfContainerR1, wtOfSeedContainerR1, wtOfDrySeedContainerR1, moistureContent, inOfAnalyst;

            labReferenceCode = edt_lab.getText().toString().trim();
            dateOfReceipt = txt_date_sample_receipt.getText().toString().trim();
            dateOfTest = txt_date_test.getText().toString().trim();

            method = spnr_method.getSelectedItem().toString();
            variety = txt_variety.getText().toString().trim();

            tempC = edt_temp.getText().toString().trim();
            rh = edt_rh.getText().toString().trim();

            meterReading1 = edt_meter_reading1.getText().toString().trim();
            meterReading2 = edt_meter_reading2.getText().toString().trim();
            meterReading3 = edt_meter_reading3.getText().toString().trim();
            tempOven = edt_container_temp.getText().toString().trim();

            wtOfContainer = edt_wt_container.getText().toString().trim();
            wtOfSeedContainer = edt_wt_seed_container.getText().toString().trim();
            wtOfDrySeedContainer = edt_dry_seed_container.getText().toString().trim();

            wtOfContainerR1 = edt_wt_container1.getText().toString().trim();
            wtOfSeedContainerR1 = edt_wt_seed_container1.getText().toString().trim();
            wtOfDrySeedContainerR1 = edt_dry_seed_container1.getText().toString().trim();

            moistureContent = edt_moisture.getText().toString().trim();
            inOfAnalyst = edt_analyst.getText().toString().trim();

            if (updatevalue == 0) {
                SaveDataMOISTURE(labReferenceCode, dateOfTest, method, dateOfReceipt, cropName, variety, seedClass, tempC, rh, meterReading1, meterReading2, meterReading3, tempOven, wtOfContainer, wtOfSeedContainer, wtOfDrySeedContainer, wtOfContainerR1, wtOfSeedContainerR1, wtOfDrySeedContainerR1, moistureContent, inOfAnalyst);

            } else if (updatevalue == 1) {
                Log.e("i have to update", "i have to update");
                UpdateDataMOISTURE(labReferenceCode, dateOfTest, method, dateOfReceipt, cropName, variety, seedClass, tempC, rh, meterReading1, meterReading2, meterReading3, tempOven, wtOfContainer, wtOfSeedContainer, wtOfDrySeedContainer, wtOfContainerR1, wtOfSeedContainerR1, wtOfDrySeedContainerR1, moistureContent, inOfAnalyst);

            } else {
                Log.e("Value is not come", "Value is not come");

            }

        }
    }

    private void UpdateDataMOISTURE(String labReferenceCode, String dateOfTest, String method, String dateOfReceipt, String cropName, String variety, String seedClass, String tempC, String rh, String meterReading1, String meterReading2, String meterReading3, String tempOven, String wtOfContainer, String wtOfSeedContainer, String wtOfDrySeedContainer, String wtOfContainerR1, String wtOfSeedContainerR1, String wtOfDrySeedContainerR1, String moistureContent, String inOfAnalyst) {


        JSONObject js = new JSONObject();


        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfTest", dateOfTest);
            js.put("method", method);
            js.put("dateOfReceipt", dateOfReceipt);

            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            // Meter
            js.put("tempC", tempC);
            js.put("rh", rh);
            js.put("meterReading1", meterReading1);
            js.put("meterReading2", meterReading2);
            js.put("meterReading3", meterReading3);
            // Oven
            js.put("tempOven", tempOven);

            js.put("wtOfContainer", wtOfContainer);
            js.put("wtOfSeedContainer", wtOfSeedContainer);
            js.put("wtOfDrySeedContainer", wtOfDrySeedContainer);

            js.put("wtOfContainerR1", wtOfContainerR1);
            js.put("wtOfSeedContainerR1", wtOfSeedContainerR1);
            js.put("wtOfDrySeedContainerR1", wtOfDrySeedContainerR1);


            js.put("moistureContent", moistureContent);
            js.put("inOfAnalyst", inOfAnalyst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().updateMoistureTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
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

    private void SaveDataMOISTURE(String labReferenceCode, String dateOfTest, String method, String dateOfReceipt, String cropName, String variety, String seedClass, String tempC, String rh, String meterReading1, String meterReading2, String meterReading3, String tempOven, String wtOfContainer, String wtOfSeedContainer, String wtOfDrySeedContainer, String wtOfContainerR1, String wtOfSeedContainerR1, String wtOfDrySeedContainerR1, String moistureContent, String inOfAnalyst) {


        JSONObject js = new JSONObject();


        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfTest", dateOfTest);
            js.put("method", method);
            js.put("dateOfReceipt", dateOfReceipt);

            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);
            // Meter
            js.put("tempC", tempC);
            js.put("rh", rh);
            js.put("meterReading1", meterReading1);
            js.put("meterReading2", meterReading2);
            js.put("meterReading3", meterReading3);
            // Oven
            js.put("tempOven", tempOven);

            js.put("wtOfContainer", wtOfContainer);
            js.put("wtOfSeedContainer", wtOfSeedContainer);
            js.put("wtOfDrySeedContainer", wtOfDrySeedContainer);

            js.put("wtOfContainerR1", wtOfContainerR1);
            js.put("wtOfSeedContainerR1", wtOfSeedContainerR1);
            js.put("wtOfDrySeedContainerR1", wtOfDrySeedContainerR1);


            js.put("moistureContent", moistureContent);
            js.put("inOfAnalyst", inOfAnalyst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().saveMoistureTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
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

    private void createIDS() {
        int_issue_date = (LinearLayout) findViewById(R.id.int_issue_date);
        int_date_test = (LinearLayout) findViewById(R.id.int_date_test);
        lnt_date_sample_receipt = (LinearLayout) findViewById(R.id.lnt_date_sample_receipt);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);

        crd_containerTemp2 = (CardView) findViewById(R.id.crd_containerTemp2);
        crd_meter_reading = (CardView) findViewById(R.id.crd_meter_reading);
        crd_container_oven_r1 = (CardView) findViewById(R.id.crd_container_oven_r1);
        crd_container_oven_r2 = (CardView) findViewById(R.id.crd_container_oven_r2);


        txt_date_test = (TextView) findViewById(R.id.txt_date_test);
        txt_crop_Name = (TextView) findViewById(R.id.txt_crop_Name);
        txt_variety = (TextView) findViewById(R.id.txt_variety);

        txt_R_1 = (TextView) findViewById(R.id.txt_R_1);
        txt_R_2 = (TextView) findViewById(R.id.txt_R_2);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_oven_calculate = (Button) findViewById(R.id.btn_oven_calculate);

        txt_date_sample_receipt = (TextView) findViewById(R.id.txt_date_sample_receipt);


        edt_lab = (EditText) findViewById(R.id.edt_lab_ref);
        edt_temp = (EditText) findViewById(R.id.edt_temp);
        edt_rh = (EditText) findViewById(R.id.edt_rh);
        edt_container_temp = (EditText) findViewById(R.id.edt_container_temp);

        // replica 1
        edt_dry_seed_container = (EditText) findViewById(R.id.edt_wt_dry_seed_container);
        edt_wt_seed_container = (EditText) findViewById(R.id.edt_wt_seed_sontainer);
        edt_wt_container = (EditText) findViewById(R.id.edt_wt_container);
        // replica 2
        edt_dry_seed_container1 = (EditText) findViewById(R.id.edt_wt_dry_seed_container1);
        edt_wt_seed_container1 = (EditText) findViewById(R.id.edt_wt_seed_sontainer1);
        edt_wt_container1 = (EditText) findViewById(R.id.edt_wt_container1);
// meter
        edt_meter_reading1 = (EditText) findViewById(R.id.edt_meter_reading1);
        edt_meter_reading2 = (EditText) findViewById(R.id.edt_meter_reading2);
        edt_meter_reading3 = (EditText) findViewById(R.id.edt_meter_reading3);

        edt_analyst = (EditText) findViewById(R.id.edt_analyst);
        edt_moisture = (EditText) findViewById(R.id.edt_moisture);


        edt_seed_class = (EditText) findViewById(R.id.edt_seed_class);

        spnr_method = (Spinner) findViewById(R.id.spnr_method);

        datePickerDialog = new DatePickerDialog(this, this, year, month, day);


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
        Log.e("Date values", "Type" + DT + "  date " + day1 + " - " + month1 + " - " + year);

        if (DT == DateType.DOT) {
            txt_date_test.setText(year + "-" + month1 + "-" + day1);

        } else if (DT == DateType.DI) {

        } else if (DT == DateType.DSAMPLE) {
            txt_date_sample_receipt.setText(year + "-" + month1 + "-" + day1);

        }

    }
}