package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.Constants.BankDetail;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.adapter.BankDetailsAdapterBullion;
import com.VijayAssayCenter.model.BankDetailsModel;
import com.VijayAssayCenter.utils.BaseActivity;
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


public class PaymentBankDetails extends BaseActivity {

    LinearLayout lay_back;

    RecyclerView rvBankDetails;
    TextView tvBankDetailsNotAvailableMessage;


    private ArrayList<BankDetailsModel> bankDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_bank_details);

        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rvBankDetails= (RecyclerView) findViewById(R.id.rvBankDetails);
        tvBankDetailsNotAvailableMessage=findViewById(R.id.tvBankDetailsNotAvailableMessage);

        bankDetailsList = new ArrayList<>();
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(PaymentBankDetails.this, 1);
        rvBankDetails.setLayoutManager(homelayoutmanager);
        rvBankDetails.setItemAnimator(new DefaultItemAnimator());
        rvBankDetails.setAdapter(new BankDetailsAdapterBullion(PaymentBankDetails.this, bankDetailsList));

        getBankDetails();
    }

    @SuppressLint("StaticFieldLeak")
    private void getBankDetails() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", BankDetail);
                request.addProperty("ClientId","1");
                try {
                    bankDetailsResponse = loadService(request, BankDetail);
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

                    Log.e("bankDetailsRespones",""+response);

                    dismissProgress();

                    if(response.equals("[]")){
                        tvBankDetailsNotAvailableMessage.setVisibility(View.VISIBLE);
                        rvBankDetails.setVisibility(View.GONE);

                    }else {
                        tvBankDetailsNotAvailableMessage.setVisibility(View.GONE);
                        rvBankDetails.setVisibility(View.VISIBLE);

                        bankDetailsList.clear();
                        bankDetailsList.addAll((Collection<? extends BankDetailsModel>) new Gson().fromJson(response, new TypeToken<ArrayList<BankDetailsModel>>() {
                        }.getType()));
                        Objects.requireNonNull(rvBankDetails.getAdapter()).notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    dismissProgress();

                }
            }
        }.execute();
    }



}
