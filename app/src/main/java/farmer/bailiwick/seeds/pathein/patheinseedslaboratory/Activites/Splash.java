package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.R;
import farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support.SavedData;

;

/**
 * Created by Prince on 22-11-2016.
 */

public class Splash extends AppCompatActivity {
// requriment
/*
1    imei no verification
2    Get Version work in mobile
3    In Web Show Sc Area According to booth
4
*/


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    SharedPreferences pref;
    boolean isLogin;

    Shimmer shimmer;
    ShimmerTextView txt_header,txt_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ExceptionHandler.register(this, "http://uvcabs.esy.es/crash_log_vistarak/server.php");
        setContentView(R.layout.splash);
        txt_header = (ShimmerTextView) findViewById(R.id.txt_header);
        txt_version= (ShimmerTextView) findViewById(R.id.txt_version);
        shimmer = new Shimmer();
        shimmer.start(txt_header);
        shimmer.start(txt_version);

        isLogin = SavedData.getLogin();

        startSplash();
    }


    private void startSplash() {

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (isLogin) {
                    Intent i = new Intent(Splash.this, DashBoardActivity.class);

                    startActivity(i);

                } else {
                    Intent i = new Intent(Splash.this, LoginActivity.class);
                    startActivity(i);

                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}





