package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.PhysicalDataResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveTestResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CalulateMethods;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalPurityActivity extends RootActivity implements DatePickerDialog.OnDateSetListener {
    Context mContext;

    private LinearLayout int_issue_date, int_date_receipt, int_date_test;
    private TextView txt_date_receipt, txt_date_test, txt_crop_Name, txt_variety;

    private Button btn_submit;
    private EditText edt_lab, edt_seed_class, edt_physical_purity, edt_analyst;
    // Round 1
    EditText edt_weight_sample, edt_pure_seed, edt_insert_matter, edt_other_crop, edt_weed_seed;
    TextView txt_OC_avg, txt_IM_avg, txt_PS_avg, txt_WS_avg;

    //Round 2
    EditText edt_weight_sample1, edt_pure_seed1, edt_insert_matter1, edt_other_crop1, edt_weed_seed1;
    TextView txt_OC_avg1, txt_IM_avg1, txt_PS_avg1, txt_WS_avg1;

    // Average
    CardView crd_result;

    TextView avg_working_sample, txt_per_other_seed, txt_per_inert_seed, txt_per_pure_seed, txt_per_weed_seed, txt_avg_pure_seed, txt_avg_inert_seed, txt_avg_other_seed, txt_avg_weed_seed;

    Button btn_analys_calculate;

    private Shimmer shimmer;
    ShimmerTextView txt_header;

    DatePickerDialog datePickerDialog;
    android.app.AlertDialog alertDialog;

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
    String crop_id, seed_id;
    int updatevalue = -1;

    float fPureSeed_wt, fInertSeed_wt, fOtherSeed_wt, fSeed_wt, fWeedSeed_wt;
    // Average
    float fPure_AVG, fInert_AVG, fOther_AVG, fWeed_AVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_purity);
        mContext = PhysicalPurityActivity.this;
        createIDS();
        clickevent();
        getDetails();
        Analysis_round();
        Analysis_round1();
    }


    private void Analysis_round() {
        edt_pure_seed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample.getText().toString().trim();
                String pure_seed = edt_pure_seed.getText().toString().trim();
                if (!pure_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample.setError("Requried");
                        edt_weight_sample.requestFocus();
                        return;
                    } else {
                        float psAVG = CalulateMethods.getPureSeedAverage(pure_seed, seed_weight);
                        txt_PS_avg.setText(psAVG + " %");
                    }
                }
            }
        });


        edt_insert_matter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample.getText().toString().trim();
                String inert_seed = edt_insert_matter.getText().toString().trim();
                if (!inert_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample.setError("Requried");
                        edt_weight_sample.requestFocus();
                        return;
                    } else {
                        float imAVG = CalulateMethods.getInertSeedAverage(inert_seed, seed_weight);
                        txt_IM_avg.setText(imAVG + " %");
                    }
                }
            }
        });

        edt_other_crop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample.getText().toString().trim();
                String other_seed = edt_other_crop.getText().toString().trim();
                if (!other_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample.setError("Requried");
                        edt_weight_sample.requestFocus();
                        return;
                    } else {
                        float ocAVG = CalulateMethods.getOtherSeedAverage(other_seed, seed_weight);
                        txt_OC_avg.setText(ocAVG + " %");
                    }
                }
            }
        });

        edt_weed_seed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample.getText().toString().trim();
                String weed_seed = edt_weed_seed.getText().toString().trim();
                if (!weed_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample.setError("Requried");
                        edt_weight_sample.requestFocus();
                        return;
                    } else {

                        float wsAVG = CalulateMethods.getOtherSeedAverage(weed_seed, seed_weight);
                        txt_WS_avg.setText(wsAVG + " %");
                    }
                }
            }
        });

    }

    private void Analysis_round1() {
        edt_pure_seed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample1.getText().toString().trim();
                String pure_seed = edt_pure_seed1.getText().toString().trim();
                if (!pure_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample1.setError("Requried");
                        edt_weight_sample1.requestFocus();
                        return;
                    } else {
                        float psAVG = CalulateMethods.getPureSeedAverage(pure_seed, seed_weight);
                        txt_PS_avg1.setText(psAVG + " %");
                    }
                }
            }
        });


        edt_insert_matter1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample1.getText().toString().trim();
                String inert_seed = edt_insert_matter1.getText().toString().trim();
                if (!inert_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample1.setError("Requried");
                        edt_weight_sample1.requestFocus();
                        return;
                    } else {
                        float imAVG = CalulateMethods.getInertSeedAverage(inert_seed, seed_weight);
                        txt_IM_avg1.setText(imAVG + " %");
                    }
                }
            }
        });

        edt_other_crop1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample1.getText().toString().trim();
                String other_seed = edt_other_crop1.getText().toString().trim();
                if (!other_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample1.setError("Requried");
                        edt_weight_sample1.requestFocus();
                        return;
                    } else {
                        float ocAVG = CalulateMethods.getOtherSeedAverage(other_seed, seed_weight);
                        txt_OC_avg1.setText(ocAVG + " %");
                    }
                }
            }
        });
        edt_weed_seed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String seed_weight = edt_weight_sample1.getText().toString().trim();
                String weed_seed = edt_weed_seed1.getText().toString().trim();
                if (!weed_seed.equalsIgnoreCase("")) {
                    if (seed_weight.equalsIgnoreCase("")) {
                        edt_weight_sample1.setError("Requried");
                        edt_weight_sample1.requestFocus();
                        return;
                    } else {

                        float wsAVG = CalulateMethods.getOtherSeedAverage(weed_seed, seed_weight);
                        txt_WS_avg1.setText(wsAVG + " %");
                    }
                }
            }
        });

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
            getLabPhysical_data(operationType, labRef_No);


        } else {
            Toast.makeText(mContext, "List id is Not Get", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLabPhysical_data(String operationType, String labRef_no) {


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
        RetrofitApiClient.get().getPhysicalTestDetail(body).enqueue(new Callback<PhysicalDataResponse>() {
            @Override
            public void onResponse(Call<PhysicalDataResponse> call, Response<PhysicalDataResponse> response) {
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
            public void onFailure(Call<PhysicalDataResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });

    }

    private void setBasedetail(Response<PhysicalDataResponse> response) {


        // Common Value
        edt_lab.setText(response.body().getList().get(0).getLabReferenceCode());
        txt_crop_Name.setText(response.body().getList().get(0).getCropName());
        edt_seed_class.setText(response.body().getList().get(0).getSeedClass());
        txt_variety.setText(response.body().getList().get(0).getVariety());
        seed_id = response.body().getList().get(0).getSeedClassId();
        crop_id = response.body().getList().get(0).getCropId();
        txt_date_receipt.setText(response.body().getList().get(0).getDateOfReceipt());
        updatevalue = response.body().getList().get(0).getUpdate();

        if (updatevalue == 1) {
            txt_date_test.setText(getfillValue(response.body().getList().get(0).getDateOfTest(), ""));
            // Replica 1
            edt_weight_sample.setText(getfillValue(response.body().getList().get(0).getWeightOfSample(), ""));
            edt_pure_seed.setText(getfillValue(response.body().getList().get(0).getPureSeed(), ""));
            edt_insert_matter.setText(getfillValue(response.body().getList().get(0).getInertMatter(), ""));
            edt_other_crop.setText(getfillValue(response.body().getList().get(0).getOtherCropSeeds(), ""));
// Replica 2
            edt_weight_sample1.setText(getfillValue(response.body().getList().get(0).getWeightOfSampleR(), ""));
            edt_pure_seed1.setText(getfillValue(response.body().getList().get(0).getPureSeedR(), ""));
            edt_insert_matter1.setText(getfillValue(response.body().getList().get(0).getInertMatterR(), ""));
            edt_other_crop1.setText(getfillValue(response.body().getList().get(0).getOtherCropSeedsR(), ""));

            edt_physical_purity.setText(getfillValue(response.body().getList().get(0).getPhysicalPurity(), ""));
            edt_analyst.setText(getfillValue(response.body().getList().get(0).getInOfAnalyst(), ""));
            CalculaterRound();
            CalculaterRound1();
        }

        //  txt_date_test.setText(getfillValue(response.body().getList().get(0).getDateOfTest(), ""));


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
                CalculaterRound1();

            }
        });


        int_date_receipt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                DT = DateType.DR;
                // datePickerDialog.show();
            }
        });
        int_date_test.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                DT = DateType.DOT;
                datePickerDialog.show();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_LONG).show();
                checkValidation();

            }
        });
    }

    private void CalculaterRound() {
        String seed_weight = edt_weight_sample.getText().toString().trim();


            String pure_seed = edt_pure_seed.getText().toString().trim();
            String inert_matter = edt_insert_matter.getText().toString().trim();
            String other_seed = edt_other_crop.getText().toString().trim();
            String weed_seed = edt_weed_seed.getText().toString().trim();

            if (seed_weight.equalsIgnoreCase("")) {
                edt_weight_sample.setError("Requried ");
                edt_weight_sample.requestFocus();
                return;
            }
           else if (!pure_seed.equalsIgnoreCase("")) {
                float psAVG = CalulateMethods.getPureSeedAverage(pure_seed, seed_weight);

                txt_PS_avg.setText(psAVG + " %");

                return;
            } else if (!inert_matter.equalsIgnoreCase("")) {
                float imAVG = CalulateMethods.getInertSeedAverage(inert_matter, seed_weight);
                txt_IM_avg.setText(imAVG + " %");
            } else if (!other_seed.equalsIgnoreCase("")) {
                float ocAVG = CalulateMethods.getInertSeedAverage(other_seed, seed_weight);
                txt_OC_avg.setText(ocAVG + " %");
            } else if (!weed_seed.equalsIgnoreCase("")) {
                float wsAVG = CalulateMethods.getInertSeedAverage(weed_seed, seed_weight);
                txt_WS_avg.setText(wsAVG + " %");

        }
    }

    private void CalculaterRound1() {
        String seed_weight = edt_weight_sample1.getText().toString().trim();


            String pure_seed = edt_pure_seed1.getText().toString().trim();
            String inert_matter = edt_insert_matter1.getText().toString().trim();
            String other_seed = edt_other_crop1.getText().toString().trim();
            String weed_seed = edt_weed_seed1.getText().toString().trim();

            if (seed_weight.equalsIgnoreCase("")) {
                edt_weight_sample1.setError("Requried");
                edt_weight_sample1.requestFocus();
                return;
            }else if (!pure_seed.equalsIgnoreCase("")) {
                float psAVG = CalulateMethods.getPureSeedAverage(pure_seed, seed_weight);
                txt_PS_avg1.setText(psAVG + " %");

            } else if (!inert_matter.equalsIgnoreCase("")) {
                float imAVG = CalulateMethods.getInertSeedAverage(inert_matter, seed_weight);
                txt_IM_avg1.setText(imAVG + " %");
            } else if (!other_seed.equalsIgnoreCase("")) {
                float ocAVG = CalulateMethods.getInertSeedAverage(other_seed, seed_weight);
                txt_OC_avg1.setText(ocAVG + " %");
            } else if (!weed_seed.equalsIgnoreCase("")) {
                float wsAVG = CalulateMethods.getInertSeedAverage(weed_seed, seed_weight);
                txt_WS_avg1.setText(wsAVG + " %");
            }
        PurityAverage();


    }

    private void PurityAverage() {
        edt_physical_purity.setText("");
        crd_result.setVisibility(View.INVISIBLE);

        String pure_seed = edt_pure_seed.getText().toString().trim();
        String pure_seed1 = edt_pure_seed1.getText().toString().trim();

        String inert_seed = edt_insert_matter.getText().toString().trim();
        String inert_seed1 = edt_insert_matter1.getText().toString().trim();

        String other_seed = edt_other_crop.getText().toString().trim();
        String other_seed1 = edt_other_crop1.getText().toString().trim();

        String weed_seed = edt_weed_seed.getText().toString().trim();
        String weed_seed1 = edt_weed_seed1.getText().toString().trim();

        String seed_weight = edt_weight_sample.getText().toString().trim();
        String seed_weight1 = edt_weight_sample1.getText().toString().trim();

        if (seed_weight.equalsIgnoreCase("") || seed_weight1.equalsIgnoreCase("")) {
            edt_weight_sample.setError("Requried");
            edt_weight_sample1.setError("Requried");
            return;
        } else if (inert_seed.equalsIgnoreCase("") || inert_seed1.equalsIgnoreCase("")) {
            edt_insert_matter.setError("Requried");
            edt_insert_matter1.setError("Requried");
            return;
        } else if (other_seed.equalsIgnoreCase("") || other_seed1.equalsIgnoreCase("")) {
            edt_other_crop.setError("Requried");
            edt_other_crop1.setError("Requried");

            return;
        } else if (weed_seed.equalsIgnoreCase("") || weed_seed1.equalsIgnoreCase("")) {
            edt_weed_seed.setError("Requried");
            edt_weed_seed1.setError("Requried");

            return;
        } else if (pure_seed.equalsIgnoreCase("") || pure_seed1.equalsIgnoreCase("")) {
            edt_pure_seed.setError("Requried");
            edt_pure_seed1.setError("Requried");

            return;
        } else {
            float psAVG = CalulateMethods.getPureSeedAverage(pure_seed, seed_weight);
            float psAVG1 = CalulateMethods.getPureSeedAverage(pure_seed1, seed_weight1);

            float inAVG = CalulateMethods.getPureSeedAverage(inert_seed, seed_weight);
            float inAVG1 = CalulateMethods.getPureSeedAverage(inert_seed1, seed_weight1);

            float otAVG = CalulateMethods.getPureSeedAverage(other_seed, seed_weight);
            float otAVG1 = CalulateMethods.getPureSeedAverage(other_seed1, seed_weight1);

            float wsAVG = CalulateMethods.getPureSeedAverage(weed_seed, seed_weight);
            float wsAVG1 = CalulateMethods.getPureSeedAverage(weed_seed1, seed_weight1);

            // Averages
            float pureSeed_Average = (psAVG + psAVG1) / 2;
            float inertSeed_Average = (inAVG + inAVG1) / 2;
            float otherSeed_Average = (otAVG + otAVG1) / 2;
            float weedSeed_Average = (wsAVG + wsAVG1) / 2;

            // weight


            fPureSeed_wt = CalulateMethods.getAverageWEIGHT(pure_seed, pure_seed1);
            fInertSeed_wt = CalulateMethods.getAverageWEIGHT(inert_seed, inert_seed1);
            fOtherSeed_wt = CalulateMethods.getAverageWEIGHT(other_seed, other_seed1);
            fWeedSeed_wt = CalulateMethods.getAverageWEIGHT(weed_seed, weed_seed1);

            fSeed_wt = CalulateMethods.getAverageWEIGHT(seed_weight, seed_weight1);


            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            fPure_AVG = Float.valueOf(decimalFormat.format(pureSeed_Average));
            fInert_AVG = Float.valueOf(decimalFormat.format(inertSeed_Average));
            fOther_AVG = Float.valueOf(decimalFormat.format(otherSeed_Average));
            fWeed_AVG = Float.valueOf(decimalFormat.format(weedSeed_Average));

            avg_working_sample.setText(fSeed_wt + " %");
            // Average
            txt_per_pure_seed.setText("" + fPure_AVG + " %");
            txt_per_inert_seed.setText("" + fInert_AVG + " %");
            txt_per_other_seed.setText("" + fOther_AVG + " %");
            txt_per_weed_seed.setText("" + fWeed_AVG + " %");

            // Grams

            txt_avg_pure_seed.setText("" + fPureSeed_wt + " g");
            txt_avg_inert_seed.setText("" + fInertSeed_wt + " g");
            txt_avg_other_seed.setText("" + fOtherSeed_wt + " g");
            txt_avg_weed_seed.setText("" + fWeedSeed_wt + " g");

            edt_physical_purity.setText("" + fPure_AVG);
            crd_result.setVisibility(View.VISIBLE);

        }
    }

    private void checkValidation() {
        CalculaterRound();
        CalculaterRound1();

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

        } else if (edt_weight_sample.getText().toString().trim().equalsIgnoreCase("")) {
            edt_weight_sample.setError("Requried");
            return;

        } else if (edt_pure_seed.getText().toString().trim().equalsIgnoreCase("")) {
            edt_pure_seed.setError("Requried");
            return;

        } else if (edt_insert_matter.getText().toString().trim().equalsIgnoreCase("")) {
            edt_insert_matter.setError("Requried");
            return;

        } else if (edt_other_crop.getText().toString().trim().equalsIgnoreCase("")) {
            edt_other_crop.setError("Requried");
            return;

        } else if (edt_weight_sample1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_weight_sample1.setError("Requried");
            return;

        } else if (edt_pure_seed1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_pure_seed1.setError("Requried");
            return;

        } else if (edt_insert_matter1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_insert_matter1.setError("Requried");
            return;

        } else if (edt_other_crop1.getText().toString().trim().equalsIgnoreCase("")) {
            edt_other_crop1.setError("Requried");
            return;

        } else if (edt_physical_purity.getText().toString().trim().equalsIgnoreCase("")) {
            edt_physical_purity.setError("Requried");
            return;

        } else if (edt_analyst.getText().toString().trim().equalsIgnoreCase("")) {
            edt_analyst.setError("Requried");
            return;

        } else {
            Log.e("All Set ", "All Set ");
            //  Toast.makeText(getApplicationContext(), "All data is selected No save it", Toast.LENGTH_SHORT).show();
            String labReferenceCode, dateOfReceipt, dateOfTest, cropName = "", variety, seedClass = "", pureSeed, weightOfSample, inertMatter, otherCropSeeds, weedCropSeeds, pureSeedR, weightOfSampleR, inertMatterR, otherCropSeedsR, weedCropSeedsR, inOfAnalyst, physicalPurity, avWtOfSample, avWtOfPureSeed, avWtOfInert, avOtherCrop, avWeedCrop, otherCropPer, inertPer, weedPer;

            labReferenceCode = edt_lab.getText().toString().trim();
            dateOfReceipt = txt_date_receipt.getText().toString().trim();
            dateOfTest = txt_date_test.getText().toString().trim();
            variety = txt_variety.getText().toString().trim();
            // Replica 1
            weightOfSample = edt_weight_sample.getText().toString().trim();
            pureSeed = edt_pure_seed.getText().toString().trim();
            inertMatter = edt_insert_matter.getText().toString().trim();
            otherCropSeeds = edt_other_crop.getText().toString().trim();
            weedCropSeeds = edt_weed_seed.getText().toString().trim();

            // Replica 2
            weightOfSampleR = edt_weight_sample1.getText().toString().trim();
            pureSeedR = edt_pure_seed1.getText().toString().trim();
            inertMatterR = edt_insert_matter1.getText().toString().trim();
            otherCropSeedsR = edt_other_crop1.getText().toString().trim();
            weedCropSeedsR = edt_weed_seed1.getText().toString().trim();

            //Average Purit G
            avWtOfSample = "" + fSeed_wt;
            avWtOfPureSeed = "" + fPureSeed_wt;
            avWtOfInert = "" + fInertSeed_wt;
            avOtherCrop = "" + fOtherSeed_wt;
            avWeedCrop = "" + fWeedSeed_wt;

            // Average purity in percentage
            otherCropPer = "" + fOther_AVG;
            inertPer = "" + fInert_AVG;
            weedPer = "" + fWeed_AVG;

            physicalPurity = edt_physical_purity.getText().toString().trim();
            inOfAnalyst = edt_analyst.getText().toString().trim();

            if (updatevalue == 0) {
                SaveDatage(labReferenceCode, dateOfReceipt, dateOfTest, cropName, variety, seedClass, pureSeed, weightOfSample, inertMatter, otherCropSeeds, pureSeedR, weightOfSampleR, inertMatterR, otherCropSeedsR, inOfAnalyst, physicalPurity, avWtOfSample, avWtOfPureSeed, avWtOfInert, avOtherCrop, otherCropPer, inertPer, weedCropSeeds, weedCropSeedsR, avWeedCrop, weedPer);

            } else if (updatevalue == 1) {
                Log.e("i have to update", "i have to update");
                UpdateDatag(labReferenceCode, dateOfReceipt, dateOfTest, cropName, variety, seedClass, pureSeed, weightOfSample, inertMatter, otherCropSeeds, pureSeedR, weightOfSampleR, inertMatterR, otherCropSeedsR, inOfAnalyst, physicalPurity, avWtOfSample, avWtOfPureSeed, avWtOfInert, avOtherCrop, otherCropPer, inertPer, weedCropSeeds, weedCropSeedsR, avWeedCrop, weedPer);

            } else {
                Log.e("Value is not come", "Value is not come");

            }
        }
    }

    private void UpdateDatag(String labReferenceCode, String dateOfReceipt, String dateOfTest, String cropName, String variety, String seedClass, String pureSeed, String weightOfSample, String inertMatter, String otherCropSeeds, String pureSeedR, String weightOfSampleR, String inertMatterR, String otherCropSeedsR, String inOfAnalyst, String physicalPurity, String avWtOfSample, String avWtOfPureSeed, String avWtOfInert, String avOtherCrop, String otherCropPer, String inertPer, String weedCropSeeds, String weedCropSeedsR, String avWeedCrop, String weedPer) {


        JSONObject js = new JSONObject();


        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfTest", dateOfTest);

            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);

            js.put("pureSeed", pureSeed);
            js.put("weightOfSample", weightOfSample);
            js.put("inertMatter", inertMatter);
            js.put("otherCropSeeds", otherCropSeeds);
            js.put("weed", weedCropSeeds);

            js.put("pureSeedR", pureSeedR);
            js.put("weightOfSampleR", weightOfSampleR);
            js.put("inertMatterR", inertMatterR);
            js.put("otherCropSeedsR", otherCropSeedsR);
            js.put("weedReplica", weedCropSeedsR);


            js.put("inOfAnalyst", inOfAnalyst);
            js.put("physicalPurity", physicalPurity);

            js.put("avWtOfSample", avWtOfSample);

            js.put("avWtOfPureSeed", avWtOfPureSeed);
            js.put("avWtOfInert", avWtOfInert);
            js.put("avOtherCrop", avOtherCrop);
            js.put("weedAverage", avWeedCrop);

            js.put("otherCropPer", otherCropPer);
            js.put("inertPer", inertPer);
            js.put("weedPercentage", weedPer);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().updatePhysicalTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
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

    private void SaveDatage(String labReferenceCode, String dateOfReceipt, String dateOfTest, String cropName, String variety, String seedClass, String pureSeed, String weightOfSample, String inertMatter, String otherCropSeeds, String pureSeedR, String weightOfSampleR, String inertMatterR, String otherCropSeedsR, String inOfAnalyst, String physicalPurity, String avWtOfSample, String avWtOfPureSeed, String avWtOfInert, String avOtherCrop, String otherCropPer, String inertPer, String weedCropSeeds, String weedCropSeedsR, String avWeedCrop, String weedPer) {


        JSONObject js = new JSONObject();


        try {
            js.put("labReferenceCode", labReferenceCode);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("dateOfTest", dateOfTest);

            js.put("cropName", crop_id);
            js.put("variety", variety);
            js.put("seedClass", seed_id);

            js.put("pureSeed", pureSeed);
            js.put("weightOfSample", weightOfSample);
            js.put("inertMatter", inertMatter);
            js.put("otherCropSeeds", otherCropSeeds);
            js.put("weed", weedCropSeeds);

            js.put("pureSeedR", pureSeedR);
            js.put("weightOfSampleR", weightOfSampleR);
            js.put("inertMatterR", inertMatterR);
            js.put("otherCropSeedsR", otherCropSeedsR);
            js.put("weedReplica", weedCropSeedsR);


            js.put("inOfAnalyst", inOfAnalyst);
            js.put("physicalPurity", physicalPurity);

            js.put("avWtOfSample", avWtOfSample);

            js.put("avWtOfPureSeed", avWtOfPureSeed);
            js.put("avWtOfInert", avWtOfInert);
            js.put("avOtherCrop", avOtherCrop);
            js.put("weedAverage", avWeedCrop);

            js.put("otherCropPer", otherCropPer);
            js.put("inertPer", inertPer);
            js.put("weedPercentage", weedPer);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().savePhysicalTestDetail(body).enqueue(new Callback<SaveTestResponse>() {
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

        int_issue_date = (LinearLayout) findViewById(R.id.lnt_issue_date);
        int_date_receipt = (LinearLayout) findViewById(R.id.int_date_receipt);
        int_date_test = (LinearLayout) findViewById(R.id.int_date_test);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);


        txt_date_receipt = (TextView) findViewById(R.id.txt_date_receipt);
        txt_date_test = (TextView) findViewById(R.id.txt_date_test);

        txt_crop_Name = (TextView) findViewById(R.id.txt_crop_Name);
        txt_variety = (TextView) findViewById(R.id.txt_variety);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_analys_calculate = (Button) findViewById(R.id.btn_analys_calculate);

        edt_lab = (EditText) findViewById(R.id.edt_lab);
        edt_seed_class = (EditText) findViewById(R.id.edt_seed_class);
        // Round 1
        edt_weight_sample = (EditText) findViewById(R.id.edt_weight_sample);
        edt_pure_seed = (EditText) findViewById(R.id.edt_pure_seed);
        edt_insert_matter = (EditText) findViewById(R.id.edt_insert_matter);
        edt_other_crop = (EditText) findViewById(R.id.edt_other_crop);
        edt_weed_seed = (EditText) findViewById(R.id.edt_weed_seed);

        txt_OC_avg = (TextView) findViewById(R.id.txt_OC_avg);
        txt_IM_avg = (TextView) findViewById(R.id.txt_IM_avg);
        txt_PS_avg = (TextView) findViewById(R.id.txt_PS_avg);
        txt_WS_avg = (TextView) findViewById(R.id.txt_WS_avg);

        crd_result = (CardView) findViewById(R.id.crd_result);
        avg_working_sample = (TextView) findViewById(R.id.avg_working_sample);
        txt_per_other_seed = (TextView) findViewById(R.id.txt_per_other_seed);
        txt_per_inert_seed = (TextView) findViewById(R.id.txt_per_inert_seed);
        txt_per_pure_seed = (TextView) findViewById(R.id.txt_per_pure_seed);
        txt_per_weed_seed = (TextView) findViewById(R.id.txt_per_weed_seed);

        txt_avg_pure_seed = (TextView) findViewById(R.id.txt_avg_pure_seed);
        txt_avg_inert_seed = (TextView) findViewById(R.id.txt_avg_inert_seed);
        txt_avg_other_seed = (TextView) findViewById(R.id.txt_avg_other_seed);
        txt_avg_weed_seed = (TextView) findViewById(R.id.txt_avg_weed_seed);
        // Round 2

        edt_weight_sample1 = (EditText) findViewById(R.id.edt_weight_sample1);
        edt_pure_seed1 = (EditText) findViewById(R.id.edt_pure_seed1);
        edt_insert_matter1 = (EditText) findViewById(R.id.edt_insert_matter1);
        edt_other_crop1 = (EditText) findViewById(R.id.edt_other_crop1);
        edt_weed_seed1 = (EditText) findViewById(R.id.edt_weed_seed1);

        txt_OC_avg1 = (TextView) findViewById(R.id.txt_OC_avg1);
        txt_IM_avg1 = (TextView) findViewById(R.id.txt_IM_avg1);
        txt_PS_avg1 = (TextView) findViewById(R.id.txt_PS_avg1);
        txt_WS_avg1 = (TextView) findViewById(R.id.txt_WS_avg1);


        edt_physical_purity = (EditText) findViewById(R.id.edt_physical_purity);
        edt_analyst = (EditText) findViewById(R.id.edt_analyst);
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);

        setCurrentDate();
        shimmer = new Shimmer();
        shimmer.start(txt_header);

        alertDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.spot_custom).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");
        crd_result.setVisibility(View.GONE);
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
        }
        if (DT == DateType.DOT) {
            txt_date_test.setText(year + "-" + month1 + "-" + day1);
        } else if (DT == DateType.DI) {
        }
    }
}