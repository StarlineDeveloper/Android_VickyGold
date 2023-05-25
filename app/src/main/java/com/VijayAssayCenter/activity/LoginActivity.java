package com.VijayAssayCenter.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
//import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LoginActivity extends BaseActivity {


    TextView btnLogin;

    String LoginID = "";
    String Password = "";
    String mac = "";
    String devicetoken = "";
    String devicetype = "Android";

    EditText et_login_id;
    EditText ed_pass;

    String LoginRemider = "Yes";


    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        et_login_id = (EditText) findViewById(R.id.et_login_id);
        ed_pass = (EditText) findViewById(R.id.ed_pass);


        checkbox = (CheckBox) findViewById(R.id.checkbox);
        //checkbox = new CheckBox(this, null, R.style.txt_black_16);

        checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkbox.isChecked()) {
                    LoginRemider = "Yes";
                } else {
                    LoginRemider = "No";

                }


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("devicetoken", devicetoken);
                Log.e("mac", mac);
                if (et_login_id.getText().toString().trim().length() == 0) {
                    et_login_id.setError("Enter Login id");
                    et_login_id.requestFocus();
                } else if (ed_pass.getText().toString().trim().length() == 0) {
                    ed_pass.setError("Enter Password");
                    ed_pass.requestFocus();
                } else if (isConnected()) {


                    mac = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//                    devicetoken = FirebaseInstanceId.getInstance().getToken();
                    LoginID = et_login_id.getText().toString().trim();
                    Password = ed_pass.getText().toString().trim();

                    LoginApi();


                } else {
                    Toast.makeText(LoginActivity.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    private void LoginApi() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetAdminLoginDetailMobile);
                request.addProperty("LoginID", LoginID);
                request.addProperty("Password", Password);
                request.addProperty("mac", mac);
                request.addProperty("devicetoken", devicetoken);
                request.addProperty("devicetype", devicetype);

                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.GetAdminLoginDetailMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("LoginResponce", response);

                    dismissProgress();

                    JSONObject LoginResponce = new JSONObject(response);

                    if (LoginResponce.getString("Status").equals("true")) {
                        String AdminId = LoginResponce.getString("AdminId");
                        String UserName = LoginResponce.getString("UserName");
                        String Name = LoginResponce.getString("Name");
                        String LastLoginTime = LoginResponce.getString("LastLoginTime");
                        String DeviceToken = LoginResponce.getString("DeviceToken");
                        String mac = LoginResponce.getString("mac");
                        String RemainGold = LoginResponce.getString("RemainGold");
                        String RemainSilver = LoginResponce.getString("RemainSilver");


                        if (LoginRemider.equals("Yes")) {
                            Common.setPreferenceString(LoginActivity.this, "AdminId", AdminId);
                        } else {
                            Constants.AdminId = AdminId;
                        }

                        Common.setPreferenceString(LoginActivity.this, "UserName", UserName);
                        Common.setPreferenceString(LoginActivity.this, "Name", Name);
                        Common.setPreferenceString(LoginActivity.this, "LastLoginTime", LastLoginTime);
                        Common.setPreferenceString(LoginActivity.this, "DeviceToken", DeviceToken);
                        Common.setPreferenceString(LoginActivity.this, "mac", mac);


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        String msg = LoginResponce.getString("msg");


                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Invalid Credentials")
                                .setMessage(msg)
                                .setPositiveButton("Cancal", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                    }
                                })
                                .show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LoginResponce", e.getMessage());

                }
            }
        }.execute();
    }

    public static String loadServiceForBullion(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("http://192.168.3.23:1155/webservice/MobileAdmin.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }


}
