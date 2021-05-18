package tbc.uncagedmist.rationcard.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    public static final String WIN_URL = "https://894.win.qureka.com";

    public static String CurrentStateId;
    public static String CurrentStateName;

    public static String CurrentProductUrl;
    public static String CurrentProductName;

    public static final String PRIVACY_URL = "https://docs.google.com/document/d/1N9dzhvQlIAV3v3357SOrFBwZ3Y3k_A_K_mFbkEoQOqA/edit?usp=sharing";

    public static boolean isConnectedToInternet(Context context)    {

        ConnectivityManager connectivityManager = (
                ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)    {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null)   {

                for (int i = 0; i <info.length;i++)   {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)  {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
