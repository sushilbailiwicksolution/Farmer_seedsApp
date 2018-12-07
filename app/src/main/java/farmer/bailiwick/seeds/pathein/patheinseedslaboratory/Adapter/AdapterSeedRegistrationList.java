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

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Model.Report_List_For_AddResponse;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;

/**
 * Created by Prince on 21-11-2018.
 */

public class AdapterSeedRegistrationList extends RecyclerView.Adapter<AdapterSeedRegistrationList.ViewHolder> {

    Context context;
    List<Report_List_For_AddResponse.ListReoprtData> ListBeanList;
    Activity activity;

    private ItemClickRecListInterface itemClickListener;

    public interface ItemClickRecListInterface {
        void onItemClick(int position);
    }

    public void updateList(List<Report_List_For_AddResponse.ListReoprtData> list) {
        ListBeanList = list;
        notifyDataSetChanged();
    }

    public AdapterSeedRegistrationList(Context context, List<Report_List_For_AddResponse.ListReoprtData> cartListBeanList, ItemClickRecListInterface itemClickRecListInterface) {
        this.context = context;
        this.ListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
        Log.e("my size", "Size : " + cartListBeanList.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cst_testing_reports, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_labrefrence_no.setText("Lab Ref No : " + ListBeanList.get(position).getLabReferenceNumber());
        holder.txt_name.setText("Sender's Name : " + ListBeanList.get(position).getName());
        holder.txt_date.setText(ListBeanList.get(position).getDate());

        holder.lnt_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_labrefrence_no, txt_date, txt_name;
        LinearLayout lnt_row;

        public ViewHolder(View itemView) {
            super(itemView);

            lnt_row = (LinearLayout) itemView.findViewById(R.id.lnt_row);
            txt_labrefrence_no = (TextView) itemView.findViewById(R.id.txt_lab_ref_no);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);


        }


    }

}
