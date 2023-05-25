package com.VijayAssayCenter.adapter;

import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;
import static com.VijayAssayCenter.utils.Constants.InsertCoinOpenOrderDetailWithRegID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.Login;
import com.VijayAssayCenter.model.GetCoinKey;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.transports.WebSocket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jatinkintu on 12/12/17.
 */

public class AdapterCoinRate extends RecyclerView.Adapter<AdapterCoinRate.MyViewHolder> {

    private Activity activity;
    private List<GetCoinKey> PaperList = new ArrayList<GetCoinKey>();
    private LayoutInflater layoutInflater;
    private String product_id;
    Animation animFadein;
    int Qunty = 1;
    String regresponse = "";
    String name = "";

    Double chekAmount = null;
    Double TotalAmount = null;
    AsyncProgressDialog ad;

    private static final String URL22 = "https://vickygold.co.in:10008";
    private io.socket.client.Socket mSocket10008;
    String LoginToken = "";

    String ObjVariable = "";


    private List<Boolean> isclick = new ArrayList<>();
    int red, green, black, white;

    AlertDialog abstudent;


    public AdapterCoinRate(Activity activity) {
        this.activity = activity;
        red = ContextCompat.getColor(activity, R.color.red);
        green = ContextCompat.getColor(activity, R.color.green);
        black = ContextCompat.getColor(activity, R.color.black);
        white = ContextCompat.getColor(activity, R.color.white);

    }

    public void addAll(List<GetCoinKey> bannerList) {
        this.PaperList = bannerList;


//        for (int i = 0; i < bannerList.size(); i++) {
//            isclick.add(i, false);
//
//        }
        //notifyDataSetChanged();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_rate, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tv_name.setText(PaperList.get(position).getCoinsName());
        //holder.tv_ask_rate.setText(PaperList.get(position).getAsk());
        //  holder.tv_ask_rate.setTextColor(green);


        Log.e("GoldCoinIsDisplay====", "" + Constants.GoldCoinIsDisplay);
        Log.e("SilverCoinIsDisplay====", "" + Constants.SilverCoinIsDisplay);

        String imageUri = "https://vickygold.co.in/" + PaperList.get(position).getCoinsImagePath();
        // Log.e("jkjgkfjgk",imageUri);
        //https://i.imgur.com/tGbaZCY.jpg

        // String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        // Picasso.with(activity).load(imageUri).into(holder.iv_menu);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        if (PaperList.get(position).getAsk().equalsIgnoreCase("--")) {

            holder.tv_ask_rate.setBackgroundResource(0);
            holder.tv_ask_rate.setText(PaperList.get(position).getAsk());
            holder.tv_ask_rate.setTextColor(black);
            holder.tv_rs.setTextColor(black);
        } else {

            if (!holder.tv_ask_rate.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.tv_ask_rate.getText())) {

                if (Double.parseDouble(PaperList.get(position).getAsk()) != Double.parseDouble(holder.tv_ask_rate.getText().toString())) {

//                        holder.tv_ask_rate.setBackgroundResource(Double.parseDouble(PaperList.get(position).getAsk())
//                                < Double.parseDouble(holder.tv_ask_rate.getText().toString()) ? R.drawable.liverate_red : R.drawable.liverate_green);

                    if (Double.parseDouble(PaperList.get(position).getAsk()) < Double.parseDouble(holder.tv_ask_rate.getText().toString())) {
                        holder.tv_ask_rate.setTextColor(red);
                        holder.tv_rs.setTextColor(red);

                    } else {
                        holder.tv_ask_rate.setTextColor(green);
                        holder.tv_rs.setTextColor(green);

                    }

                    holder.tv_ask_rate.setText(PaperList.get(position).getAsk());

                } else {
                    holder.tv_ask_rate.setText(PaperList.get(position).getAsk());
                    holder.tv_ask_rate.setTextColor(black);
                    holder.tv_rs.setTextColor(black);
                    holder.tv_ask_rate.setBackgroundResource(0);
                }
            } else {
                holder.tv_ask_rate.setText(PaperList.get(position).getAsk());
                holder.tv_ask_rate.setTextColor(black);
                holder.tv_rs.setTextColor(black);
                holder.tv_rs.setTextColor(black);
                holder.tv_ask_rate.setBackgroundResource(0);
            }
        }


        Glide.with(activity).load(imageUri).apply(options).into(holder.iv_menu);
        if (PaperList.get(position).getCoinsBase().equals("silver")) {
            holder.tv_by_now.setText("BUY SILVER COIN");

        } else {
            holder.tv_by_now.setText("BUY GOLD COIN");
        }

        if (PaperList.get(position).getStock().equals("true")) {
            holder.iv_tham.setBackgroundResource(R.drawable.thamup);


        } else {
            holder.iv_tham.setBackgroundResource(R.drawable.thamdown);

        }

        if (Constants.GoldCoinIsDisplay.equals("true") && Constants.SilverCoinIsDisplay.equals("true")) {
            holder.lay_item_live.setVisibility(View.VISIBLE);

        } else {
            if (Constants.SilverCoinIsDisplay.equals("true")) {
                if (PaperList.get(position).getCoinsBase().equals("silver")) {
                    holder.lay_item_live.setVisibility(View.VISIBLE);

                } else {
                    holder.lay_item_live.setVisibility(View.GONE);
                }
            }

            if (Constants.GoldCoinIsDisplay.equals("true")) {
                if (PaperList.get(position).getCoinsBase().equals("gold")) {
                    holder.lay_item_live.setVisibility(View.VISIBLE);

                } else {
                    holder.lay_item_live.setVisibility(View.GONE);
                }
            }
        }


        holder.tv_by_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String UserName = Common.getPreferenceString(activity, "UserName", "");
                Log.e("UserName", UserName);
                if (UserName.equals("")) {
                    Intent TreadeInsert = new Intent(activity, Login.class);
                    TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(TreadeInsert);
                } else {

                    try {

                        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dailog_coin);

                        TextView tv_symob_name = dialog.findViewById(R.id.tv_symob_name);
                        final TextView tv_amount = dialog.findViewById(R.id.tv_amount);
                        final TextView tv_total = dialog.findViewById(R.id.tv_total);
                        tv_symob_name.setText(PaperList.get(position).getCoinsName());
                        tv_amount.setText(PaperList.get(position).getAsk());

                        DecimalFormat df2 = new DecimalFormat("0.00");
                        chekAmount = Double.valueOf(PaperList.get(position).getAsk());
                        TotalAmount = chekAmount * Qunty;
                        tv_total.setText("Total: " + df2.format(TotalAmount));

                        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        final EditText tv_manual_quntite = dialog.findViewById(R.id.tv_manual_quntite);
                        tv_manual_quntite.setSelection(tv_manual_quntite.getText().length());

                        ImageView iv_plus = dialog.findViewById(R.id.iv_plus);
                        ImageView iv_minus = dialog.findViewById(R.id.iv_minus);
                        iv_plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (tv_manual_quntite.getText().toString().trim().length() == 0) {

                                } else {
                                    Qunty = Integer.parseInt(tv_manual_quntite.getText().toString());
                                    Qunty++;
                                    tv_manual_quntite.setText("" + Qunty);


                                }

                            }
                        });

                        tv_manual_quntite.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                Log.e("WhatClick", "" + s);


                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                if (tv_manual_quntite.getText().toString().trim().length() == 0) {
                                    tv_total.setText("Total: 00.00");

                                } else {
                                    DecimalFormat df2 = new DecimalFormat("0.00");
                                    Qunty = Integer.parseInt(tv_manual_quntite.getText().toString());
                                    TotalAmount = chekAmount * Qunty;
                                    tv_total.setText("Total: " + df2.format(TotalAmount));
                                }

                                // TODO Auto-generated method stub
                            }
                        });

                        iv_minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (tv_manual_quntite.getText().toString().trim().length() == 0) {

                                } else {
                                    Qunty = Integer.parseInt(tv_manual_quntite.getText().toString());
                                    if (Qunty == 1) {

                                    } else {
                                        Qunty--;
                                        tv_manual_quntite.setText("" + Qunty);
                                    }

                                }

                            }
                        });

                        TextView buy_now = dialog.findViewById(R.id.buy_now);
                        buy_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (tv_manual_quntite.getText().toString().trim().length() == 0) {
                                    tv_manual_quntite.setError("QUANTITY");
                                    tv_manual_quntite.requestFocus();
                                } else {
                                    String TradeType = "1";
                                    String LimitPrice = "";
                                    LoginToken = Common.getPreferenceString(activity, "TokenLogin", "");

                                    ObjVariable = "{" +
                                            "\"SymbolId\":\"" + PaperList.get(position).getCoinsId() + "\"," +
                                            "\"Quantity\":\"" + Qunty + "\"," +
                                            "\"TradeFrom\":\"android\"," +
                                            "\"TradeType\":\"" + TradeType + "\"," +
                                            "\"DeviceToken\":\"" + "" + "\"," +
                                            "\"BuyLimitPrice\":\"" + LimitPrice + "\"," +
                                            "\"SellLimitPrice\":\"" + "0" + "\"," +
                                            "\"Token\":\"" + LoginToken + "\"" +
                                            "}";

                                    name = PaperList.get(position).getCoinsBase();
                                    Log.e("TreadeObject", ObjVariable);
                                    dialog.dismiss();
                                    new Register().execute();
                                }
                            }
                        });

                        try {
                            Log.e("Starline3555", "Runnnnnn");
                            try {
                                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                                try {
//                                    mySSLContext.init(null, trustAllCerts, null);
//                                    IO.setDefaultSSLContext(mySSLContext);
//                                    // mSocket10008 = IO.socket(URL22);
//
//
//                                    IO.Options opts = new IO.Options();
//
//                                    opts.reconnection = true;
//                                    opts.reconnectionDelay = 1000;
//                                    opts.timeout = 50000;
//
//                                    opts.transports = new String[]{WebSocket.NAME};
//                                    mSocket10008 = IO.socket(URL22, opts);
                                    mSocket10008 = io.socket.client.IO.socket(Constants.SocketUrl);
                                    mSocket10008.connect();
                                    if (mSocket10008.connected()) {
                                        Log.e("DFDFDFDF", "iff");

                                        mSocket10008.emit("room", "vickygold").on("coinrate", new io.socket.client.Socket.Listener() {
                                            @Override
                                            public void call(final Object... args) {
                                                Log.e("FDFDFDFDFD", args[0].toString());
                                                try {
                                                    JSONObject result = null;

                                                    result = new JSONObject("" + args[0].toString());
                                                    Log.e("CKCKKKCKC", result.toString());
                                                    final String Rate = result.getString("CoinRate");

                                                    activity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            JSONArray resultArray = null;
                                                            try {
                                                                resultArray = new JSONArray(Rate);
                                                                for (int i = 0; i < resultArray.length(); i++) {
                                                                    JSONObject p = (JSONObject) resultArray.get(i);

                                                                    String CoinsId = p.getString("CoinsId");
                                                                    Log.e("CoinsId", CoinsId);

                                                                    if (PaperList.get(position).getCoinsId().equals(CoinsId)) {

                                                                        if (tv_manual_quntite.getText().toString().trim().length() == 0) {
                                                                            tv_total.setText("Total: 00.00");

                                                                        } else {
                                                                            chekAmount = Double.valueOf(p.getString("Ask"));

                                                                            DecimalFormat df2 = new DecimalFormat("0.00");
                                                                            Qunty = Integer.parseInt(tv_manual_quntite.getText().toString());
                                                                            TotalAmount = chekAmount * Qunty;
                                                                            tv_total.setText("Total: " + df2.format(TotalAmount));

                                                                            //tv_amount.setText(p.getString("Ask"));
                                                                            String Asknew = p.getString("Ask");


                                                                            if (Asknew.equalsIgnoreCase("--")) {

                                                                                tv_amount.setText(Asknew);
                                                                                tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                            } else {
                                                                                if (!tv_amount.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(tv_amount.getText())) {

                                                                                    if (Double.parseDouble(Asknew) != Double.parseDouble(tv_amount.getText().toString())) {

                                                                                        //tv_amount.setBackgroundResource(Double.parseDouble(Asknew) < Double.parseDouble(tv_amount.getText().toString()) ? R.drawable.liverate_red : R.drawable.liverate_green);

                                                                                        if (Double.parseDouble(Asknew) < Double.parseDouble(tv_amount.getText().toString())) {
                                                                                            tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.red));


                                                                                        } else {
                                                                                            tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.green));

                                                                                        }
                                                                                        tv_amount.setText(Asknew);
                                                                                    } else {
                                                                                        tv_amount.setText(Asknew);
                                                                                        tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                                    }
                                                                                } else {
                                                                                    tv_amount.setText(Asknew);
                                                                                    tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                                }
                                                                            }


                                                                        }

                                                                    } else {

                                                                    }


                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });


                                                } catch (JSONException e) {
                                                    Log.d("errrrrrrorrrlog", "run: " + e.getMessage());
                                                    e.printStackTrace();
                                                }


                                            }
                                        });

                                    } else {
                                        Log.e("Starline1", "sleeee");
                                    }

                                    mSocket10008.on(Socket.EVENT_CONNECT, new io.socket.client.Socket.Listener() {
                                        @Override
                                        public void call(Object... args) {

                                            Log.e("Starline1", "Conncet");


                                        }
                                    }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.client.Socket.Listener() {
                                        @Override
                                        public void call(Object... arg0) {

                                            Log.e("Starline2", arg0[0].toString());

                                        }
                                    }).on("something_changed", new io.socket.client.Socket.Listener() {
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


                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.CENTER);
                        dialog.setCanceledOnTouchOutside(false);


                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return PaperList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name;
        TextView tv_by_now;
        TextView tv_ask_rate;
        TextView tv_rs;
        ImageView iv_menu;
        ImageView iv_tham;
        LinearLayout lay_item_live;

        public MyViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.tv_name);
            tv_ask_rate = view.findViewById(R.id.tv_ask_rate);
            tv_by_now = view.findViewById(R.id.tv_by_now);
            iv_menu = view.findViewById(R.id.iv_menu);
            iv_tham = view.findViewById(R.id.iv_tham);
            lay_item_live = view.findViewById(R.id.lay_item_live);
            tv_rs = view.findViewById(R.id.tv_rs);
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


    private class Register extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", InsertCoinOpenOrderDetailWithRegID);
            request.addProperty("ObjOrder", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, InsertCoinOpenOrderDetailWithRegID);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {

                dismissProgress();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TradeResponcedddddd", "" + s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnCode = json.getString("ReturnCode");
                            if (ReturnCode.equals("200")) {

                                String OpeOrder = json.getString("OpeOrder");
                                //Log.e("diiid",OpeOrder);

                                if (OpeOrder.equals("")) {
                                    String ReturnMsg = json.getString("ReturnMsg");
                                    new android.app.AlertDialog.Builder(activity)
                                            .setTitle("Vicky Jewellery Works")
                                            .setMessage(ReturnMsg)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                }
                                            })
                                            .show();
                                } else {
                                    JSONArray jArray = new JSONArray(OpeOrder);
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject json_data = jArray.getJSONObject(i);
                                        Constants.DealNo = json_data.getString("DealNo");
                                        Constants.ProductionName = json_data.getString("SymbolName");
                                        Constants.Volume = json_data.getString("Volume");
                                        Constants.OrderRate = json_data.getString("Rate");
                                        Constants.Total = json_data.getString("Total");

                                    }

                                    Log.e("ConstantsVolume", Constants.Volume);
                                    Log.e("ConstantsPrize", Constants.Prize);
                                    Log.e("ConstantsOrderRate", Constants.OrderRate);


                                    if (Constants.DealNo.equals("")) {

                                    } else {
                                        Constants.Messsage = json.getString("ReturnMsg");
                                        Constants.Sorces = name;
                                        Intent Orderconfirmationpage = new Intent(activity, com.VijayAssayCenter.activity.CoinOrderConfirm.class);
                                        Orderconfirmationpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.startActivity(Orderconfirmationpage);
                                    }
                                }


                            } else {
                                String ReturnMsg = json.getString("ReturnMsg");
                                new android.app.AlertDialog.Builder(activity)
                                        .setTitle("Vicky Jewellery Works")
                                        .setMessage(ReturnMsg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation
                                            }
                                        })
                                        .show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TradeResponce", e.getMessage());

                        }

                    }
                });

            } catch (Exception e) {
                Log.e("TradeResponce", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void showProgress(String msg) {

        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(activity);
            ad.show(msg);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void dismissProgress() {
        try {
            if (ad != null) {
                ad.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
