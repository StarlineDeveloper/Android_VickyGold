package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.Constants.GetcontactDetails;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.adapter.MendalDelevory;
import com.VijayAssayCenter.model.MentalAddressModel;
import com.VijayAssayCenter.utils.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class MetalDeliveryLocation extends BaseActivity {


    LinearLayout lay_back;
    MendalDelevory mendalDelevory;
    RecyclerView rv_menal_delevry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metal_delivery_location);


        GetAddressDetails();
        rv_menal_delevry = findViewById(R.id.rv_menal_delevry);
        mendalDelevory = new MendalDelevory(MetalDeliveryLocation.this);
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(MetalDeliveryLocation.this, 1);
        rv_menal_delevry.setLayoutManager(homelayoutmanager);
        rv_menal_delevry.setNestedScrollingEnabled(false);
        rv_menal_delevry.setItemAnimator(new DefaultItemAnimator());
        rv_menal_delevry.setAdapter(mendalDelevory);

        lay_back = (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private void GetAddressDetails() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String contactDetailsRes = "";
                SoapObject request = new SoapObject("http://tempuri.org/", GetcontactDetails);
                //request.addProperty("CleintId","4");
                try {
                    contactDetailsRes = loadService(request, GetcontactDetails);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return contactDetailsRes;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {

                    Log.e("bankDetailsRespones", "" + response);

                    dismissProgress();
                    mendalDelevory.addAll((List<MentalAddressModel>) new Gson().fromJson(response, new TypeToken<ArrayList<MentalAddressModel>>() {
                    }.getType()));


                } catch (Exception e) {
                    e.printStackTrace();
                    dismissProgress();

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
