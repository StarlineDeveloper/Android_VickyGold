package com.VijayAssayCenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
//import com.github.irvingryan.VerifyCodeView;

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

public class EnterOTP extends BaseActivity {

    TextView tv_condition;
    LinearLayout tv_submit;
//    VerifyCodeView pinview;
    String OTP="";
    String ObjVariable="";
    String regresponse="";

    String ObjVariableOtpResend="";


    TextView tv_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        tv_condition= (TextView) findViewById(R.id.tv_condition);
//        pinview = findViewById(R.id.pinview);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String red = "By proceeding, you agree to our";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
        builder.append(redSpannable);

        String white = "Terms & Conditions & Privacy Policy. ";
        SpannableString whiteSpannable= new SpannableString(white);
        whiteSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#009BE0")), 0, white.length(), 0);
        builder.append(whiteSpannable);

        String blue = "Standard operator charges may apply for SMS";
        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, blue.length(), 0);
        builder.append(blueSpannable);

        tv_condition.setText(builder, TextView.BufferType.SPANNABLE);

        tv_otp=findViewById(R.id.tv_otp);
        tv_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ObjVariableOtpResend="{\"Number\":\""+Constants.Mobileno+"\"," +
                        "\"GST\":\""+""+"\"," +
                        "\"Flag\":\"new\"," +
                        "\"Name\":\""+""+"\"," +
                        "\"Firmname\":\""+""+"\"," +
                        "\"Email\":\""+""+"\"," +
                        "\"RegisterDoc\":\""+""+"\"," +
                        "\"DocName\":\""+""+"\"," +
                        "\"RegisterDoc1\":\""+""+"\"," +
                        "\"DocName1\":\""+""+"\"," +
                        "\"RegisterDoc2\":\""+""+"\"," +
                        "\"DocName2\":\""+""+"\"," +
                        "\"ClientId\":\"2\"," +
                        "\"AccountId\":\"0\"," +
                        "\"City\":\""+""+"\"}";


                new OtpResend().execute();

            }
        });


        tv_submit=findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                OTP = pinview.getText();
//                if (OTP.isEmpty()) {
//                    Toast.makeText(EnterOTP.this,"Enter OTP",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    ObjVariable="{\"Number\":\""+Constants.Mobileno+"\"," +
//                            "\"ClientId\":\"2\"," +
//                            "\"Otp\":\""+OTP+"\"}";
//
//                    Log.e("otpii",ObjVariable);
//
//                    new Register().execute();
//
//                }


//                Intent MainActivity=new Intent(EnterOTP.this, MainActivity.class);
//                startActivity(MainActivity);
//                finish();
            }
        });
    }

    private class Register extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", "VerifyOtpApp");
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadService5411(request, "VerifyOtpApp");
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("RegisterRespoce", s);

                dismissProgress();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("RegisterRespoce", ""+s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnMsg = json.getString("ReturnMsg");
                            JSONArray jArray = new JSONArray(ReturnMsg);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject json_data = jArray.getJSONObject(i);
                                String Status=json_data.getString("Status");
                                if(Status.equals("true")){
                                    Constants.page=0;
                                    Common.setPreferenceString(EnterOTP.this,"FristTime","Yes");
                                    Common.setPreferenceString(EnterOTP.this,"FristTimeMobile",Constants.Mobileno);
                                    Intent MainActivity=new Intent(getActivity(), MainActivity.class);
                                    startActivity(MainActivity);
                                    finish();

                                }else {

                                    String Err_Msg=json_data.getString("Err_Msg");
                                    new AlertDialog.Builder(EnterOTP.this)
                                            .setTitle("Vicky Jewellery")
                                            .setMessage(Err_Msg)
                                            .setNegativeButton("Cancel", null)
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


    public static String loadService5411(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }

    private class OtpResend extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", "insertRegisterApp");
            request.addProperty("Obj",ObjVariableOtpResend);
            Log.e("request", request.toString());
            try {
                regresponse = loadService5411(request, "insertRegisterApp");
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("RegisterRespoce", s);

                dismissProgress();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("RegisterRespoce", ""+s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnMsg = json.getString("ReturnMsg");


                            JSONArray jArray = new JSONArray(ReturnMsg);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject json_data = jArray.getJSONObject(i);
                                String Status=json_data.getString("Status");
                                if(Status.equals("1")){

                                    new AlertDialog.Builder(EnterOTP.this)
                                            .setTitle("Vicky jewellery works")
                                            .setMessage("Otp resend successfully registered mobile number "+Constants.Mobileno)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }

                                            })
                                            //.setNegativeButton("No", null)
                                            .show();
                                }else {

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

}
