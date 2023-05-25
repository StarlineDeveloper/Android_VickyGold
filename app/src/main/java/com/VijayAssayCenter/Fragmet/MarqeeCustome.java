package com.VijayAssayCenter.Fragmet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MarqeeCustome extends Fragment {


    TextView btn_submit;

    String MarweeTest="";

    EditText et_marqee;
    AsyncProgressDialog ad;

    AlertDialog alertDialogcomman;


    public MarqeeCustome() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_marqee_custome, container, false);

        btn_submit= (TextView) view.findViewById(R.id.btn_submit);
        et_marqee= (EditText) view.findViewById(R.id.et_marqee);

        GetMarqee();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarweeTest=et_marqee.getText().toString().trim();
                AddMarqee();
            }
        });

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void AddMarqee() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.AddMarqueeMobile);
                request.addProperty("Marquee", MarweeTest);


                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.AddMarqueeMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("Marqeeresponce",response);
                    dismissProgress();
                    JSONObject MarqueeJson = new JSONObject(response);
                    if(MarqueeJson.getString("Status").equals("true")){
                        DialogSuccessComman();
                        GetMarqee();

                    }
                    else {
                        Toast.makeText(getActivity(),"failed...!", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private void GetMarqee() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetMarqueeMobile);
                //request.addProperty("Marquee", MarweeTest);


                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.GetMarqueeMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("Marqeeresponce",response);

                    JSONObject MarqueeJson = new JSONObject(response);
                    if(MarqueeJson.getString("Status").equals("true")){
                        MarweeTest=MarqueeJson.getString("Marquee");
                        et_marqee.setText(MarweeTest);




                    }
                    else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();
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


    public void showProgress(String msg) {

        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(getActivity());
            ad.show(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dismissProgress() {
        try {
            if (ad != null) {
                ad.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DialogSuccessComman() {

        LayoutInflater li = LayoutInflater.from(getActivity());

        final View confirmDialog = li.inflate(R.layout.dailog_success, null);
        TextView tv_msg= (TextView) confirmDialog.findViewById(R.id.tv_msg);
        //tv_msg.setText(msg);

        final TextView tv_ok= (TextView) confirmDialog.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(300);
                tv_ok.setAlpha(1f);
                tv_ok.startAnimation(animation1);
                alertDialogcomman.dismiss();



            }
        });

       AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(confirmDialog);

        alertDialogcomman = alert.create();

        alertDialogcomman.show();

        alertDialogcomman.setCanceledOnTouchOutside(true);




    }


}
