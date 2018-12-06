package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Report_status_Response;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;

/**
 * Created by Prince on 21-11-2018.
 */

public class AdapterAllSeedsReport extends RecyclerView.Adapter<AdapterAllSeedsReport.ViewHolder> {

    Context context;
    List<Report_status_Response.ListData> ListBeanList;
    Activity activity;

    private ReportClickRecListInterface reportClickListener;

    public interface ReportClickRecListInterface {
        void onItemClick(int position);
    }

    private GerminationClickRecListInterface germinationClickListener;

    public interface GerminationClickRecListInterface {
        void onGItemClick(int position);
    }

    public AdapterAllSeedsReport(Context context, List<Report_status_Response.ListData> cartListBeanList, ReportClickRecListInterface itemClickRecListInterface, GerminationClickRecListInterface gClick) {
        this.context = context;
        this.ListBeanList = cartListBeanList;
        reportClickListener = itemClickRecListInterface;
        germinationClickListener = gClick;

        Log.e("my size", "Size : " + cartListBeanList.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cst_status_reports, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_labrefrence_no.setText("Lab Ref No : " + ListBeanList.get(position).getLabReferenceCode());
        holder.txt_name.setText("Sender's Name : " + ListBeanList.get(position).getName());
        holder.txt_date.setText("");
        String Mtest = ListBeanList.get(position).getMoistureTest();
        String Ptest = ListBeanList.get(position).getPhysicalTest();
        String Gtest = ListBeanList.get(position).getGerminationTest();
        String Rtest = ListBeanList.get(position).getRedRiceTest();

        checkAndSet(holder.txt_germination_status, Gtest);
        checkAndSet(holder.txt_moisture_status, Mtest);
        checkAndSet(holder.txt_physical_status, Ptest);
        checkAndSet(holder.txt_red_rice_status, Rtest);

        boolean isReportAvail = isReportAvail(Gtest, Mtest, Ptest, Rtest);
        if (isReportAvail) {
            holder.txt_view_report.setVisibility(View.VISIBLE);
        } else {
            holder.txt_view_report.setVisibility(View.GONE);

        }
        holder.txt_view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportClickListener.onItemClick(position);
            }
        });
        holder.txt_germination_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                germinationClickListener.onGItemClick(position);
            }
        });

    }

    private boolean isReportAvail(String gtest, String mtest, String ptest, String rtest) {
        if (gtest.equalsIgnoreCase("3") || mtest.equalsIgnoreCase("3") || ptest.equalsIgnoreCase("3") || rtest.equalsIgnoreCase("3")) {
            return false;

        } else {
            return true;
        }
    }

    private void checkAndSet(TextView textview, String value) {
        if (value.equalsIgnoreCase("2")) {
            textview.setVisibility(View.GONE);

        } else if (value.equalsIgnoreCase("3")) {
            textview.setTextColor(context.getResources().getColor(R.color.app_red));
        } else if (value.equalsIgnoreCase("4")) {
            textview.setTextColor(context.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return ListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_labrefrence_no, txt_date, txt_name, txt_view_report, txt_moisture_status, txt_physical_status, txt_germination_status, txt_red_rice_status;
        LinearLayout lnt_row;

        public ViewHolder(View itemView) {
            super(itemView);

            lnt_row = (LinearLayout) itemView.findViewById(R.id.lnt_row);
            txt_labrefrence_no = (TextView) itemView.findViewById(R.id.txt_lab_ref_no);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);

            txt_view_report = (TextView) itemView.findViewById(R.id.txt_view_report);
            txt_moisture_status = (TextView) itemView.findViewById(R.id.txt_moisture_status);
            txt_physical_status = (TextView) itemView.findViewById(R.id.txt_physical_status);
            txt_germination_status = (TextView) itemView.findViewById(R.id.txt_germination_status);
            txt_red_rice_status = (TextView) itemView.findViewById(R.id.txt_red_rice_status);

        }


    }

}
