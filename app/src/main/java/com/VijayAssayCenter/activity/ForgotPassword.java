package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.Constants.ForgotPasswardApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ForgotPassword extends BaseActivity {


    EditText et_user_id;
    EditText et_mobile;
    LinearLayout lay_login;
    LinearLayout lay_back;

    int UserId;
    String Mobileno="";
    String ObjVariable="";
    String regresponse="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        et_user_id=findViewById(R.id.et_user_id);
        et_mobile=findViewById(R.id.et_mobile);
        lay_login=findViewById(R.id.lay_login);
        lay_back=findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lay_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mobile.getText().toString().trim().length() == 0) {
                    et_mobile.setError("Please Enter Mobile Number");
                    et_mobile.requestFocus();
                }else if(et_mobile.getText().toString().trim().length() < 10){
                    et_mobile.setError("Please Enter volid Mobile Number");
                    et_mobile.requestFocus();
                }else {
                    if (isConnected()) {
                        Mobileno = et_mobile.getText().toString().trim();

                        if (et_user_id.getText().toString().trim().length() == 0) {

                            UserId=0;
                            ObjVariable="{" +
                                    "\"ClientId\":1," +
                                    "\"Number\":\""+Mobileno+"\"," +
                                    "\"LoginId\":"+UserId+"}";
                        }
                        else {
                            UserId = Integer.parseInt(et_user_id.getText().toString().trim());
                            ObjVariable="{" +
                                    "\"ClientId\":1," +
                                    "\"Number\":\""+Mobileno+"\"," +
                                    "\"LoginId\":"+UserId+"}";
                        }



                        Log.e("ForgotObject",ObjVariable);


                        new ForgotPasswordAPI().execute();


                    } else {
                        Toast.makeText(ForgotPassword.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

    }

    private class ForgotPasswordAPI extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
         }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", ForgotPasswardApp);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, ForgotPasswardApp);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("orderresponce", s);

                dismissProgress();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("re_response", ""+s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnMsg = json.getString("ReturnMsg");

                            JSONArray jArray = new JSONArray(ReturnMsg);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject json_data = jArray.getJSONObject(i);

                                String Status=json_data.getString("Status");
                                String msgs=json.getString("ReturnMsg");
                                if(Status.equals("1")){
                                    new AlertDialog.Builder(ForgotPassword.this)
                                            .setTitle("Account details will be sent over whatsapp")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                    onBackPressed();
                                                    finish();
                                                }
                                            })
                                            .show();

                                    Log.e("dineshhhh",Status);

                                }else {
                                    String msg=json.getString("ReturnMsg");
                                    new AlertDialog.Builder(ForgotPassword.this)
                                            .setTitle(msg)
                                            .setPositiveButton("Cancal", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                }
                                            })
                                            .show();
                                }

                            }







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("re_response", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }
}