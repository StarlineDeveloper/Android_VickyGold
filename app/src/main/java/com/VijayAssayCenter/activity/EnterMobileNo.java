package com.VijayAssayCenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Constants;

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

public class EnterMobileNo extends BaseActivity {

    TextView tv_condition;

    EditText et_mobile_no;
    LinearLayout lay_get_opt;

    String MobileNo="";
    LinearLayout lay_skip_login;
    LinearLayout tv_login;

    String ObjVariable="";
    String regresponse="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_no);



        tv_condition= (TextView) findViewById(R.id.tv_condition);

        tv_login=findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TreadeInsert=new Intent(EnterMobileNo.this, Login.class);
                TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(TreadeInsert);
            }
        });

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


        et_mobile_no=findViewById(R.id.et_mobile_no);
        lay_get_opt=findViewById(R.id.lay_get_opt);
        lay_skip_login=findViewById(R.id.lay_skip_login);

        lay_skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainActivity=new Intent(getActivity(), MainActivity.class);
                MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MainActivity);
            }
        });

        lay_get_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_mobile_no.getText().toString().trim().length() == 0) {
                    et_mobile_no.setError("Enter Mobile Number");
                    et_mobile_no.requestFocus();
                } else if (et_mobile_no.getText().toString().trim().length() < 10) {
                    et_mobile_no.setError("Invalid Mobile Number");
                    et_mobile_no.requestFocus();
                } else {
                    if (isConnected()) {
                        MobileNo=et_mobile_no.getText().toString().trim();

                        ObjVariable="{\"Number\":\""+MobileNo+"\"," +
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

                        Log.e("tikk",ObjVariable);

                        new Register().execute();

                    } else {
                        Toast.makeText(EnterMobileNo.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();

                    }
                }

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
            SoapObject request = new SoapObject("http://tempuri.org/", "insertRegisterApp");
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadService22(request, "insertRegisterApp");
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


                                   Constants.Mobileno=MobileNo;
                                   Intent MainActivity=new Intent(getActivity(), EnterOTP.class);
                                   MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(MainActivity);
                                   finish();
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

    public static String loadService22(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }
}
