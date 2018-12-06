package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Dilog.SumbitQuery;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.SaveLoginResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.RootActivity;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.SavedData;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.webservices.RetrofitApiClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prince on 01-09-2017.
 */

public class LoginActivity extends RootActivity {

    private Context mContext;

    Button btn_login;
    EditText edt_user_name, edt_password;
    android.app.AlertDialog alertDialog;
    TextView txt_forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        createids();
        click_evnet();

    }


    private void click_evnet() {
        txt_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowForgetDialog();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username, password;
                username = edt_user_name.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    edt_user_name.setError("Requried");
                    return;

                } else if (password.equalsIgnoreCase("")) {
                    edt_password.setError("Requried");
                    return;

                } else {
                    LoginTask(username, password);
                }


            }
        });


    }

    private void ShowForgetDialog() {
        final SumbitQuery sub = new SumbitQuery(LoginActivity.this);
        sub.show();
        sub.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub.dismiss();
            }
        });
    }


    private void LoginTask(String username, String password) {

        JSONObject js = new JSONObject();

        try {
            js.put("emailId", username);
            js.put("password", password);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("Params ", "Values  : " + js.toString());

//          alertDialog.show();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (js.toString()));
        // Dialog start
        RetrofitApiClient.get().LoginTask(body).enqueue(new Callback<SaveLoginResponse>() {
            @Override
            public void onResponse(Call<SaveLoginResponse> call, Response<SaveLoginResponse> response) {
                // dialog End
                // Log.e("my Response  : ","ppp  :  "+ response.body().toString());
                Log.e("my Response  : ", "Rajesh  :  " + new Gson().toJson(response));
                //        alertDialog.dismiss();
                Log.e("my Response  : ", response.body().getMessage().toString());
                if (response.body().getStatusCode() == 0) {
                    Log.e("my Response  : ", response.body().getMessage().toString());
                    Log.e("my employee  : ", response.body().getEmployeeId().toString());

                    SavedData.setUserID(response.body().getEmployeeId().toString());
                    SavedData.setUSER_Role(response.body().getRoleId().toString());
                    SavedData.saveLogin(true);

                    Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<SaveLoginResponse> call, Throwable t) {
                Log.e("my Response  : ", t.toString());
                alertDialog.dismiss();
            }
        });


    }


    @Override
    public void onBackPressed() {
        finish();
    }


    private void createids() {
        btn_login = (Button) findViewById(R.id.btn_signin);
        edt_user_name = (EditText) findViewById(R.id.edt_user_name);
        edt_password = (EditText) findViewById(R.id.edt_password);
        txt_forgetPassword = (TextView) findViewById(R.id.txt_forget);

        edt_user_name.setText("emp1@gmail.com");
        edt_password.setText("123456");

        alertDialog = new SpotsDialog.Builder().setContext(this).build();
        alertDialog.setTitle("Seeds");
        alertDialog.setMessage("Please wait.....");
    }

}
