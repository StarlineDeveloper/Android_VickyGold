package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadService;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.PaymentBankDetails;
import com.VijayAssayCenter.adapter.BankDetailsAdapterBullion;
import com.VijayAssayCenter.model.BankDetailsModel;
import com.VijayAssayCenter.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public class BankDetails extends Fragment {


    RecyclerView rvBankDetails;
    TextView tvBankDetailsNotAvailableMessage;


    private ArrayList<BankDetailsModel> bankDetailsList;

    public BankDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_details, container, false);


        rvBankDetails = (RecyclerView) view.findViewById(R.id.rvBankDetails);
        tvBankDetailsNotAvailableMessage = view.findViewById(R.id.tvBankDetailsNotAvailableMessage);

        bankDetailsList = new ArrayList<>();
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
        rvBankDetails.setLayoutManager(homelayoutmanager);
        rvBankDetails.setItemAnimator(new DefaultItemAnimator());
        rvBankDetails.setAdapter(new BankDetailsAdapterBullion(getActivity(), bankDetailsList));

        getBankDetails();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void getBankDetails() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.BankDetail);
                request.addProperty("ClientId", "1");
                try {
                    bankDetailsResponse = loadService(request, Constants.BankDetail);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return bankDetailsResponse;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {

                    Log.e("bankDetailsRespones", "" + response);


                    if (response.equals("[]")) {
                        tvBankDetailsNotAvailableMessage.setVisibility(View.VISIBLE);
                        rvBankDetails.setVisibility(View.GONE);

                    } else {
                        tvBankDetailsNotAvailableMessage.setVisibility(View.GONE);
                        rvBankDetails.setVisibility(View.VISIBLE);

                        bankDetailsList.clear();
                        bankDetailsList.addAll((Collection<? extends BankDetailsModel>) new Gson().fromJson(response, new TypeToken<ArrayList<BankDetailsModel>>() {
                        }.getType()));
                        Objects.requireNonNull(rvBankDetails.getAdapter()).notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }.execute();
    }

//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/WebService.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }
}