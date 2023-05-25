package com.VijayAssayCenter.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.VijayAssayCenter.R;


/**
 * Created by root on 1/3/16.
 */
public class NetworkChangeReciever extends BroadcastReceiver {

    Context context;


    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.e("Your  Internate is off", "this is called");

       AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Internet Connection Error")
                .setMessage("Please connect to working Internet connection..!")
                .setPositiveButton(R.string.hint_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        context.startActivity(intent);

                    }
                })
                .create();
        dialog.show();
        Button ybutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ybutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));

    }


}
