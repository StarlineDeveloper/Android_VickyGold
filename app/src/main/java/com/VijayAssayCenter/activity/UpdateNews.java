package com.VijayAssayCenter.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.AppCompatTextView;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.adapter.UpdateAdapter;
import com.VijayAssayCenter.model.UpdateModel;
import com.VijayAssayCenter.utils.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.VijayAssayCenter.NetworkChangeReceiver.isOnline;
import static com.VijayAssayCenter.utils.Constants.GetNewsDateWise;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UpdateNews extends BaseActivity {

    LinearLayout lay_back;


    private RecyclerView rvUpdate;
    private AppCompatTextView tvNoDataFound;
    private List<UpdateModel> updateModelArrayList;

    private int mYear, mMonth, mDay;
    private String Y, M, D;
    AVLoadingIndicatorView avLoadingIndicatorView;

    TextView tv_search;



    TextView date1;
    TextView date2;

    String regresponse;


    String DateApi1="";
    String DateApi2="";

    String ObjVariable="";
    String BullionID="1";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);

        lay_back=findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rvUpdate = findViewById(R.id.rvUpdate);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);


        date1=findViewById(R.id.date1);
        date2=findViewById(R.id.date2);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

        date1.setText(df.format(c));
        date2.setText(df.format(c));

        tv_search=findViewById(R.id.tv_search);
        avLoadingIndicatorView = findViewById(R.id.avi);


        DateApi1=df.format(c);
        DateApi2=df.format(c);


        ObjVariable="{\"EndDate\":\""+DateApi2+"\",\"Client\":\""+BullionID+"\",\"StartDate\":\""+DateApi1+"\"}";
        tvNoDataFound.setVisibility(View.GONE);


        if (isOnline(getActivity())) {
            new RegisterApi().execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet Connection..", Toast.LENGTH_LONG).show();
        }


        date1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                D= String.valueOf(dayOfMonth);
                                M= String.valueOf(monthOfYear+1);
                                Y= String.valueOf(year);

                                if(M.length()==1){
                                    M="0"+M;
                                }else {

                                }

                                if(D.length()==1){
                                    D="0"+D;
                                }else {

                                }


                                date1.setText(D + "/" + M + "/" + Y);
                                DateApi1=D + "/" + M + "/" + Y;



                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                D= String.valueOf(dayOfMonth);
                                M= String.valueOf(monthOfYear+1);
                                Y= String.valueOf(year);

                                if(M.length()==1){
                                    M="0"+M;
                                }else {

                                }

                                if(D.length()==1){
                                    D="0"+D;
                                }else {

                                }


                                date2.setText(D + "/" + M + "/" + Y);
                                DateApi2=D + "/" + M + "/" + Y;

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });




        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjVariable="{\"EndDate\":\""+DateApi2+"\",\"Client\":\""+BullionID+"\",\"StartDate\":\""+DateApi1+"\"}";

                if (isOnline(getActivity())) {
                    new RegisterApi().execute();
                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection..", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class RegisterApi extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetNewsDateWise);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadService(request, GetNewsDateWise);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {

                avLoadingIndicatorView.setVisibility(View.GONE);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(s.equals("[]")){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvUpdate.setVisibility(View.GONE);
                                    tvNoDataFound.setVisibility(View.VISIBLE);


                                }
                            });
                        }
                        else {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvNoDataFound.setVisibility(View.GONE);
                                        rvUpdate.setVisibility(View.VISIBLE);


                                    }
                                });
                                if (updateModelArrayList == null)
                                    updateModelArrayList = new ArrayList<>();


                                updateModelArrayList = new ArrayList<>();
                                UpdateAdapter updateAdapter = new UpdateAdapter(updateModelArrayList, UpdateNews.this);
                                rvUpdate.setLayoutManager(new LinearLayoutManager(UpdateNews.this));
                                rvUpdate.setItemAnimator(new DefaultItemAnimator());
                                rvUpdate.setAdapter(updateAdapter);

                                Log.e("updateresponce", s);

                                updateModelArrayList.clear();
                                updateModelArrayList.addAll((Collection<? extends UpdateModel>)
                                        new Gson().fromJson(s, new TypeToken<List<UpdateModel>>() {
                                        }.getType()));

                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
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