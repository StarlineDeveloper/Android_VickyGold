package com.VijayAssayCenter.Fragmet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
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
import com.VijayAssayCenter.activity.ApplicationController;
import com.VijayAssayCenter.adapter.AdapterCoinRate;
import com.VijayAssayCenter.font.MyTextViewBold;
import com.VijayAssayCenter.model.GetCoinKey;
import com.VijayAssayCenter.utils.Constants;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class CoinRate extends Fragment {


    RecyclerView rv_coin_rate;
    private io.socket.client.Socket mSocket10008;
    private static final String URL22 = "https://vickygold.co.in:10008";
    AdapterCoinRate adapterCoinRate;
    io.socket.client.Socket NewSocket;
    MyTextViewBold tv_number;
    TextView tvLiveRateNotAvailable;
    private static final String URLNew = "https://vickygold.co.in:10001";

    public CoinRate() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tutorials, container, false);

        rv_coin_rate=view.findViewById(R.id.rv_coin_rate);
        tv_number=view.findViewById(R.id.tv_numberv);
        adapterCoinRate = new AdapterCoinRate(getActivity());
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
        rv_coin_rate.setLayoutManager(homelayoutmanager);
        rv_coin_rate.setNestedScrollingEnabled(false);
        rv_coin_rate.setItemAnimator(new DefaultItemAnimator());
        rv_coin_rate.setAdapter(adapterCoinRate);
        tvLiveRateNotAvailable=view.findViewById(R.id.tvLiveRateNotAvailable);
        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRedirect( "9331113115");
            }
        });

        return view;
    }

    private void callRedirect(String s) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + s));
        getContext().startActivity(callIntent);
    }


    @Override
    public void onResume() {
        GetInfoClient();
//        ApplicationController.getInstance().trackScreenView("Coin Rate Screen_Android");

        //  LiveRateSoket();
        super.onResume();
    }





    public void GetInfoClient(){

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
//                    opts.transports = new String[] { WebSocket.NAME };
//                    NewSocket = IO.socket(Constants.SocketUrl, opts);
                    NewSocket = io.socket.client.IO.socket(Constants.SocketUrl);
                    NewSocket.connect();


                    if(NewSocket.connected()){
                        FluteerDate();
                    }else {


                    }


                    NewSocket.on(Socket.EVENT_CONNECT,  new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.e("Test1","Conncet");
                            FluteerDate();

                        }

                    }).on(Socket.EVENT_CONNECT_ERROR,  new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Infoget2==",arg0[0].toString());

                        }
                    }).on("something_changed", new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Infoget3==",obj.toString());

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
            Log.e("Starline3555","Runnnnnn");
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
//                    opts.transports = new String[] { WebSocket.NAME };
//                    mSocket10008 = IO.socket(Constants.SocketUrl, opts);
                    mSocket10008 = io.socket.client.IO.socket(Constants.SocketUrl);
                    mSocket10008.connect();
                    if(mSocket10008.connected()){
                        LiveRateSoket();
                        Log.e("Starline1","iff");

                    }else {
                        Log.e("Starline1","sleeee");
                    }

                    mSocket10008.on(Socket.EVENT_CONNECT,new io.socket.emitter.Emitter.Listener(){
                        @Override
                        public void call(Object... args) {

                            Log.e("Starline1","Conncet");

                            LiveRateSoket();


                        }
                    }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Starline2",arg0[0].toString());

                        }
                    }).on("something_changed",new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Starline3",obj.toString());

                        }
                    });

                    mSocket10008.connect();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                Log.e("Starline3555",e.toString());

                e.printStackTrace();
            }

        } catch (Exception e) {
            Log.e("Starline355",e.toString());

            throw new RuntimeException(e);
        }




    }

    public void LiveRateSoket() {

        mSocket10008.emit("room", Constants.prjName).on("coinrate", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.e("connnn",args[0].toString());


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject result = null;

                            result = new JSONObject(""+args[0]);
                            String Rate=result.getString("CoinRate");
                            String Valueis="";

                            ArrayList<String> aListgold = new ArrayList<String>();
                            ArrayList<String> aListsilver = new ArrayList<String>();

                            JSONArray jArray = new JSONArray(Rate);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject json_data = jArray.getJSONObject(i);

                                String CoinsBase=json_data.getString("CoinsBase");
                                if(CoinsBase.equals("gold")){
                                    String CoinsId=json_data.getString("CoinsId");
                                    String CoinsName=json_data.getString("CoinsName");
                                    String Ask=json_data.getString("Ask");
                                    String Bid=json_data.getString("Bid");
                                    String Stock=json_data.getString("Stock");
                                    String CoinsImagePath=json_data.getString("CoinsImagePath");

                                    Valueis="{\"CoinsId\":\""+CoinsId+"\",\n" +
                                            "\"CoinsName\":\""+CoinsName+"\",\n" +
                                            "\"CoinsBase\":\""+CoinsBase+"\",\n" +
                                            "\"Ask\":\""+Ask+"\",\n" +
                                            "\"Bid\":\""+Bid+"\",\n" +
                                            "\"Stock\":\""+Stock+"\",\n" +
                                            "\"CoinsImagePath\":\""+CoinsImagePath+"\"}";
                                    aListgold.add(Valueis);


                                }else {

                                    String CoinsId=json_data.getString("CoinsId");
                                    String CoinsName=json_data.getString("CoinsName");
                                    String Ask=json_data.getString("Ask");
                                    String Bid=json_data.getString("Bid");
                                    String Stock=json_data.getString("Stock");
                                    String CoinsImagePath=json_data.getString("CoinsImagePath");
                                    Log.e("huuduudd",CoinsId);
                                    Valueis="{\"CoinsId\":\""+CoinsId+"\",\n" +
                                            "\"CoinsName\":\""+CoinsName+"\",\n" +
                                            "\"CoinsBase\":\""+CoinsBase+"\",\n" +
                                            "\"Ask\":\""+Ask+"\",\n" +
                                            "\"Bid\":\""+Bid+"\",\n" +
                                            "\"Stock\":\""+Stock+"\",\n" +
                                            "\"CoinsImagePath\":\""+CoinsImagePath+"\"}";

                                    aListsilver.add(Valueis);

                                }



                            }

                            Log.e("huuduudd", String.valueOf(aListsilver));

                            String Rateneww=String.valueOf(aListgold)+String.valueOf(aListsilver);


                            ArrayList<String> SnoTitles3;
                            SnoTitles3 = new ArrayList<String>();
                            SnoTitles3.addAll(aListgold);
                            SnoTitles3.addAll(aListsilver);


                            adapterCoinRate.addAll((List<GetCoinKey>) new Gson().fromJson(String.valueOf(SnoTitles3), new TypeToken<ArrayList<GetCoinKey>>() {}.getType()));

                            Rate="";




                        } catch (JSONException e) {
                            Log.d("errrrrrrorrrlog", "run: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

   }


    public void FluteerDate(){
        Log.e("jkjfdjfkdlfkjdfk","Sucesss");
        NewSocket.emit("ClientData",Constants.prjName).on("ClientData", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.e("ClientData",""+args[0]);

            }
        });

        NewSocket.emit("Client",Constants.prjName).on("ClientHeaderDetails", new io.socket.emitter.Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject result = null;
                        try {
                            result = new JSONObject(""+args[0]);
                            final String Data=result.getString("Data");
                            Log.e("hhhhdhdhdhd",""+Data);


                            JSONArray resultArray = null;
                            try {
                                resultArray = new JSONArray(Data);
                                for (int i = 0; i < resultArray.length(); i++) {


                                    JSONObject p = (JSONObject)resultArray.get(i);
                                    String LiveratenotAvlable = p.getString("CoinRateDisplay");

                                    Constants.GoldCoinIsDisplay = p.getString("GoldCoinIsDisplay");
                                    Constants.SilverCoinIsDisplay = p.getString("SilverCoinIsDisplay");

                                    Log.e("LiveratenotAvlable",""+LiveratenotAvlable);
                                    Log.e("GoldCoinIsDisplay",""+Constants.GoldCoinIsDisplay);
                                    Log.e("SilverCoinIsDisplay",""+Constants.SilverCoinIsDisplay);

                                    if(LiveratenotAvlable.equals("true")){
                                        rv_coin_rate.setVisibility(View.VISIBLE);
                                        tvLiveRateNotAvailable.setVisibility(View.GONE);
                                    }
                                    else {
                                        rv_coin_rate.setVisibility(View.GONE);
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






            }
        });





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
}
