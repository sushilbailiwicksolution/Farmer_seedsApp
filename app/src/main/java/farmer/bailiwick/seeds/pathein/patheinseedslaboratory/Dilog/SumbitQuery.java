package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Dilog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;

/**
 * Created by Prince on 29-12-2017.
 */

public class SumbitQuery extends Dialog {
    Context mContext;

    public EditText  edt_email;
    public Button btn_Apply, btn_cancel;

    public SumbitQuery(Activity con) {
        super(con);
        this.mContext = con;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cst_dialog_forget_password);
        createIds();
        clickListner();
    }

    private void clickListner() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }



    private void createIds() {
        edt_email = (EditText) findViewById(R.id.edt_email);
        btn_Apply = (Button) findViewById(R.id.btn_Apply);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

    }
}
