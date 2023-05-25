package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.BaseActivity.loadService;
import static com.VijayAssayCenter.utils.Constants.GetVersionAndroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.VijayAssayCenter.BuildConfig;
import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

public class SplashScreenActivity extends AppCompatActivity {

    private String play_store_version_code;
    private String VERSION_NAME;
    String regresponse = "";
    String FristTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // FristTime=Common.getPreferenceString(SplashScreenActivity.this,"FristTime","");
        FristTime = Common.getPreferenceString(SplashScreenActivity.this, "UserName", "");
        VERSION_NAME = BuildConfig.VERSION_NAME;
        new CheckVersiton().execute();
        Common.setPreferenceString(SplashScreenActivity.this, "dialogopen", "dialogopen");
//        checkAndClearCache(this);
        clearCache(SplashScreenActivity.this);

    }

    private void checkAndClearCache(SplashScreenActivity splashScreenActivity) {

        if (Common.getPreferenceString(splashScreenActivity, "cache", "").equals("")) {
            clearCache(SplashScreenActivity.this);
            Common.setPreferenceString(splashScreenActivity, "cache", "cache");
        }else {
            Log.e("clearCache","Not First Time");
        }

    }

    private class CheckVersiton extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetVersionAndroid);
            request.addProperty("Obj", "{\"ClientId\":1}");
            Log.e("request", request.toString());
            try {
                regresponse = loadService(request, GetVersionAndroid);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.e("versionresponce", s);
                Log.e("VERSION_NAME", "" + VERSION_NAME);
                play_store_version_code = s.trim();

                try {
                    if (!play_store_version_code.isEmpty()) {
                        if (convertStringToInt(VERSION_NAME) != convertStringToInt(play_store_version_code)) {
                            openAppUpdateDialog(SplashScreenActivity.this);

                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                if(FristTime.equals("")){
//                    Intent intent=new Intent(SplashScreenActivity.this,EnterMobileNo.class);
//                    startActivity(intent);
//                    finish();
//                }else {

                                    Constants.page = 0;
                                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //}

                                }
                            }, 800);

                        }
                    } else {

                        Log.e("ololo.", "elseee");
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } catch (Exception e) {
                    Log.e("re_response", e.getMessage());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.e("re_response", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static int convertStringToInt(String version) {
        String versionString = version;
        String[] versionArray = versionString.split("\\.");
        int versionInt = Integer.parseInt(versionArray[0] + versionArray[1] + versionArray[2]);
        return versionInt;
    }
//    public static String loadService2(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/WebService.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

    @SuppressLint("SetTextI18n")
    public static void openAppUpdateDialog(final Activity activity) {
       AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.update_available, null);
        TextView update_txt = (TextView) dialogView.findViewById(R.id.update_txt);
        update_txt.setText("New version is available, please update now for exploring best features of" + " " + activity.getString(R.string.app_name));
        builder.setView(dialogView);
        final AlertDialog alt = builder.create();
        alt.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                activity.finish();
            }
        });
        alt.show();

        dialogView.findViewById(R.id.updateNow).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alt.dismiss();
                        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        myAppLinkToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            activity.startActivity(myAppLinkToMarket);
//                            clearCache(activity);

                            activity.finish();
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(activity.getApplicationContext(), "unable to find market app", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static void clearCache(Activity context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
