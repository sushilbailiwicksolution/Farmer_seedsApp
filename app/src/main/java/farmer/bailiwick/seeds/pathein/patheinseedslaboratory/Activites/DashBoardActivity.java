package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONObject;

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.EmployeeDetailResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.ItagExtra;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.SavedData;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prince on 06-11-2018.
 */

public class DashBoardActivity extends RootActivity {
    Shimmer shimmer;
    ShimmerTextView txt_header;
    CardView crd_registration, crd_moisture_test, crd_physical_purity, crd_germiantion, crd_red_rice, crd_reports;
    Context mContext;
    LinearLayout lnt_head2, lnt_head1;

    RecyclerView recyclerview_Lab_ref;
    String employee_id, role_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mContext = DashBoardActivity.this;
        Log.e("Employee id", "Emplyee Id  ... " + SavedData.getUserID());
        employee_id = SavedData.getUserID();
        role_id = SavedData.getUSER_Role();

        createIDS();
        shimmer = new Shimmer();
        shimmer.start(txt_header);
        clickEvent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
    }

    private void clickEvent() {
        crd_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RegistrationActivity.class);
                startActivity(i);
            }
        });
        crd_moisture_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SearchLabrefrence.class);
                i.putExtra("operationType", ItagExtra.MoistureTest);
                i.putExtra("Page", "Moisture");
                startActivity(i);
            }
        });
        crd_physical_purity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SearchLabrefrence.class);
                i.putExtra("operationType", ItagExtra.PhysicalTest);
                i.putExtra("Page", "Physical Test");
                startActivity(i);
            }
        });
        crd_germiantion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SearchLabrefrence.class);
                i.putExtra("operationType", ItagExtra.germinationTest);
                i.putExtra("Page", "Germination");
                startActivity(i);
            }
        });
        crd_red_rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SearchLabrefrence.class);
                i.putExtra("operationType", ItagExtra.RedRiceTest);
                i.putExtra("Page", "Red Rice");
                startActivity(i);
            }
        });
        crd_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, SearchReportsStatus.class);

                startActivity(i);
            }
        });
    }

    private void createIDS() {
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);
        crd_registration = (CardView) findViewById(R.id.crd_registration);
        crd_moisture_test = (CardView) findViewById(R.id.crd_moisture_test);
        crd_physical_purity = (CardView) findViewById(R.id.crd_physical_purity_test);
        crd_germiantion = (CardView) findViewById(R.id.crd_germination);
        crd_red_rice = (CardView) findViewById(R.id.crd_red_rice);
        crd_reports = (CardView) findViewById(R.id.crd_reports);

        lnt_head2 = (LinearLayout) findViewById(R.id.lnt_head2);
        lnt_head1 = (LinearLayout) findViewById(R.id.lnt_head1);


        Log.e("roll id is ", ItagExtra.RollAdmin + "   Roll id is ==  " + role_id);
        if (role_id.equalsIgnoreCase(ItagExtra.RollAdmin)) {
            Log.e("roll id is ", "i m here...  " + role_id);
            crd_registration.setVisibility(View.GONE);
            lnt_head2.setVisibility(View.GONE);
            lnt_head1.setVisibility(View.GONE);

        } else if (role_id.equalsIgnoreCase(ItagExtra.RollEmployee)) {
            crd_reports.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //     getMenuInflater().inflate(R.menu.menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                Intent i = new Intent(DashBoardActivity.this, LoginActivity.class);
                Log.e("value", "" + item.getItemId());

                SavedData.saveLogin(false);
                SavedData.saveUSER_NAME("-1");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                return true;

            default:
                Log.e("value", "" + item.getItemId());

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        getUserDetail(SavedData.getUserID(), "fcmid");

        super.onResume();
    }

    private void getUserDetail(String userID, String fcmid) {
        JSONObject js = new JSONObject();

        try {
            js.put("id", userID);
            js.put("fcmId", fcmid);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

        //  alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().EmployeeDetailTask(body).enqueue(new Callback<EmployeeDetailResponse>() {
            @Override
            public void onResponse(Call<EmployeeDetailResponse> call, Response<EmployeeDetailResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                //        alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response != null) {
                    if (response.body().getStatusCode() == 0) {
                        Log.e("my Response  : ", response.body().getMessage().toString());

                        SavedData.saveUSER_NAME(response.body().getList().get(0).getFirstName().toString() + " " + response.body().getList().get(0).getLastName().toString());
                        SavedData.saveADDRESS(getfillValue(response.body().getList().get(0).getAddress(), ""));
                        SavedData.saveMOBILE1(getfillValue(response.body().getList().get(0).getMobileNo(), ""));

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(DashBoardActivity.this, LoginActivity.class);
                        SavedData.saveLogin(false);
                        SavedData.saveUSER_NAME("-1");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();

                }


            }

            private String getfillValue(String strValue, String defaultValue) {
                if (strValue == null) {
                    return defaultValue;
                } else {
                    return strValue;
                }

            }

            @Override
            public void onFailure(Call<EmployeeDetailResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
            }
        });

    }
}
