package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.ApplicationController;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.adapter.LiveRateMainAdapter;
import com.VijayAssayCenter.adapter.SliderAdapter;
import com.VijayAssayCenter.adapter.SpotRateAdapter;
import com.VijayAssayCenter.model.GetMainRate;
import com.VijayAssayCenter.model.LiverateSymbolModel;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.VijayAssayCenter.utils.SliderData;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.SliderView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class BullionRate extends Fragment {

    RecyclerView rv_main_product;
    RecyclerView rv_future_product;
    LiveRateMainAdapter liveRateMainAdapter;
    //  FutureAdapter futureAdapter;
    String GetLiverate = "";
    io.socket.client.Socket NewSocket;
    private ArrayList<LiverateSymbolModel.MCXBean> list_spotSymbol;
    private io.socket.client.Socket mSocket10008;
    private static final String URL22 = "https://vickygold.co.in:10008";
    private static final String URLNew = "https://vickygold.co.in:10001";
    String ReferranceProductName = "";
    String RefranceRate = "";
    String Hersrdata = "";
    TextView tvLiveRateNotAvailable;
    private SpotRateAdapter spotRateAdapter;
    AsyncProgressDialog ad;
    String LoginId = "";
    AVLoadingIndicatorView avLoadingIndicatorView;
    String Gruopin = "";
    SliderView sliderView;
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

    public BullionRate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv_main_product = (RecyclerView) view.findViewById(R.id.rv_main_product);


        getSilder();


        sliderView = view.findViewById(R.id.slider);


        LoginId = Common.getPreferenceString(MainActivity.activity, "LoginId", "");


        avLoadingIndicatorView = view.findViewById(R.id.avi);

        tvLiveRateNotAvailable = view.findViewById(R.id.tvLiveRateNotAvailable);

        rv_future_product = (RecyclerView) view.findViewById(R.id.rv_future_product);
//        futureAdapter = new FutureAdapter(getActivity());
//        RecyclerView.LayoutManager homelayoutmanager1 = new GridLayoutManager(getActivity(), 2);
//        rv_future_product.setLayoutManager(homelayoutmanager1);
//        rv_future_product.setNestedScrollingEnabled(false);
//        rv_future_product.setItemAnimator(new DefaultItemAnimator());
//        rv_future_product.setAdapter(futureAdapter);


        setAdapter();

        avLoadingIndicatorView.setVisibility(View.VISIBLE);


        return view;
    }


    @Override
    public void onResume() {
        GetInfoClient();
        ApplicationController.getInstance().trackScreenView("Bullion Rate Screen_Android");


        super.onResume();
    }


    public void LiveRateSoket() {

        mSocket10008.emit("room", Constants.prjName).on("message", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(Object... args) {
                GetLiverate = "" + args[0];

                Common.setPreferenceString(MainActivity.activity, "GetLiverate", GetLiverate);

                Log.e("GetLiverateLogin", GetLiverate);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject result = null;

                            result = new JSONObject(GetLiverate);
                            final String Rate = result.getString("Rate");

//                            JSONArray array = null;
//                            try {
//                                array = new JSONArray(Rate);
//                            } catch (Exception e) {
//
//                            }

                            liveRateMainAdapter.addAll((List<GetMainRate>) new Gson().fromJson(Rate, new TypeToken<ArrayList<GetMainRate>>() {
                            }.getType()));
                            avLoadingIndicatorView.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            Log.d("errrrrrrorrrlog", "run: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });


            }
        });


        mSocket10008.emit("Liverate", "vickygold").on("Liverate", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.e("Dijjjjj==", "" + args[0]);
                RefranceRate = "" + args[0];
                Common.setPreferenceString(MainActivity.activity, "RefranceRate", RefranceRate);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        list_spotSymbol = new ArrayList<>();
                        list_spotSymbol.clear();
                        list_spotSymbol.removeAll(list_spotSymbol);
                        Log.e("ReferranceProductName", ReferranceProductName);

                        JSONArray resultArray = null;
                        try {
                            resultArray = new JSONArray(ReferranceProductName);
                            for (int i = 0; i < resultArray.length(); i++) {

                                LiverateSymbolModel.MCXBean fm = new LiverateSymbolModel.MCXBean();
                                JSONObject p = (JSONObject) resultArray.get(i);
                                String Source = p.getString("Source");


                                String Referance_Id = p.getString("Referance_Id");
                                String Bullion_Id = p.getString("Bullion_Id");
                                String Symbol_Name = p.getString("Symbol_Name").toUpperCase();
                                String IsDisplay = p.getString("IsDisplay");

                                //    Log.e("IsDisplaySpot",""+IsDisplay);

                                if (IsDisplay.equals("true")) {
                                    JSONArray resultArrayRefrance = null;
                                    try {

                                        ///
                                        resultArrayRefrance = new JSONArray(RefranceRate);
                                        for (int j = 0; j < resultArrayRefrance.length(); j++) {

                                            JSONObject pp = (JSONObject) resultArrayRefrance.get(i);

                                            String symbol = pp.getString("symbol");
                                            //Log.e("symbolnn",symbol);

                                            if (symbol.equals(Source)) {

                                                String Bid = pp.getString("Bid");
                                                String Ask = pp.getString("Ask");
                                                String High = pp.getString("High");
                                                String Low = pp.getString("Low");

                                                fm.setBid(Bid);
                                                fm.setAsk(Ask);
                                                fm.setHigh(Low);
                                                fm.setLow(High);
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    fm.setReferance_Id(Referance_Id);
                                    fm.setBullion_Id(Bullion_Id);
                                    fm.setSource(Source);
                                    fm.setSymbol_Name(Symbol_Name);
                                    fm.setIsDisplay(IsDisplay);
                                    list_spotSymbol.add(fm);
                                    spotRateAdapter.refillData(list_spotSymbol);

                                    avLoadingIndicatorView.setVisibility(View.GONE);
                                    //Log.e("Starline1","Gone");


                                } else {

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

    }

    public void FluteerDate() {
        Log.e("jkjfdjfkdlfkjdfk", "Sucesss");
        NewSocket.emit("ClientData", Constants.prjName).on("ClientData", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ReferranceProductName = "" + args[0];
                Log.e("ClientData", "" + args[0]);

            }
        });


        NewSocket.emit("Client", Constants.prjName).on("ClientHeaderDetails", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Hersrdata = "" + args[0];
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject result = null;
                        try {
                            result = new JSONObject(Hersrdata);
                            final String Data = result.getString("Data");

                            JSONArray resultArray = null;
                            try {
                                resultArray = new JSONArray(Data);
                                for (int i = 0; i < resultArray.length(); i++) {


                                    JSONObject p = (JSONObject) resultArray.get(i);
                                    String LiveratenotAvlable = p.getString("RateDisplay");

                                    Log.e("ClientHeaderDetails", "" + LiveratenotAvlable);

                                    if (LiveratenotAvlable.equals("true")) {
                                        rv_main_product.setVisibility(View.VISIBLE);
                                        tvLiveRateNotAvailable.setVisibility(View.GONE);


                                    } else {
                                        rv_main_product.setVisibility(View.GONE);
                                        tvLiveRateNotAvailable.setVisibility(View.VISIBLE);

                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


                Log.e("HeaderDetailsConTactUs=", "" + args[0]);
                JSONArray resultArray = null;

                try {
                    JSONObject result = null;

                    result = new JSONObject("" + args[0]);
                    final String Data = result.getString("Data");


                    try {
                        resultArray = new JSONArray(Data);
                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject p = (JSONObject) resultArray.get(i);


                            Log.e("BullionID====", "" + p.getString("BullionID"));
                            Log.e("BookingNo1", "" + p.getString("BookingNo1"));


                            String BookingNo1 = p.getString("BookingNo1");
                            String BookingNo2 = p.getString("BookingNo2");
                            String BookingNo3 = p.getString("BookingNo3");
                            String BookingNo4 = p.getString("BookingNo4");
                            String BookingNo5 = p.getString("BookingNo5");
                            String BookingNo6 = p.getString("BookingNo6");
                            String BookingNo7 = p.getString("BookingNo7");


                            Common.setPreferenceString(MainActivity.activity, "BookingNo1", BookingNo1);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo2", BookingNo2);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo3", BookingNo3);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo4", BookingNo4);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo5", BookingNo5);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo6", BookingNo6);
                            Common.setPreferenceString(MainActivity.activity, "BookingNo7", BookingNo7);

                            Common.setPreferenceString(MainActivity.activity, "Email1", p.getString("Email1"));
                            Common.setPreferenceString(MainActivity.activity, "Email2", p.getString("Email2"));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        if (LoginId.equals("")) {
            Log.e("Diiididiididid", "Null");

        } else {
            Log.e("Diiididiididid", "Null Not" + LoginId);

            NewSocket.emit("End_Client", Constants.prjName + "_" + LoginId).on("Accountdetails", new io.socket.emitter.Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String Accountdetails = "" + args[0];
                    Log.e("Accountdetails", Accountdetails);

                    JSONObject json = null;
                    try {
                        json = new JSONObject("" + args[0]);

                        String Status = json.getString("Status");
                        Log.e("Status", Status);

                        if (Status.equals("true")) {
                            Constants.totalBalance = json.getDouble("Balance");
                            Gruopin = json.getString("GroupName");

                            if (getActivity() == null) {

                            } else {
                                Common.setPreferenceString(MainActivity.activity, "Balance", "" + Constants.totalBalance);
                                Common.setPreferenceString(MainActivity.activity, "GroupName", Gruopin);
                                Log.e("Gruopin", Gruopin);
                            }

                            NewSocket.emit("GroupDetails", Constants.prjName + "_" + Gruopin).on("GroupDetails", new io.socket.emitter.Emitter.Listener() {
                                @Override
                                public void call(final Object... args) {
                                    //    Log.e("Dinesh1",""+args[0]);
                                    //  Log.e("Dinesh2",""+args[1]);

                                    String GroupDetails = "" + args[0];
                                    if (GroupDetails.equals("[]")) {
                                        Constants.GoldBuyPremium = 0;
                                        Constants.GoldSellPremium = 0;
                                        Constants.GrupStaus = "";

                                        Constants.SilverBuyPremium = 0;
                                        Constants.SilverSellPremium = 0;
                                    } else {
                                        JSONArray jArray = null;
                                        try {
                                            jArray = new JSONArray(GroupDetails);
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json_data = jArray.getJSONObject(i);

                                                Log.e("tttttttttt", "" + json_data);


                                                Constants.GoldBuyPremium = json_data.getInt("GoldBuyPremium");
                                                Constants.GoldSellPremium = json_data.getInt("GoldSellPremium");
                                                Constants.GrupStaus = json_data.getString("Status");
                                                Log.e("GrupStaus", Constants.GrupStaus);

                                                Constants.SilverBuyPremium = json_data.getInt("SilverBuyPremium");
                                                Constants.SilverSellPremium = json_data.getInt("SilverSellPremium");
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    //grop


                                    if (args[1] == null) {

                                    } else {
                                        String ClientData = "" + args[1];
                                        Constants.BuyPremium.removeAll(Constants.BuyPremium);
                                        Constants.SellPremium.removeAll(Constants.SellPremium);
                                        Constants.SellPremiumIDDDD.removeAll(Constants.SellPremiumIDDDD);
                                        Constants.OneClick.removeAll(Constants.OneClick);
                                        Constants.InTotal.removeAll(Constants.InTotal);
                                        Constants.Step.removeAll(Constants.Step);

                                        Constants.BuyPremium.clear();
                                        Constants.SellPremium.clear();
                                        Constants.SellPremiumIDDDD.clear();
                                        Constants.OneClick.clear();
                                        Constants.InTotal.clear();
                                        Constants.Step.clear();


                                        JSONArray jArray = null;
                                        try {
                                            jArray = new JSONArray(ClientData);
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json_data = jArray.getJSONObject(i);
                                                Log.e("Diehhhhhshss", "" + json_data);


                                                int BuyPremium = json_data.getInt("BuyPremium");
                                                int SellPremium = json_data.getInt("SellPremium");
                                                int SymbolID = json_data.getInt("SymbolID");
                                                int OneClick = json_data.getInt("OneClick");
                                                int InTotal = json_data.getInt("InTotal");
//                                                float Step = json_data.getDouble("Step");
                                                float Step = Float.valueOf(json_data.getString("Step"));
//                                                double step=Double.parseDouble(json_data.getString("Step"));
                                                Log.e("dieueuuueue", "" + InTotal);


                                                Constants.BuyPremium.add(BuyPremium);
                                                Constants.SellPremium.add(SellPremium);
                                                Constants.SellPremiumIDDDD.add(SymbolID);
                                                Constants.OneClick.add(OneClick);
                                                Constants.InTotal.add(InTotal);
                                                Constants.Step.add(Step);


                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            });

                        } else {
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Common.setPreferenceString(MainActivity.activity, "Access", "");
                                        Common.setPreferenceString(MainActivity.activity, "AccountID", "");
                                        Common.setPreferenceString(MainActivity.activity, "Balance", "");
                                        Common.setPreferenceString(MainActivity.activity, "City", "");
                                        Common.setPreferenceString(MainActivity.activity, "Email", "");
                                        Common.setPreferenceString(MainActivity.activity, "GroupID", "");
                                        Common.setPreferenceString(MainActivity.activity, "GroupName", "");
                                        Common.setPreferenceString(MainActivity.activity, "GST", "");
                                        Common.setPreferenceString(MainActivity.activity, "LoginId", "");
                                        Common.setPreferenceString(MainActivity.activity, "LoginId", "");
                                        Common.setPreferenceString(MainActivity.activity, "Name", "");
                                        Common.setPreferenceString(MainActivity.activity, "Number", "");
                                        Common.setPreferenceString(MainActivity.activity, "password", "");
                                        Common.setPreferenceString(MainActivity.activity, "Status", "");
                                        Common.setPreferenceString(MainActivity.activity, "status_code", "");
                                        Common.setPreferenceString(MainActivity.activity, "UserName", "");
                                        Common.setPreferenceString(MainActivity.activity, "TokenLogin", "");
                                        Common.setPreferenceString(MainActivity.activity, "Firmname", "");
                                        Common.setPreferenceString(MainActivity.activity, "DisplayName", "");

                                        Intent TreadeInsert = new Intent(MainActivity.activity, MainActivity.class);
                                        TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(TreadeInsert);
                                        getActivity().finish();

                                    }
                                });
                            } catch (Exception e) {

                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            NewSocket.emit("ClientData", Constants.prjName).on("checklogin", new io.socket.emitter.Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    Log.e("checklogin", "" + args[0]);
                    if (args[0].toString().equals("true")) {
                        //Log.e("checklogin","UserNameNull-ture");

                    } else {
                        Common.setPreferenceString(MainActivity.activity, "Access", "");
                        Common.setPreferenceString(MainActivity.activity, "AccountID", "");
                        Common.setPreferenceString(MainActivity.activity, "Balance", "");
                        Common.setPreferenceString(MainActivity.activity, "City", "");
                        Common.setPreferenceString(MainActivity.activity, "Email", "");
                        Common.setPreferenceString(MainActivity.activity, "GroupID", "");
                        Common.setPreferenceString(MainActivity.activity, "GroupName", "");
                        Common.setPreferenceString(MainActivity.activity, "GST", "");
                        Common.setPreferenceString(MainActivity.activity, "LoginId", "");
                        Common.setPreferenceString(MainActivity.activity, "LoginId", "");
                        Common.setPreferenceString(MainActivity.activity, "Name", "");
                        Common.setPreferenceString(MainActivity.activity, "Number", "");
                        Common.setPreferenceString(MainActivity.activity, "password", "");
                        Common.setPreferenceString(MainActivity.activity, "Status", "");
                        Common.setPreferenceString(MainActivity.activity, "status_code", "");
                        Common.setPreferenceString(MainActivity.activity, "UserName", "");
                        Common.setPreferenceString(MainActivity.activity, "TokenLogin", "");
                        Common.setPreferenceString(MainActivity.activity, "Firmname", "");
                        Common.setPreferenceString(MainActivity.activity, "DisplayName", "");

                        Intent TreadeInsert = new Intent(MainActivity.activity, MainActivity.class);
                        TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(TreadeInsert);
                        getActivity().finish();
                    }

                }
            });

        }

    }


    private void setAdapter() {
        try {

            list_spotSymbol = new ArrayList<>();
            spotRateAdapter = new SpotRateAdapter(new ArrayList<LiverateSymbolModel.MCXBean>(), getActivity());
            RecyclerView.LayoutManager homelayoutmanager2 = new GridLayoutManager(getActivity(), 2);
            rv_future_product.setLayoutManager(homelayoutmanager2);
            rv_future_product.setItemAnimator(new DefaultItemAnimator());
            rv_future_product.setNestedScrollingEnabled(false);
            rv_future_product.setAdapter(spotRateAdapter);


            liveRateMainAdapter = new LiveRateMainAdapter(getActivity());
            RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
            rv_main_product.setLayoutManager(homelayoutmanager);
            rv_main_product.setItemAnimator(new DefaultItemAnimator());
            rv_main_product.setNestedScrollingEnabled(false);
            rv_main_product.setAdapter(liveRateMainAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void GetInfoClient() {
        try {
            try {
                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                try {
//                    mySSLContext.init(null, trustAllCerts, null);
//                    IO.setDefaultSSLContext(mySSLContext);
//                    //mSocket = IO.socket(URL);
//                    IO.Options opts = new IO.Options();
//
//                    opts.reconnection = true;
//                    opts.reconnectionDelay = 1000;
//                    opts.timeout = 50000;
//
//                    opts.transports = new String[]{WebSocket.NAME};
                    NewSocket = io.socket.client.IO.socket(Constants.SocketUrl);
                    NewSocket.connect();

//                    NewSocket = IO.socket(Constants.SocketUrl, opts);


                    if (NewSocket.connected()) {
                        FluteerDate();
                    } else {


                    }


                    NewSocket.on(Socket.EVENT_CONNECT, new io.socket.client.Socket.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.e("Test1", "Conncet");
                            FluteerDate();

                        }

                    }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Infoget2==", arg0[0].toString());

                        }
                    }).on("something_changed", new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Infoget3==", obj.toString());

                        }
                    });

                    NewSocket.connect();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try {
            Log.e("Starline3555", "Runnnnnn");
            try {
                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                try {
//                    mySSLContext.init(null, trustAllCerts, null);
//                    IO.setDefaultSSLContext(mySSLContext);
//                    // mSocket10008 = IO.socket(URL22);
//
//
//                    IO.Options opts = new IO.Options();
//
//                    opts.reconnection = true;
//                    opts.reconnectionDelay = 1000;
//                    opts.timeout = 50000;
//
//                    opts.transports = new String[]{WebSocket.NAME};
                    mSocket10008 = io.socket.client.IO.socket(Constants.SocketUrl);
                    mSocket10008.connect();

//                    mSocket10008 = IO.socket(Constants.SocketUrl, opts);

                    if (mSocket10008.connected()) {
                        LiveRateSoket();
                        Log.e("Starline1", "iff");

                    } else {
                        Log.e("Starline1", "sleeee");
                    }

                    mSocket10008.on(Socket.EVENT_CONNECT, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {

                            Log.e("Starline1", "Conncet");

                            LiveRateSoket();


                        }
                    }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Starline2", arg0[0].toString());

                        }
                    }).on("something_changed", new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Starline3", obj.toString());

                        }
                    });

                    mSocket10008.connect();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                Log.e("Starline3555", e.toString());
                e.printStackTrace();
            }

        } catch (Exception e) {
            Log.e("Starline355", e.toString());

            throw new RuntimeException(e);
        }


    }


    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }};


    @SuppressLint("StaticFieldLeak")
    private void getSilder() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetSliderByClient);
                request.addProperty("ClientID", 1);

                try {
                    bankDetailsResponse = loadService(request, Constants.GetSliderByClient);
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

                    Log.e("sliderrresponce", "" + response);
                    if (response.equals("[]")) {

                    } else {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            String imageurl = json_data.getString("SliderImagePath");
                            String RedirectLink = json_data.getString("RedirectLink");

                            sliderDataArrayList.add(new SliderData(imageurl, RedirectLink));

                            SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);
                            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setScrollTimeInSec(3);
                            sliderView.setAutoCycle(true);
                            sliderView.startAutoCycle();
                        }
                    }


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
