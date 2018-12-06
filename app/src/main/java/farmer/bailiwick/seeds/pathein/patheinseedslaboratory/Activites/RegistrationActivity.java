package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.CustomDialog.SweetAlertDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.CropResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Regionresponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SeedsResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Township_Response;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.VarityResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Village_T_Response;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.village_Response;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prince on 12-11-2018.
 */

public class RegistrationActivity extends RootActivity implements DatePickerDialog.OnDateSetListener {
    Context mContext;

    LinearLayout lnt_date_receipt, lnt_year_of_production;
    TextView txt_date_receipt, txt_year_of_production, txt_varity;
    Spinner spnr_crop, spnr_season, spnr_seeds;
    Spinner spnr_sender_catagory, spnr_region, spnr_village_tract, spnr_village, spnr_township;

    AutoCompleteTextView spnr_varity;

    Button btn_submit;
    EditText edt_smple_qty, edt_lot_no, edt_sender_name, edt_varity;
    RadioGroup RDGcrop_status;
    RadioButton rb_good, rb_not_good;
    CheckBox chk_moisture, chk_physical, chk_germination, chk_red_rice, chk_alltest;
    Shimmer shimmer;
    ShimmerTextView txt_header;

    DatePickerDialog datePickerDialog;

    ArrayList<String> LIST_crop_name, LIST_varity_name, LIST_region_name, LIST_season_name, List_Seeds;
    ArrayAdapter Spinner_Crop_adapter, Spinner_varity_adapter, Spinner_region_adapter, Spinner_Season_adapter, Spinner_Seeds_adapter;
    ArrayAdapter Spinner_sender_catagory_adapter, Spinner_town_adapter, Spinner_village_adapter, Spinner_tract_adapter;

    ArrayList<String> List_sender_catagory;

    enum DateType {DR, DI}

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    DateType DT;
    android.app.AlertDialog alertDialog;

    ArrayList<String> List_Township, List_Village, List_Trackt;

    List<CropResponse.CropData> CropList;
    List<Regionresponse.ListRegion> RegionList;
    List<VarityResponse.ListVarity> VarityList;
    List<SeedsResponse.ListSeeds> SeedsList;


    List<Township_Response.ListData> townsList;
    List<Village_T_Response.ListData> tractList;
    List<village_Response.ListData> villageList;


    boolean isVaritySpinner, isTract = false;

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
        setContentView(R.layout.register_activity);
        mContext = RegistrationActivity.this;

        createIDS();
        setSpinnerData();
        clickevent();
        spinnerClickEvent();
        setAddressVisiblity(View.VISIBLE, View.GONE, View.GONE);
    }


    private void clickevent() {
        lnt_year_of_production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(mContext, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        txt_year_of_production.setText(Integer.toString(selectedYear));
                        year = selectedYear;
                    }
                }, year, 0);

                builder.showYearOnly().setYearRange(1990, 2030).build().show();
            }

        });

        lnt_date_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = DateType.DR;
                datePickerDialog.show();
            }
        });

        chk_alltest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SetAllTestChecked(isChecked);

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        if (edt_sender_name.getText().toString().trim().equalsIgnoreCase("")) {
            edt_sender_name.setError("requried");
            edt_sender_name.requestFocus();
            return;

        } else if (txt_date_receipt.getText().toString().equalsIgnoreCase("")) {
            txt_date_receipt.setError("Please Select Date");
            txt_date_receipt.requestFocus();
            return;
        } else if (spnr_sender_catagory.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Sender Catagory", Toast.LENGTH_LONG).show();
            spnr_sender_catagory.requestFocus();
            return;

        } else if (isTract) {
            Toast.makeText(getApplicationContext(), "Please Fill Address", Toast.LENGTH_LONG).show();
            spnr_region.requestFocus();
            return;

        } else if (spnr_crop.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Crop", Toast.LENGTH_LONG).show();
            spnr_crop.requestFocus();
            return;

        } else if (spnr_crop.getSelectedItem().toString().trim().equalsIgnoreCase("Rice") && spnr_varity.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(getApplicationContext(), "Please Select Varity", Toast.LENGTH_LONG).show();
            spnr_varity.setError("Please Select Varity");
            spnr_varity.requestFocus();
            return;

        } else if (!spnr_crop.getSelectedItem().toString().trim().equalsIgnoreCase("Rice") && edt_varity.getText().toString().trim().equalsIgnoreCase("")) {
            edt_varity.setError("Please Select Varity");
            edt_varity.requestFocus();
            return;

        }
        if (spnr_region.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Region", Toast.LENGTH_LONG).show();
            spnr_region.requestFocus();

            return;

        } else if (txt_year_of_production.getText().toString().equalsIgnoreCase(getResources().getString(R.string.year))) {
            txt_year_of_production.setError("Please Select Year");
            txt_year_of_production.requestFocus();
            return;
        } else if (spnr_season.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Season", Toast.LENGTH_LONG).show();
            spnr_season.requestFocus();

            return;

        } else if (edt_lot_no.getText().toString().trim().equalsIgnoreCase("")) {
            edt_lot_no.setError("requried");
            edt_lot_no.requestFocus();

            return;


        } else if (spnr_seeds.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Seed", Toast.LENGTH_LONG).show();
            spnr_seeds.requestFocus();
            return;


        } else if (!isAnyboxChecked()) {
            Toast.makeText(getApplicationContext(), "Please Select TEST to be done", Toast.LENGTH_LONG).show();
            return;

        } else if (edt_smple_qty.getText().toString().trim().equalsIgnoreCase("")) {

            edt_smple_qty.setError("requried");
            edt_smple_qty.requestFocus();

            return;

        } else if (RDGcrop_status.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select Product Quality", Toast.LENGTH_LONG).show();
            return;
        } else if (isVaritySpinner && spnr_varity.getText().toString().trim().equalsIgnoreCase("")) {
            //       Log.e("i m heree ...1111 ","posoition  "+spnr_varity.getSelectedItemPosition());

            Toast.makeText(getApplicationContext(), "Please Select Varity", Toast.LENGTH_LONG).show();
            return;

        } else if (!isVaritySpinner && edt_varity.getText().toString().trim().equalsIgnoreCase("")) {
            edt_varity.setError("Requried");
            return;


        } else {
            Log.e("All Set ", "All Set ");
            Toast.makeText(getApplicationContext(), "All data is selected No save it", Toast.LENGTH_SHORT).show();
// user
            String senderName, township, senderCatagory, villageTrack, village, dateOfReceipt, crop, varityName, region, season_name, lotNo, seed_name, sampleQty, packing, year, germinationTest, moistureTest, physicalPurityTest, redRiceTest, allTest;
            senderName = edt_sender_name.getText().toString().trim();
            senderCatagory = "" + (spnr_sender_catagory.getSelectedItemPosition() );
// address
            township = townsList.get(spnr_township.getSelectedItemPosition() - 1).getId().toString();
            villageTrack = tractList.get(spnr_village_tract.getSelectedItemPosition() - 1).getId().toString();
            village = villageList.get(spnr_village.getSelectedItemPosition() - 1).getId().toString();
            region = RegionList.get(spnr_region.getSelectedItemPosition() - 1).getId().toString();
            dateOfReceipt = txt_date_receipt.getText().toString();
            crop = CropList.get(spnr_crop.getSelectedItemPosition() - 1).getId().toString();
            season_name = getSeason();
            seed_name = SeedsList.get(spnr_seeds.getSelectedItemPosition() - 1).getId().toString();
            if (isVaritySpinner) {
                varityName = spnr_varity.getText().toString().trim();
            } else {
                varityName = edt_varity.getText().toString();
            }
            lotNo = edt_lot_no.getText().toString().trim();
            sampleQty = edt_smple_qty.getText().toString().trim();
            year = txt_year_of_production.getText().toString();
            int selectedId = RDGcrop_status.getCheckedRadioButtonId();
            Log.e("radio button value", "" + selectedId);
            packing = "" + 0;

            int id = RDGcrop_status.getCheckedRadioButtonId();
            View radioButton = RDGcrop_status.findViewById(id);
            int radioId = RDGcrop_status.indexOfChild(radioButton);

            RadioButton btn = (RadioButton) RDGcrop_status.getChildAt(radioId);
            String selection = (String) btn.getText();
            packing = selection;


            allTest = "0";
            germinationTest = checkedtest(chk_germination);
            redRiceTest = checkedtest(chk_red_rice);
            physicalPurityTest = checkedtest(chk_physical);
            moistureTest = checkedtest(chk_moisture);


            RegisterSeeds(senderName, township, senderCatagory, villageTrack, village, dateOfReceipt, crop, varityName, region, season_name, lotNo, seed_name, sampleQty, packing, year, germinationTest, moistureTest, physicalPurityTest, redRiceTest, allTest);


        }

    }

    private String checkedtest(CheckBox chkBox) {

        if (chkBox.isChecked()) {
            return "1";
        } else {
            return "0";
        }
    }


    private String getSeason() {

        String value = spnr_season.getSelectedItem().toString();
        if (value.equalsIgnoreCase("Summer")) {
            return "1";
        } else {
            return "2";
        }


    }

    private void RegisterSeeds(String senderName, String township, String senderCatagory, String villageTrack, String village, String dateOfReceipt, String crop, String variety, String region, String season, String lotNo, String seedClass, String sampleQty, String packing, String year, String germinationTest, String moistureTest, String physicalPurityTest, String redRiceTest, String allTest) {


        JSONObject js = new JSONObject();

        try {
            js.put("senderName", senderName);
            // js.put("address", address);
            js.put("township", township);
            js.put("senderCatagory", senderCatagory);
            js.put("villageTrack", villageTrack);
            js.put("village", village);
            js.put("dateOfReceipt", dateOfReceipt);
            js.put("crop", crop);
            js.put("variety", variety);
            js.put("region", region);
            js.put("season", season);
            js.put("lotNo", lotNo);
            js.put("seedClass", seedClass);
            js.put("sampleQty", sampleQty);
            js.put("packing", packing);
            js.put("year", year);
            js.put("germinationTest", germinationTest);
            js.put("moistureTest", moistureTest);
            js.put("physicalPurityTest", physicalPurityTest);
            js.put("redRiceTest", redRiceTest);
            js.put("allTest", allTest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().SaveSample(body).enqueue(new Callback<Regionresponse>() {
            @Override
            public void onResponse(Call<Regionresponse> call, Response<Regionresponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                //        alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    Log.e("my Response  : ", response.body().getMessage().toString());

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Registration Succesfull!").setContentText("Please Note Your LAB REF No :  " + response.body().getMessage().toString()).show();
                    clearData();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<Regionresponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }

    private void SetAllTestChecked(boolean isChecked) {

        chk_red_rice.setChecked(isChecked);
        chk_germination.setChecked(isChecked);
        chk_physical.setChecked(isChecked);
        chk_moisture.setChecked(isChecked);
    }

    private void clearData() {

        edt_sender_name.setText("");
        // txt_date_receipt.setText("");
        setCurrentDate();
        spnr_crop.setSelection(0);
        spnr_region.setSelection(0);
        txt_year_of_production.setText("YEAR");
        spnr_season.setSelection(0);
        edt_lot_no.setText("");
        spnr_seeds.setSelection(0);
        chk_alltest.setChecked(false);
        SetAllTestChecked(false);
        edt_smple_qty.setText("");
        rb_good.setChecked(false);
        rb_not_good.setChecked(false);
        edt_sender_name.requestFocus();

    }

    private void spinnerClickEvent() {
        spnr_village_tract.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    isTract = false;
                } else {
                    if (spnr_village_tract.getVisibility() == View.VISIBLE) {
                        isTract = true;

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnr_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("position", "my position" + position);
                Log.e("position", "Size  " + CropList.size() + "    position " + (position - 1));
                if (position > 0) {
                    Log.e("position", "Size  " + CropList.get(position - 1).getCrop());

                }

                if (position == 0) {
                    setVarityVisiblity(false, false);
                } else if (CropList.get(position - 1).getCrop().equalsIgnoreCase("Rice")) {
                    getVarity(CropList.get(position - 1).getId());


                } else {
                    setVarityVisiblity(false, true);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnr_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setAddressVisiblity(View.GONE, View.GONE, View.GONE);
                } else {
                    setAddressVisiblity(View.GONE, View.GONE, View.GONE);
                    getTownship(RegionList.get(position - 1).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnr_township.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setAddressVisiblity(View.VISIBLE, View.GONE, View.GONE);
                } else {
                    setAddressVisiblity(View.VISIBLE, View.GONE, View.GONE);
                    getVillage(townsList.get(position - 1).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setAddressVisiblity(View.VISIBLE, View.VISIBLE, View.GONE);
                } else {
                    setAddressVisiblity(View.VISIBLE, View.VISIBLE, View.GONE);
                    getTract(villageList.get(position - 1).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTract(Integer id) {
        alertDialog.show();

        JSONObject js = new JSONObject();

        try {
            js.put("id", "" + id);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), js.toString());
        // Dialog start
        RetrofitApiClient.get().getTract(body).enqueue(new Callback<Village_T_Response>() {
            @Override
            public void onResponse(Call<Village_T_Response> call, Response<Village_T_Response> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    tractList = response.body().getList();
                    setTractList();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<Village_T_Response> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }

    private void setTractList() {
        List_Trackt = new ArrayList<>();
        List_Trackt.add("Select Village Tract");
        for (int i = 0; i < tractList.size(); i++) {
            List_Trackt.add(tractList.get(i).getName());

        }
        Spinner_tract_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, List_Trackt);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_tract_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_village_tract.setAdapter(Spinner_tract_adapter);
        setAddressVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE);
    }

    private void getVillage(Integer id) {
        alertDialog.show();

        JSONObject js = new JSONObject();

        try {
            js.put("id", "" + id);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), js.toString());
        // Dialog start
        RetrofitApiClient.get().getVillage(body).enqueue(new Callback<village_Response>() {
            @Override
            public void onResponse(Call<village_Response> call, Response<village_Response> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    villageList = response.body().getList();
                    setVillage();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<village_Response> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });

    }

    private void setVillage() {
        List_Village = new ArrayList<>();
        List_Village.add("Select Village");
        for (int i = 0; i < villageList.size(); i++) {
            List_Village.add(villageList.get(i).getName());

        }
        Spinner_village_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, List_Village);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_village_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_village.setAdapter(Spinner_village_adapter);
        setAddressVisiblity(View.VISIBLE, View.VISIBLE, View.GONE);
    }

    private void getTownship(Integer id) {

        alertDialog.show();

        JSONObject js = new JSONObject();

        try {
            js.put("id", "" + id);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), js.toString());
        // Dialog start
        RetrofitApiClient.get().getTownship(body).enqueue(new Callback<Township_Response>() {
            @Override
            public void onResponse(Call<Township_Response> call, Response<Township_Response> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    townsList = response.body().getList();
                    setTownship();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<Township_Response> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });
    }

    private void setTownship() {
        List_Township = new ArrayList<>();
        List_Township.add("Select Town");
        for (int i = 0; i < townsList.size(); i++) {
            List_Township.add(townsList.get(i).getName());

        }
        Spinner_town_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, List_Township);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_town_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_township.setAdapter(Spinner_town_adapter);
        setAddressVisiblity(View.VISIBLE, View.GONE, View.GONE);
    }

    private void setAddressVisiblity(int isTownship, int isVillage, int isTract) {


        spnr_township.setVisibility(isTownship);
        spnr_village_tract.setVisibility(isTract);
        spnr_village.setVisibility(isVillage);

    }


    private void setSpinnerData() {
        getCrop_Data();
        setSeason_Data();
    }

    private void getCrop_Data() {
        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "");
        // Dialog start
        RetrofitApiClient.get().getCrop(body).enqueue(new Callback<CropResponse>() {
            @Override
            public void onResponse(Call<CropResponse> call, Response<CropResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    CropList = response.body().getList();
                    setCropData();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<CropResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });
    }

    private void getVarity(Integer cropId) {

        JSONObject js = new JSONObject();

        try {
            js.put("id", "" + cropId);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().getVarity(body).enqueue(new Callback<VarityResponse>() {
            @Override
            public void onResponse(Call<VarityResponse> call, Response<VarityResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    VarityList = response.body().getList();
                    setVerity_data();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<VarityResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });
    }

    private void setSeason_Data() {
        LIST_season_name = new ArrayList<>();
        LIST_season_name.add("Select Season");
        LIST_season_name.add("Rainy");
        LIST_season_name.add("Summer");
        Spinner_Season_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, LIST_season_name);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_Season_adapter.setDropDownViewResource(R.layout.spinner_layout_white);

        spnr_season.setAdapter(Spinner_Season_adapter);

        List_sender_catagory = new ArrayList<>();
        List_sender_catagory.add("Select Catagory");
        List_sender_catagory.add("Township Seed Inspectors");
        List_sender_catagory.add("Seed Companies");
        List_sender_catagory.add("Township Seed Inspectors for Seed Grower Associations Members");
        List_sender_catagory.add("NGOs");
        List_sender_catagory.add("Seed Farms");

        Spinner_sender_catagory_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, List_sender_catagory);
        Spinner_sender_catagory_adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnr_sender_catagory.setAdapter(Spinner_sender_catagory_adapter);
    }

    private void setVerity_data() {
        try {


            LIST_varity_name = new ArrayList<>();
            if (LIST_varity_name.size() > 0) {
                LIST_varity_name.clear();
            }
//            LIST_varity_name.add("Select Variety");
            for (int i = 0; i < VarityList.size(); i++) {
                LIST_varity_name.add(VarityList.get(i).getVariety());

            }


            Spinner_varity_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, LIST_varity_name);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
            Spinner_varity_adapter.setDropDownViewResource(R.layout.spinner_layout_white);

            spnr_varity.setAdapter(Spinner_varity_adapter);

            spnr_varity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spnr_varity.showDropDown();
                }
            });

            setVarityVisiblity(true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setregion_Data() {
        LIST_region_name = new ArrayList<>();
        LIST_region_name.add("Select Region");
        for (int i = 0; i < RegionList.size(); i++) {
            LIST_region_name.add(RegionList.get(i).getRegion());

        }

        Spinner_region_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, LIST_region_name);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_region_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_region.setAdapter(Spinner_region_adapter);
        getSeeds();
    }

    private void getSeeds() {


        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "");
        // Dialog start
        RetrofitApiClient.get().getSeeds(body).enqueue(new Callback<SeedsResponse>() {
            @Override
            public void onResponse(Call<SeedsResponse> call, Response<SeedsResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    SeedsList = response.body().getList();
                    setSeeds_Data();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<SeedsResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }

    private void setSeeds_Data() {
        List_Seeds = new ArrayList<>();
        List_Seeds.add("Seed Class");
        for (int i = 0; i < SeedsList.size(); i++) {
            List_Seeds.add(SeedsList.get(i).getSeedClass());

        }
        Spinner_Seeds_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, List_Seeds);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_Seeds_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_seeds.setAdapter(Spinner_Seeds_adapter);


    }

    private void setCropData() {
        LIST_crop_name = new ArrayList<>();
        LIST_crop_name.add("Select Crop");
        for (int i = 0; i < CropList.size(); i++) {
            LIST_crop_name.add(CropList.get(i).getCrop());

        }
        Spinner_Crop_adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout_white_text, LIST_crop_name);
//        Spinner_Religion_adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner_Crop_adapter.setDropDownViewResource(R.layout.spinner_layout_white);
        spnr_crop.setAdapter(Spinner_Crop_adapter);

        getRegion();

    }

    private void getRegion() {
        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "");
        // Dialog start
        RetrofitApiClient.get().getregion(body).enqueue(new Callback<Regionresponse>() {
            @Override
            public void onResponse(Call<Regionresponse> call, Response<Regionresponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    RegionList = response.body().getList();
                    setregion_Data();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<Regionresponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });
    }

    private boolean isAnyboxChecked() {
        boolean isAnyCheck = false;

        if (chk_alltest.isChecked() || chk_germination.isChecked() || chk_physical.isChecked() || chk_red_rice.isChecked() || chk_moisture.isChecked()) {
            isAnyCheck = true;
        }

        return isAnyCheck;
    }

    private void createIDS() {
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);

        lnt_date_receipt = (LinearLayout) findViewById(R.id.lnt_date_receipt);
        lnt_year_of_production = (LinearLayout) findViewById(R.id.lnt_year_of_production);

        txt_date_receipt = (TextView) findViewById(R.id.txt_date_receipt);
        txt_year_of_production = (TextView) findViewById(R.id.txt_year_of_production);
        txt_varity = (TextView) findViewById(R.id.txt_varity);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        spnr_crop = (Spinner) findViewById(R.id.spnr_crop);
        spnr_varity = (AutoCompleteTextView) findViewById(R.id.spnr_varity);
        spnr_region = (Spinner) findViewById(R.id.spnr_region);
        spnr_season = (Spinner) findViewById(R.id.spnr_season);
        spnr_seeds = (Spinner) findViewById(R.id.spnr_seeds);

        edt_smple_qty = (EditText) findViewById(R.id.edt_smple_qty);
        edt_lot_no = (EditText) findViewById(R.id.edt_lot_no);
        edt_sender_name = (EditText) findViewById(R.id.edt_sender_name);
        edt_varity = (EditText) findViewById(R.id.edt_varity);

        RDGcrop_status = (RadioGroup) findViewById(R.id.RDGcrop_status);
        rb_good = (RadioButton) findViewById(R.id.rb_good);
        rb_not_good = (RadioButton) findViewById(R.id.rb_not_good);

        chk_moisture = (CheckBox) findViewById(R.id.chk_moisture);
        chk_physical = (CheckBox) findViewById(R.id.chk_physical);
        chk_germination = (CheckBox) findViewById(R.id.chk_germination);
        chk_red_rice = (CheckBox) findViewById(R.id.chk_red_rice);
        chk_alltest = (CheckBox) findViewById(R.id.chk_alltest);

        spnr_sender_catagory = (Spinner) findViewById(R.id.spnr_sender_catagory);
        spnr_village_tract = (Spinner) findViewById(R.id.spnr_village_tract);
        spnr_village = (Spinner) findViewById(R.id.spnr_village);
        spnr_township = (Spinner) findViewById(R.id.spnr_township);


        CropList = new ArrayList<>();
        RegionList = new ArrayList<>();
        VarityList = new ArrayList<>();

        townsList = new ArrayList<>();
        villageList = new ArrayList<>();
        tractList = new ArrayList<>();

        setCurrentDate();


        shimmer = new Shimmer();
        shimmer.start(txt_header);

        alertDialog = new SpotsDialog.Builder().setContext(this).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");
        setVarityVisiblity(false, false);

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

        txt_date_receipt.setText(year + "-" + month1 + "-" + day1);
    }

    private void setVarityVisiblity(boolean isSpinner, boolean isEdittext) {
        Log.e("values ", "edit : " + isEdittext + "  :  spnr  : " + isSpinner);

        if (isEdittext) {
            edt_varity.setVisibility(View.VISIBLE);
            spnr_varity.setVisibility(View.GONE);
            txt_varity.setVisibility(View.GONE);
            isVaritySpinner = false;
        }

        if (isSpinner) {
            edt_varity.setVisibility(View.GONE);
            spnr_varity.setVisibility(View.VISIBLE);
            txt_varity.setVisibility(View.VISIBLE);
            isVaritySpinner = true;
        }

        if (!isSpinner && !isEdittext) {

            edt_varity.setVisibility(View.GONE);
            spnr_varity.setVisibility(View.GONE);
            txt_varity.setVisibility(View.GONE);
            isVaritySpinner = false;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String month1 = "", day1 = "";
        if (String.valueOf(month + 1).length() == 1) month1 = "0" + (month + 1);
        else month1 = (month + 1) + "";

        if (String.valueOf(dayOfMonth).length() == 1) {
            day1 = "0" + (dayOfMonth);
        } else {
            day1 = (dayOfMonth) + "";
        }


        if (DT == DateType.DR) {
            txt_date_receipt.setText(year + "-" + month1 + "-" + day1);

        } else if (DT == DateType.DI) {
            //   txt_issue_date.setText(year + "-" + month1 + "-" + day1);

        }
    }
}
