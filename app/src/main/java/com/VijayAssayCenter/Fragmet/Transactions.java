package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.adapter.TranScetionAdapter;
import com.VijayAssayCenter.model.GetTransection;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public class Transactions extends Fragment {


    String regresponse = "";
    String LoginId = "";
    String ObjVariable = "";
    TextView tv_null;
    private ArrayList<GetTransection> bankDetailsList;
    RecyclerView rvBankDetails;


    public Transactions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        LoginId = Common.getPreferenceString(getActivity(), "LoginId", "");
        Log.e("LogTransction", LoginId);

        tv_null = view.findViewById(R.id.tv_null);

        if (LoginId.equals("")) {
            tv_null.setVisibility(View.VISIBLE);
            tv_null.setText("Please Sign In");
        } else {
            tv_null.setVisibility(View.GONE);

            rvBankDetails = (RecyclerView) view.findViewById(R.id.rvBankDetails);

            bankDetailsList = new ArrayList<>();
            RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
            rvBankDetails.setLayoutManager(homelayoutmanager);
            rvBankDetails.setItemAnimator(new DefaultItemAnimator());
            rvBankDetails.setAdapter(new TranScetionAdapter(getActivity(), bankDetailsList));

            ObjVariable = "{" +
                    "\"ClientId\":1," +
                    "\"LoginId\":\"" + LoginId + "\"}";

            new GetPaymentDetail().execute();
            Log.e("ObjVariable", ObjVariable);
        }


        return view;
    }


    private class GetPaymentDetail extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetPaymentDetail);
            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, Constants.GetPaymentDetail);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("GetTransecation", s);

                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        Log.e("GetTransecation", "" + s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnMsg = json.getString("ReturnMsg");
                            if (ReturnMsg.equals("[]")) {
                                tv_null.setVisibility(View.VISIBLE);
                                tv_null.setText("Transaction not available");


                            } else {
                                tv_null.setVisibility(View.GONE);
                                bankDetailsList.clear();
                                bankDetailsList.addAll((Collection<? extends GetTransection>) new Gson().fromJson(ReturnMsg, new TypeToken<ArrayList<GetTransection>>() {
                                }.getType()));
                                Objects.requireNonNull(rvBankDetails.getAdapter()).notifyDataSetChanged();


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

//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

}
