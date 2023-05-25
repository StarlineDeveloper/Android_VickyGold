package com.VijayAssayCenter;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class NetworkChangeReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Intent internetIntent=new Intent(Constant.FILTER_INTERNET);
            internetIntent.putExtra("ACTION",isOnline(context));
            LocalBroadcastManager.getInstance(context).sendBroadcast(internetIntent);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context con) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return false;
    }
}
