package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;
import static com.VijayAssayCenter.utils.Constants.GetCloseOrderCoinDetails;
import static com.VijayAssayCenter.utils.Constants.GetCoinsOpenOrderDetails;
import static com.VijayAssayCenter.utils.Constants.GetDeleteOrderCoinDetails;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.ApplicationController;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.model.GetCoinKey;
import com.VijayAssayCenter.model.GetMainRate;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.transports.WebSocket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

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
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CoinOrdes extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    AVLoadingIndicatorView avLoadingIndicatorView;

    String regresponse="";

    private int mYear, mMonth, mDay;
    private String Y, M, D;

    String Firmname = "vickygold";
    String Fromdate = "";
    String Todate = "";
    int loginid=0;
    String ObjVariable="";

    TextView tv_balance;
    TextView tv_free_margin;
    TextView use_margin;
    TextView tv_pl;

    TextView date1;
    TextView date2;

    LinearLayout lay_vis;
    LinearLayout lay_not;

    private Socket mSocket10008;
    private static final String URL22 = "https://vickygold.co.in:10001";



    TextView tv_search;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            //do something  //Load or Refresh Data

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    new GetOpenOrderDetails().execute();
                    new GetDeleteOrder().execute();
                    new GetCloseOrder().execute();


                }
            }, 100);


        }
    }
    public CoinOrdes() {
        // Required empty public constructor
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coin_ordes, container, false);

        date1=view.findViewById(R.id.date1);
        date2=view.findViewById(R.id.date2);


        if(Common.getPreferenceString(getActivity(),"LoginId","").equals("")){

        }else {
            loginid= Integer.parseInt(Common.getPreferenceString(getActivity(),"LoginId",""));

        }


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

        Todate=df.format(c);
        Fromdate=df.format(c);

        date1.setText(df.format(c));
        date2.setText(df.format(c));

        tv_search=view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjVariable="{" +
                        "ClientID:1," +
                        "\"Firmname\":\""+Firmname+"\"," +
                        "\"Fromdate\":\""+Fromdate+"\"," +
                        "\"Todate\":\""+Todate+"\"," +
                        "loginid:"+loginid+"}";

                Log.e("djjdjjdjdd",ObjVariable);


                new GetOpenOrderDetails().execute();
                new GetDeleteOrder().execute();
               new GetCloseOrder().execute();
            }
        });

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
                                Todate=D + "/" + M + "/" + Y;



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
                                Fromdate=D + "/" + M + "/" + Y;

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        avLoadingIndicatorView = view.findViewById(R.id.avi);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        tv_balance=view.findViewById(R.id.tv_balance);
        tv_free_margin=view.findViewById(R.id.tv_free_margin);
        use_margin=view.findViewById(R.id.use_margin);
        // viewPager.setOffscreenPageLimit(5);

        avLoadingIndicatorView = view.findViewById(R.id.avi);


        lay_vis=view.findViewById(R.id.lay_vis);
        lay_not=view.findViewById(R.id.lay_not);

        final String UserName=Common.getPreferenceString(getActivity(),"UserName","");
        Log.e("UserName",UserName);
        if(UserName.equals("")){
            lay_vis.setVisibility(View.GONE);
            lay_not.setVisibility(View.VISIBLE);
        }else {

            lay_vis.setVisibility(View.VISIBLE);
            lay_not.setVisibility(View.GONE);

            ObjVariable="{" +
                    "ClientID:1," +
                    "\"Firmname\":\""+Firmname+"\"," +
                    "\"Fromdate\":\""+Fromdate+"\"," +
                    "\"Todate\":\""+Todate+"\"," +
                    "loginid:"+loginid+"}";
            Log.e("ObjVariable",ObjVariable);
            new GetOpenOrderDetails().execute();
            new GetDeleteOrder().execute();
            new GetCloseOrder().execute();
        }

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OpenOrderCoin(), "Open Order");
        adapter.addFragment(new CloseCoinOrder(), "Close Trade");
        adapter.addFragment(new DeleteCoinoder(), "Deleted Trade");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabOne.setText("Open Order");
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabFour = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabFour.setText("Close Trade");
        tabLayout.getTabAt(1).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabFive.setText("Deleted Trade");
        tabLayout.getTabAt(2).setCustomView(tabFive);
    }


    private class GetOpenOrderDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // avLoadingIndicatorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetCoinsOpenOrderDetails);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetCoinsOpenOrderDetails);
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

                avLoadingIndicatorView.setVisibility(View.GONE);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("dineshhhhhcoin", ""+s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);




                            Constants.OpenOrderCoin = json.getString("OpenOrder");




                            tabLayout.setupWithViewPager(viewPager);
                            setupViewPager(viewPager);
                            setupTabIcons();
                            viewPager.setCurrentItem(Constants.Pgaeno);

                            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                public void onPageScrollStateChanged(int arg0) {
                                }

                                public void onPageScrolled(int arg0, float arg1, int arg2) {
                                }

                                public void onPageSelected(int currentPage) {
                                    //currentPage is the position that is currently displayed.

                                    Constants.Pgaeno=currentPage;
                                    Log.e("jkjdjjhf",""+currentPage);
                                }

                            });






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



    private class GetDeleteOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("DeleteOrder", "Call");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ObjVariable="{" +
                    "ClientID:1," +
                    "\"Firmname\":\""+Firmname+"\"," +
                    "\"Fromdate\":\""+Fromdate+"\"," +
                    "\"Todate\":\""+Todate+"\"," +
                    "loginid:"+loginid+"}";
            SoapObject request = new SoapObject("http://tempuri.org/", GetDeleteOrderCoinDetails);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetDeleteOrderCoinDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("DeleteOrder", s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);
                            Constants.DeleteOrderCoin = json.getString("DeleteOrder");
                            Log.e("DeleteOrder", Constants.DeleteOrderCoin);

                            tabLayout.setupWithViewPager(viewPager);
                            setupViewPager(viewPager);
                            setupTabIcons();
                            viewPager.setCurrentItem(Constants.Pgaeno);

                            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                public void onPageScrollStateChanged(int arg0) {
                                }

                                public void onPageScrolled(int arg0, float arg1, int arg2) {
                                }

                                public void onPageSelected(int currentPage) {
                                    //currentPage is the position that is currently displayed.

                                    Constants.Pgaeno=currentPage;
                                    Log.e("jkjdjjhf",""+currentPage);
                                }

                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("DeleteOrder", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private class GetCloseOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("GetCloseOrderDetails", "Call");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetCloseOrderCoinDetails);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetCloseOrderCoinDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("GetCloseOrderCoinails", s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);
                            Constants.ClosedOrderCoin = json.getString("ClosedOrder");

                            Log.e("GetCloseOrderDetails", Constants.ClosedOrderCoin);

                            tabLayout.setupWithViewPager(viewPager);
                            setupViewPager(viewPager);
                            setupTabIcons();
                            viewPager.setCurrentItem(Constants.Pgaeno);

                            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                public void onPageScrollStateChanged(int arg0) {
                                }

                                public void onPageScrolled(int arg0, float arg1, int arg2) {
                                }

                                public void onPageSelected(int currentPage) {
                                    //currentPage is the position that is currently displayed.

                                    Constants.Pgaeno=currentPage;
                                    Log.e("jkjdjjhf",""+currentPage);
                                }

                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("GetCloseOrderDetails", e.getMessage());
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

    @Override
    public void onResume() {
       GetInfoClient();
//        ApplicationController.getInstance().trackScreenView("Coin Trades Screen_Android");

        super.onResume();
    }

    public void GetInfoClient(){
        try {
            Log.e("Starline3555","Runnnnnn");
            try {
                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                try {
                    mySSLContext.init(null, trustAllCerts, null);
                    IO.setDefaultSSLContext(mySSLContext);


                    IO.Options opts = new IO.Options();

                    opts.reconnection = true;
                    opts.reconnectionDelay = 1000;
                    opts.timeout = 50000;

                    opts.transports = new String[] { WebSocket.NAME };
                    mSocket10008 = IO.socket(Constants.SocketUrl, opts);

                    if(mSocket10008.connected()){
                        Log.e("ordersoekt","Conncet");

                        LiveRateSoket();

                    }else {
                    }

                    mSocket10008.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {

                            Log.e("ordersoekt","Conncet");
                            LiveRateSoket();




                        }
                    }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Starline2",arg0[0].toString());

                        }
                    }).on("something_changed", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Starline3",obj.toString());

                        }
                    });

                    mSocket10008.connect();


                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                Log.e("Starline3555",e.toString());

                e.printStackTrace();
            }

        } catch (URISyntaxException e) {
            Log.e("Starline355",e.toString());

            throw new RuntimeException(e);
        }




    }

    private final TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    } };


    public void LiveRateSoket() {

        Log.e("ordddeeeee","on");
        mSocket10008.emit("Orders", Constants.prjName).on("Orders", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.e("ordddeeeeecoinorde",args[0].toString());

                new GetOpenOrderDetails().execute();
                new GetDeleteOrder().execute();
                new GetCloseOrder().execute();
            }
        });




    }


}