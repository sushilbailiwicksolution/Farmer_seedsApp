/*
package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Adapter.AdapterAllSeedsReport;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Adapter.AdapterSeedRegistrationList;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Report_List_For_AddResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.CheckConnectivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.ItagExtra;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*/
/**
 * Created by Prince on 22-11-2018.
 *//*


public class AllSeedsStatus extends RootActivity implements DatePickerDialog.OnDateSetListener, AdapterAllSeedsReport. {


    TextView txt_start_date, txt_end_date, txt_title;
    ImageView img_search;
    LinearLayout lnt_start_date, lnt_end_date;
    RecyclerView recyclerview_Lab_ref;

    android.app.AlertDialog alertDialog;
    Context mContext;
    List<Report_List_For_AddResponse.ListReoprtData> ReportList;

    AdapterAllSeedsReport adptSeedsRegistered;

    DatePickerDialog datePickerDialog;


    enum DateType {DTstartDate, DTendDate}

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    SearchLabrefrence.DateType DT;

    String fromDate = year + "-" + (month + 1) + "-" + day;
    String toDate = year + "-" + (month + 1) + "-" + day;
    String operationType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_seeds_report);

        mContext = AllSeedsStatus.this;
        createIDS();
        getIntentValue();
        dateClickEvent();
        clickEvent();
    }

    private void getLabeRefrenceList(String fromDate, String toDate, String operationType) {

        JSONObject js = new JSONObject();

        try {
            js.put("operationType", operationType);
            js.put("startDate", fromDate);
            js.put("endDate", toDate);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Param ", "Param : " + js.toString());
        alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().GetListingTestResult(body).enqueue(new Callback<Report_List_For_AddResponse>() {
            @Override
            public void onResponse(Call<Report_List_For_AddResponse> call, Response<Report_List_For_AddResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    ReportList = response.body().getList();
                    //  adptSeedsRegistered.notifyDataSetChanged();
                    setDataCustomer();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<Report_List_For_AddResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });
    }

    private void setDataCustomer() {


        Log.e("cart get from database", "databse size : " + ReportList.size());

        adptSeedsRegistered = new AdapterAllSeedsReport(mContext, ReportList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_Lab_ref.setLayoutManager(mLayoutManager);
        recyclerview_Lab_ref.setItemAnimator(new DefaultItemAnimator());
        recyclerview_Lab_ref.setAdapter(adptSeedsRegistered);
    }

    private void clickEvent() {
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLabeRefrenceList(txt_start_date.getText().toString(), txt_end_date.getText().toString(), operationType);
            }
        });
    }


    private void dateClickEvent() {
        lnt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = SearchLabrefrence.DateType.DTstartDate;
                datePickerDialog.show();
            }
        });
        lnt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DT = SearchLabrefrence.DateType.DTendDate;
                datePickerDialog.show();
            }
        });

    }

    private void getIntentValue() {

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            operationType = "";
        } else {
            operationType = extras.getString("operationType");
            txt_title.setText(extras.getString("Page"));
        }
        if (!operationType.equalsIgnoreCase("")) {
            if (CheckConnectivity.isConnected(mContext)) {
                getLabeRefrenceList(fromDate, toDate, operationType);
            }


        } else {
            Toast.makeText(mContext, "List id is Not Get", Toast.LENGTH_SHORT).show();
        }
    }

    private void createIDS() {
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);


        lnt_start_date = (LinearLayout) findViewById(R.id.lnt_start_date);
        lnt_end_date = (LinearLayout) findViewById(R.id.lnt_end_date);

        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        txt_end_date = (TextView) findViewById(R.id.txt_end_date);
        txt_title = (TextView) findViewById(R.id.title_text);

        img_search = (ImageView) findViewById(R.id.img_search);
        recyclerview_Lab_ref = (RecyclerView) findViewById(R.id.recyclerview_Lab_ref);

        txt_start_date.setText(fromDate);
        txt_end_date.setText(toDate);

        alertDialog = new SpotsDialog.Builder().setContext(this).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");
        ReportList = new ArrayList<>();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.e("values", "   value dt" + DT + "  year : " + year + "  " + month + "   :   " + dayOfMonth);
        String month1 = "", day1 = "";
        if (String.valueOf(month + 1).length() == 1) month1 = "0" + (month + 1);
        else month1 = (month + 1) + "";
        if (String.valueOf(dayOfMonth).length() == 1) day1 = "0" + (dayOfMonth);
        else day1 = (dayOfMonth) + "";

        if (DT == SearchLabrefrence.DateType.DTstartDate) {
            Log.e("values", "   value dt" + DT + "  year : " + year + "  " + month + "   :   " + dayOfMonth);
            String mydate = year + "-" + month1 + "-" + day1;

            Log.e(mydate, mydate);

            txt_start_date.setText(mydate);

        } else if (DT == SearchLabrefrence.DateType.DTendDate) {
            String mydate = year + "-" + month1 + "-" + day1;
            Log.e(mydate, mydate);

            txt_end_date.setText(mydate);

        }
    }

    @Override
    public void onItemClick(int position) {
        Log.e("lab refernce no ", "lab refernce no =  " + ReportList.get(position).getLabReferenceNumber());
        Intent i;
        switch (operationType) {
            case ItagExtra.germinationTest:
                i = new Intent(AllSeedsStatus.this, GerminationTest.class);
                i.putExtra("operationType", operationType);
                i.putExtra("Labrefrence", ReportList.get(position).getLabReferenceNumber());

                startActivity(i);
                break;
            case ItagExtra.PhysicalTest:
                i = new Intent(AllSeedsStatus.this, PhysicalPurityActivity.class);
                i.putExtra("operationType", operationType);
                i.putExtra("Labrefrence", ReportList.get(position).getLabReferenceNumber());

                startActivity(i);

                break;
            case ItagExtra.RedRiceTest:
                i = new Intent(AllSeedsStatus.this, RedRiceTest.class);
                i.putExtra("operationType", operationType);
                i.putExtra("Labrefrence", ReportList.get(position).getLabReferenceNumber());

                startActivity(i);

                break;
            case ItagExtra.MoistureTest:
                i = new Intent(AllSeedsStatus.this, MoistureTestActivity.class);
                i.putExtra("operationType", operationType);
                i.putExtra("Labrefrence", ReportList.get(position).getLabReferenceNumber());

                startActivity(i);

                break;
            default:

        }

    }
}
*/
