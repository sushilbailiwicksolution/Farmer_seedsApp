package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class CheckConnectivity {
    Context con;

    public static boolean isConnected(Context context) {

        boolean flag;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            flag = true;
        } else {
            Toast.makeText(context, "No Connection !! Please Check Your Network", Toast.LENGTH_LONG).show();
            flag = false;
        }
        return flag;

    }
}
