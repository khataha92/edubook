package UserUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lap on 6/9/16.
 */
public class Application {

    private static Activity currentActivity;

    private static Context context;

    public static void setCurrentActivity(Activity currentActivity) {

        Application.currentActivity = currentActivity;

    }

    public static boolean isNetworkConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE );

        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnected();

        return isConnected;

    }

    public static int getmShortAnimationDuration(){

        return context.getResources().getInteger(android.R.integer.config_shortAnimTime);

    }

    public static Activity getCurrentActivity() {

        return currentActivity;

    }

    public static void setContext(Context context) {

        Application.context = context;

    }

    public static Context getContext() {

        return context;

    }
}
