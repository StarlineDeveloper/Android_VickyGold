package com.VijayAssayCenter.adapter;

import static com.VijayAssayCenter.utils.BaseActivity.decimalFormat;
import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;
import static com.VijayAssayCenter.utils.Constants.InsertOpenOrderDetailWithRegID;
import static com.VijayAssayCenter.utils.Utils.hideKeyBoard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.Login;
import com.VijayAssayCenter.activity.Orderconfirmationpage;
import com.VijayAssayCenter.model.GetMainRate;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.Socket;

/**
 * Created by jatinkintu on 12/12/17.
 */

public class LiveRateMainAdapter extends RecyclerView.Adapter<LiveRateMainAdapter.MyViewHolder> {

    private Activity activity;
    private List<GetMainRate> PaperList = new ArrayList<GetMainRate>();
    private LayoutInflater layoutInflater;
    private String product_id;
    Animation animFadein;
    private List<Boolean> isclick = new ArrayList<>();
    TextView tv_selection;
    AlertDialog abstudent;
    Socket mSocket10008;
    Socket mSocket1000;
    String SymbolIdMain = "";
    private static final String URL = "https://vickygold.co.in:10008";
    String SorceisEew = "";

    AsyncProgressDialog ad;

    String regresponse = "";

    private PopupWindow popupWindowGram;

    int min = 0;
    int max = 0;
    double step = 0;
    int min1 = 0;
    int max1 = 0;
    String name = "";
    String ObjVariable = "";
    String TradeType = "1";
    String SellTradeType = "2";
    String LoginToken = "";
    String ValueIs = "Y";

    String Quintry = "";
    int AmountQuintry = 0;
    private int red, green, black, white;

    String Comment = "";

    Double Amount = 0.0;
    String LimitPrice = "0";

    String WhatClick = "gram";


    public LiveRateMainAdapter(Activity activity) {
        this.activity = activity;


        //    layoutInflater = LayoutInflater.from(activity);
//        for (int i = 0; i < 2; i++) {
//            PaperList.add("");
//            isclick.add(i, false);
//
//
//        }
    }

    public void addAll(List<GetMainRate> bannerList) {
        this.PaperList = bannerList;
        red = ContextCompat.getColor(activity, R.color.red);
        green = ContextCompat.getColor(activity, R.color.green);
        black = ContextCompat.getColor(activity, R.color.black);
        white = ContextCompat.getColor(activity, R.color.white);

//        for (int i = 0; i < bannerList.size(); i++) {
//            isclick.add(i, false);
//
//        }
        //notifyDataSetChanged();
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String UserName = Common.getPreferenceString(activity, "UserName", "");
        Log.e("UserName", UserName);
        Log.e("Symobloneme", PaperList.get(position).getSymbol());
        holder.tv_hight.setText("High-" + PaperList.get(position).getHigh());
        holder.tv_low.setText("Low-" + PaperList.get(position).getLow());
        if (Constants.HighRate.equals("true")) {
            holder.tv_hight.setVisibility(View.VISIBLE);
        } else {
            holder.tv_hight.setVisibility(View.GONE);
        }
        if (Constants.LowRate.equals("true")) {
            holder.tv_low.setVisibility(View.VISIBLE);
        } else {
            holder.tv_low.setVisibility(View.GONE);
        }
        if (UserName.equals("")) {

            if (PaperList.get(position).getIsDisplay().trim().equals("True")) {
                holder.lay_item_live.setVisibility(View.VISIBLE);
            } else {
                holder.lay_item_live.setVisibility(View.GONE);
            }

            if (PaperList.get(position).getSource().equals("Gold")) {
                holder.tv_gram.setText(PaperList.get(position).getGrams() + " gm");
                holder.iv_menu.setBackgroundResource(R.drawable.gold);
            } else {
                holder.tv_gram.setText(PaperList.get(position).getGrams() + " kg");
                holder.iv_menu.setBackgroundResource(R.drawable.silver);

            }


            if (PaperList.get(position).getAsk().equalsIgnoreCase("--")) {
                holder.ask_rate.setBackgroundResource(0);
                holder.ask_rate.setText(PaperList.get(position).getAsk());
                holder.ask_rate.setTextColor(black);
            } else {
                if (!holder.ask_rate.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.ask_rate.getText())) {
                    if (Double.parseDouble(PaperList.get(position).getAsk()) != Double.parseDouble(holder.ask_rate.getText().toString())) {
                        if (Double.parseDouble(PaperList.get(position).getAsk()) < Double.parseDouble(holder.ask_rate.getText().toString())) {
                            holder.ask_rate.setTextColor(red);

                        } else {
                            holder.ask_rate.setTextColor(green);

                        }
                        holder.ask_rate.setText(PaperList.get(position).getAsk());
                    } else {
                        holder.ask_rate.setText(PaperList.get(position).getAsk());
                        holder.ask_rate.setTextColor(black);
                        holder.ask_rate.setBackgroundResource(0);
                    }
                } else {
                    holder.ask_rate.setText(PaperList.get(position).getAsk());
                    holder.ask_rate.setTextColor(black);
                    holder.ask_rate.setBackgroundResource(0);
                }
            }


        } else {

            Log.e("List", "" + Constants.SellPremiumIDDDD);

            holder.lay_item_live.setVisibility(View.GONE);
            Log.e("ssssss", "jjjj" + Constants.GrupStaus);
            if (Constants.GrupStaus.equals("true")) {
                String DisplayId = "";
                for (int i = 0; i < Constants.SellPremiumIDDDD.size(); i++) {
                    DisplayId = "" + Constants.SellPremiumIDDDD.get(i);
                    //Log.e("DisplayId",DisplayId);
                    for (int j = 0; j < PaperList.size(); j++) {
                        String SymbolId = String.valueOf(PaperList.get(j).getSymbolId());
                        String DisplayIs = String.valueOf(PaperList.get(j).getIsDisplayTerminal());
                        //Log.e("jjj", "" + jjj);
                        if (DisplayId.equals(SymbolId)) {
                            // Log.e("displaypos", "" + j);
                            if (position == j) {
                                if (DisplayIs.equals("True")) {
                                    holder.lay_item_live.setVisibility(View.VISIBLE);
                                } else {
                                    holder.lay_item_live.setVisibility(View.GONE);
                                }
                            } else {

                            }

                        } else {

                        }

                    }
                }
            } else {
                holder.lay_item_live.setVisibility(View.GONE);
            }


          /*     if(PaperList.get(position).getIsDisplayTerminal().equals("True")){
                   holder.lay_item_live.setVisibility(View.VISIBLE);

               }else if(PaperList.get(position).getIsDisplayTerminal().equals("False")){
                   holder.lay_item_live.setVisibility(View.GONE);

               }else {
                   holder.lay_item_live.setVisibility(View.GONE);

               }*/

            /*for(int j=0;j<PaperList.size();j++) {
                String jjj= String.valueOf(PaperList.get(j).getSymbolId());
                Log.e("posssss",""+j);

                if(IdSymbolllll.equals(jjj)){
                    Log.e("jjj",""+jjj);
                    if(position==j){
                        holder.lay_item_live.setVisibility(View.VISIBLE);
                    }

                }else {
                    holder.lay_item_live.setVisibility(View.GONE);

                }*/

            //}

            // Log.e("IdSymbol",""+IdSymbolllll);
            // Log.e("finalops",""+finalops);

//            if (IdSymbolllll.equals(""+finalops)) {
//                holder.lay_item_live.setVisibility(View.VISIBLE);
//                Log.e("teststs",""+finalops);
//
//            } else {
//                holder.lay_item_live.setVisibility(View.GONE);
//
//            }

            //


            if (PaperList.get(position).getSource().equals("Gold")) {
                holder.tv_gram.setText(PaperList.get(position).getGrams() + " gm");
                holder.iv_menu.setBackgroundResource(R.drawable.gold);

                int askprice = Integer.parseInt(PaperList.get(position).getAsk());

                Log.e("getSource", "" + PaperList.get(position).getSource());
                Log.e("askprice", "" + askprice);


                String AsknewGold = "";
                if (Constants.SellPremium.isEmpty()) {
                    int TotalRate = askprice + Constants.GoldSellPremium;
                    AsknewGold = String.valueOf(TotalRate);

                } else {
                    for (int i = 0; i < Constants.SellPremiumIDDDD.size(); i++) {
                        String IdSymbol = "" + Constants.SellPremiumIDDDD.get(i);
                        if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                            int SellPremium = Constants.SellPremium.get(i);
                            int TotalRate = askprice + Constants.GoldSellPremium + SellPremium;
                            AsknewGold = String.valueOf(TotalRate);

                            Log.e("AsknewGold", AsknewGold);

                        }


                    }


                }


                if (AsknewGold.equalsIgnoreCase("--")) {
                    holder.ask_rate.setBackgroundResource(0);
                    holder.ask_rate.setText(AsknewGold);
                    holder.ask_rate.setTextColor(black);
                } else {
                    if (!holder.ask_rate.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.ask_rate.getText())) {

                        if (AsknewGold.equals("") || AsknewGold == null) {
                            holder.ask_rate.setText(AsknewGold);
                            holder.ask_rate.setTextColor(black);
                            holder.ask_rate.setBackgroundResource(0);
                        } else {
                            if (Double.parseDouble(AsknewGold) != Double.parseDouble(holder.ask_rate.getText().toString())) {
                                if (Double.parseDouble(AsknewGold) < Double.parseDouble(holder.ask_rate.getText().toString())) {
                                    holder.ask_rate.setTextColor(red);
                                } else {
                                    holder.ask_rate.setTextColor(green);
                                }

                                holder.ask_rate.setText(AsknewGold);
                            } else {
                                holder.ask_rate.setText(AsknewGold);
                                holder.ask_rate.setTextColor(black);
                                holder.ask_rate.setBackgroundResource(0);
                            }
                        }


                    } else {
                        holder.ask_rate.setText(AsknewGold);
                        holder.ask_rate.setTextColor(black);
                        holder.ask_rate.setBackgroundResource(0);
                    }
                }


            } else {
                holder.tv_gram.setText(PaperList.get(position).getGrams() + " kg");
                holder.iv_menu.setBackgroundResource(R.drawable.silver);

                int askprice = Integer.parseInt(PaperList.get(position).getAsk());


                String Asknew = "";
                if (Constants.SellPremium.isEmpty()) {
                    int TotalRate = askprice + Constants.SilverSellPremium;
                    Asknew = String.valueOf(TotalRate);
                } else {


                    //  Log.e("pppppp",""+PaperList.get(position).getIsDisplayTerminal());
                    //  Log.e("pppppp",""+PaperList.get(position).getSymbolId());
                    //  Log.e("pppppp",""+Constants.SellPremium);
                    //  Log.e("pppppp",""+Constants.SellPremiumIDDDD);

                    for (int i = 0; i < Constants.SellPremiumIDDDD.size(); i++) {
                        String IdSymbol = "" + Constants.SellPremiumIDDDD.get(i);

                        Log.e("IdSymbol", IdSymbol);

                        if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                            int SellPremium = Constants.SellPremium.get(i);
                            Log.e("SellPremium", "" + SellPremium);

                            int TotalRate = askprice + Constants.SilverSellPremium + SellPremium;
                            Asknew = String.valueOf(TotalRate);
                        } else {
                           /* int TotalRate=askprice+Constants.SilverSellPremium;
                            Asknew= String.valueOf(TotalRate);*/
                        }


                    }

                }


                //holder.ask_rate.setText(Asknew);

                if (Asknew.equalsIgnoreCase("--")) {
                    holder.ask_rate.setBackgroundResource(0);
                    holder.ask_rate.setText(Asknew);
                    holder.ask_rate.setTextColor(black);
                } else {
                    if (!holder.ask_rate.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.ask_rate.getText())) {

                        if (Asknew.equals("") || Asknew == null) {
                            holder.ask_rate.setText(Asknew);
                            holder.ask_rate.setTextColor(black);
                            holder.ask_rate.setBackgroundResource(0);
                        } else {
                            if (Double.parseDouble(Asknew) != Double.parseDouble(holder.ask_rate.getText().toString())) {
                                if (Double.parseDouble(Asknew) < Double.parseDouble(holder.ask_rate.getText().toString())) {
                                    holder.ask_rate.setTextColor(red);

                                } else {
                                    holder.ask_rate.setTextColor(green);

                                }
                                holder.ask_rate.setText(Asknew);
                            } else {
                                holder.ask_rate.setText(Asknew);
                                holder.ask_rate.setTextColor(black);
                                holder.ask_rate.setBackgroundResource(0);
                            }
                        }

                    } else {
                        holder.ask_rate.setText(Asknew);
                        holder.ask_rate.setTextColor(black);
                        holder.ask_rate.setBackgroundResource(0);
                    }
                }


            }

        }


        holder.tv_name.setText(PaperList.get(position).getSymbol());
        Log.e("ask", "" + PaperList.get(position).getAsk());
        holder.tv_by_now.setText("BUY / SELL " + PaperList.get(position).getSource());

        //Log.e("DineshTest","testis ui");


//        holder.market_price.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.market_price.setBackgroundResource(R.drawable.btn1);
//                holder.future_rate.setBackgroundResource(R.drawable.white_btn);
//
//            }
//        });
//
//        holder.future_rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.future_rate.setBackgroundResource(R.drawable.btn1);
//                holder.market_price.setBackgroundResource(R.drawable.white_btn);
//            }
//        });
//
//        holder.buy_in_amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.buy_in_amount.setBackgroundResource(R.drawable.btn1);
//                holder.buy_in_gram.setBackgroundResource(R.drawable.white_btn);
//            }
//        });
//
//        holder.buy_in_gram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.buy_in_gram.setBackgroundResource(R.drawable.btn1);
//                holder.buy_in_amount.setBackgroundResource(R.drawable.white_btn);
//            }
//        });


        holder.lay_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//                Intent TreadeInsert=new Intent(activity, TreadeInsert.class);
//                TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                activity.startActivity(TreadeInsert);

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
                        dialog.setContentView(R.layout.dialog_treade);
                        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView tv_symob_name = dialog.findViewById(R.id.tv_symob_name);

                        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
                        final TextView buy_now = dialog.findViewById(R.id.buy_now);
                        final TextView sell_now = dialog.findViewById(R.id.sell_now);
                        final TextView tv_amount = dialog.findViewById(R.id.tv_amount);
                        final TextView tv_sellamount = dialog.findViewById(R.id.tv_sellamount);
                        final EditText et_comment = dialog.findViewById(R.id.et_comment);
                        tv_symob_name.setText(PaperList.get(position).getSymbol());
                        tv_amount.setText(holder.ask_rate.getText().toString().trim());
                        tv_sellamount.setText(holder.ask_rate.getText().toString().trim());
                        tv_selection = dialog.findViewById(R.id.tv_selection);


                        final String oldvalue = PaperList.get(position).getIsSymbolHedge();

                        final TextView tv_bit_4 = dialog.findViewById(R.id.tv_bit_4);
                        final TextView tv_bit_5 = dialog.findViewById(R.id.tv_bit_5);
                        final EditText tv_manual_quntite = dialog.findViewById(R.id.tv_manual_quntite);
                        ValueIs = "Y";

//                        min = 0.0;
//                        max = 0;
//
//                        min1 = 0;
//                        max1 = 0;

                        WhatClick = "gram";
                        TradeType = "1";
                        SellTradeType = "2";

                        final LinearLayout lay_gram_lay = dialog.findViewById(R.id.lay_gram_lay);
                        final LinearLayout lay_quintity = dialog.findViewById(R.id.lay_quintity);
                        final LinearLayout lay_staic_qut = dialog.findViewById(R.id.lay_staic_qut);
                        final LinearLayout manual_quty_layout = dialog.findViewById(R.id.manual_quty_layout);
                        final TextView or_lable = dialog.findViewById(R.id.or_lable);


                        if (PaperList.get(position).getIsSymbolHedge().equals("True")) {
                            lay_gram_lay.setVisibility(View.VISIBLE);
                            manual_quty_layout.setVisibility(View.VISIBLE);
                            or_lable.setVisibility(View.VISIBLE);
                        } else {
                            lay_gram_lay.setVisibility(View.VISIBLE);
                            manual_quty_layout.setVisibility(View.VISIBLE);
                            or_lable.setVisibility(View.VISIBLE);
                        }

                        final TextView tv_market = dialog.findViewById(R.id.tv_market);
                        final TextView tv_limit = dialog.findViewById(R.id.tv_limit);
                        final TextView buy_in_gram = dialog.findViewById(R.id.buy_in_gram);
                        final TextView tv_amoy = dialog.findViewById(R.id.tv_amoy);
                        final TextView tv_amoy22 = dialog.findViewById(R.id.tv_amoy22);

                        if (PaperList.get(position).getSource().equals("Gold")) {
                            tv_amoy.setVisibility(View.VISIBLE);
                            tv_amoy22.setVisibility(View.GONE);
                            manual_quty_layout.setVisibility(View.VISIBLE);
                            or_lable.setVisibility(View.VISIBLE);
                            if (PaperList.get(position).getCustomeValume().equals("0")) {
                                tv_amoy.setVisibility(View.GONE);
                                tv_amoy22.setVisibility(View.VISIBLE);
                            } else {
                                tv_amoy.setVisibility(View.VISIBLE);
                                tv_amoy22.setVisibility(View.GONE);
                            }

                        } else {
                            tv_amoy.setVisibility(View.GONE);
                            tv_amoy22.setVisibility(View.VISIBLE);

                            manual_quty_layout.setVisibility(View.GONE);
                            or_lable.setVisibility(View.GONE);
                        }

                        final EditText et_amount_test = dialog.findViewById(R.id.et_amount_test);
                        final EditText tv_parice = dialog.findViewById(R.id.tv_parice);

                        final LinearLayout lay_price = dialog.findViewById(R.id.lay_price);
                        final LinearLayout lay_amount = dialog.findViewById(R.id.lay_amount);

                        try {
                            tv_manual_quntite.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {

                                    if (tv_manual_quntite.getText().toString().trim().length() == 0) {

                                    } else {
                                        int entertest = Integer.parseInt(tv_manual_quntite.getText().toString().trim());
                                        if (max1 < entertest) {
                                            tv_manual_quntite.setError("Volume Not Between Min and Max Security Lot Size");
                                            tv_manual_quntite.requestFocus();
                                        } else if (min1 > entertest) {
                                            tv_manual_quntite.setError("Volume Not Between Min and Max Security Lot Size");
                                            tv_manual_quntite.requestFocus();
                                        } else {

                                        }
                                    }


                                }
                            });
                        } catch (Exception e) {

                        }

                        if (PaperList.get(position).getCustomeValume().equals("0")) {

                            manual_quty_layout.setVisibility(View.GONE);
                            lay_quintity.setVisibility(View.VISIBLE);
                            or_lable.setVisibility(View.GONE);

                        } else {

                            manual_quty_layout.setVisibility(View.VISIBLE);
                            lay_quintity.setVisibility(View.VISIBLE);
                            or_lable.setVisibility(View.VISIBLE);

                        }

                        tv_market.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                buy_in_gram.setTextColor(Color.WHITE);
                                buy_in_gram.setBackgroundResource(R.drawable.bi);

                                tv_amoy.setBackgroundResource(R.drawable.shape_null);
                                tv_amoy.setTextColor(Color.BLACK);
                                lay_staic_qut.setVisibility(View.VISIBLE);


                                tv_limit.setTextColor(Color.BLACK);
                                tv_limit.setBackgroundResource(R.drawable.shape_null);

                                tv_market.setTextColor(Color.WHITE);
                                tv_market.setBackgroundResource(R.drawable.bi);

                                lay_gram_lay.setVisibility(View.VISIBLE);
                                lay_price.setVisibility(View.GONE);
                                lay_quintity.setVisibility(View.GONE);

                                buy_now.setText("BUY");
                                sell_now.setText("SELL");
                                et_amount_test.setText("");
                                tv_parice.setText("");
                                lay_amount.setVisibility(View.GONE);
                                TradeType = "1";
                                SellTradeType = "2";
                                WhatClick = "market";

                                if (PaperList.get(position).getSource().equals("Gold")) {
                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);
                                    if (PaperList.get(position).getCustomeValume().equals("0")) {

                                        tv_amoy.setVisibility(View.GONE);
                                        tv_amoy22.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_amoy.setVisibility(View.VISIBLE);
                                        tv_amoy22.setVisibility(View.GONE);
                                    }

                                } else {
                                    manual_quty_layout.setVisibility(View.GONE);
                                    or_lable.setVisibility(View.GONE);
                                }


                                lay_gram_lay.setVisibility(View.VISIBLE);

                               /* if(PaperList.get(position).getIsSymbolHedge().equals("True")){
                                    lay_gram_lay.setVisibility(View.VISIBLE);
                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);
                                }else {
                                    lay_gram_lay.setVisibility(View.VISIBLE);
                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);
                                }*/

                                if (PaperList.get(position).getCustomeValume().equals("0")) {

                                    manual_quty_layout.setVisibility(View.GONE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.GONE);

                                } else {

                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);

                                }

                            }
                        });

                        tv_limit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                tv_limit.setTextColor(Color.WHITE);
                                tv_limit.setBackgroundResource(R.drawable.bi);

                                tv_market.setTextColor(Color.BLACK);
                                tv_market.setBackgroundResource(R.drawable.shape_null);

                                lay_quintity.setVisibility(View.VISIBLE);
                                lay_staic_qut.setVisibility(View.VISIBLE);

                                lay_price.setVisibility(View.VISIBLE);

                                buy_now.setText("BUY");
                                sell_now.setText("SELL");

                                WhatClick = "limit";

                                TradeType = "3";
                                SellTradeType = "4";
                                lay_gram_lay.setVisibility(View.GONE);

                                lay_amount.setVisibility(View.GONE);

                                lay_gram_lay.setVisibility(View.VISIBLE);


                                if (PaperList.get(position).getSource().equals("Gold")) {
                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);
                                    if (PaperList.get(position).getCustomeValume().equals("0")) {

                                        tv_amoy.setVisibility(View.GONE);
                                        tv_amoy22.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_amoy.setVisibility(View.VISIBLE);
                                        tv_amoy22.setVisibility(View.GONE);
                                    }

                                } else {
                                    manual_quty_layout.setVisibility(View.GONE);
                                    or_lable.setVisibility(View.GONE);
                                }

                                lay_gram_lay.setVisibility(View.GONE);
//                                if(PaperList.get(position).getIsSymbolHedge().equals("True")){
//                                    lay_gram_lay.setVisibility(View.VISIBLE);
//                                    manual_quty_layout.setVisibility(View.VISIBLE);
//                                    or_lable.setVisibility(View.VISIBLE);
//                                }else {
//                                    lay_gram_lay.setVisibility(View.VISIBLE);
//                                    manual_quty_layout.setVisibility(View.VISIBLE);
//                                    or_lable.setVisibility(View.VISIBLE);
//                                }
                                if (PaperList.get(position).getCustomeValume().equals("0")) {

                                    manual_quty_layout.setVisibility(View.GONE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.GONE);
                                } else {

                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);

                                }
                            }
                        });


                        buy_in_gram.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                buy_in_gram.setTextColor(Color.WHITE);
                                buy_in_gram.setBackgroundResource(R.drawable.bi);

                                tv_amoy.setBackgroundResource(R.drawable.shape_null);
                                tv_amoy.setTextColor(Color.BLACK);

                                lay_quintity.setVisibility(View.VISIBLE);
                                lay_staic_qut.setVisibility(View.VISIBLE);


                                lay_amount.setVisibility(View.GONE);

                                WhatClick = "gram";

                                tv_market.setTextColor(Color.WHITE);
                                tv_limit.setTextColor(Color.BLACK);
                                tv_market.setBackgroundResource(R.drawable.bi);
                                tv_limit.setBackgroundResource(R.drawable.shape_null);

                                lay_price.setVisibility(View.GONE);

                                buy_now.setText("BUY");
                                buy_now.setBackgroundResource(R.drawable.green);
                                buy_now.setMaxWidth(135);

                                sell_now.setVisibility(View.VISIBLE);
                                sell_now.setText("SELL");
                                sell_now.setBackgroundResource(R.drawable.redbutton);


                                et_amount_test.setText("");
                                tv_parice.setText("");

                                if (PaperList.get(position).getCustomeValume().equals("0")) {

                                    manual_quty_layout.setVisibility(View.GONE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.GONE);

                                } else {

                                    manual_quty_layout.setVisibility(View.VISIBLE);
                                    lay_quintity.setVisibility(View.VISIBLE);
                                    or_lable.setVisibility(View.VISIBLE);

                                }
                            }
                        });

                        tv_amoy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                tv_amoy.setTextColor(Color.WHITE);
                                tv_amoy.setBackgroundResource(R.drawable.bi);

                                buy_in_gram.setTextColor(Color.BLACK);
                                buy_in_gram.setBackgroundResource(R.drawable.shape_null);
                                lay_staic_qut.setVisibility(View.GONE);

                                lay_amount.setVisibility(View.VISIBLE);
                                WhatClick = "amount";
                                lay_quintity.setVisibility(View.GONE);

                                tv_market.setTextColor(Color.WHITE);
                                tv_limit.setTextColor(Color.BLACK);
                                tv_market.setBackgroundResource(R.drawable.bi);
                                tv_limit.setBackgroundResource(R.drawable.shape_null);

                                et_amount_test.setText("");

                                buy_now.setText("Get Details");
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                                params.weight = 1.0f;

//                                buy_now.setLayoutParams(params);
                                buy_now.setBackgroundResource(R.drawable.btn1);


                                sell_now.setVisibility(View.GONE);


//                                sell_now.setText("Get Details");
//                                sell_now.setBackgroundResource(R.drawable.btn1);


                                lay_price.setVisibility(View.GONE);


//                                if(PaperList.get(position).getCustomVolume().equals("0")){
//
//                                    manual_quty_layout.setVisibility(View.GONE);
//                                    lay_quintity.setVisibility(View.VISIBLE);
//
//                                }else {
//
//                                    manual_quty_layout.setVisibility(View.VISIBLE);
//                                    lay_quintity.setVisibility(View.GONE);
//
//                                }

                            }
                        });

                        for (int i = 0; i < Constants.SellPremiumIDDDD.size(); i++) {
                            String IdSymbol = "" + Constants.SellPremiumIDDDD.get(i);

                            if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                                min = Integer.parseInt("" + Constants.OneClick.get(i));
                                max = Integer.parseInt("" + Constants.InTotal.get(i));
//                                step = Integer.parseInt("" + Constants.Step.get(i));
                                step = Float.valueOf(Constants.Step.get(i));


//                                step=Double.parseDouble(Constants.Step.get(i)+"");


                                min1 = Integer.parseInt("" + Constants.OneClick.get(i));
                                max1 = Integer.parseInt("" + Constants.InTotal.get(i));
                            } else {

                            }
                        }
                        Log.e("min", "" + min);
                        Log.e("max1", "" + max1);


                        Log.e("Click", "Click");


                       /* Log.e("min1",""+min1);
                        Log.e("position",""+position);
                        Log.e("max1",""+max1);
                        Log.e("postis",""+Constants.OneClick.get(position));
                        Log.e("postis",""+Constants.OneClick.get(position));
                        Log.e("minnnnis",""+min);
*/
                        name = PaperList.get(position).getSource();
                        if (ValueIs.equals("Y")) {
                            if (name.equals("Gold")) {

                                if (min == 0 && max == 0) {
                                    tv_selection.setText("Select Quantity");
                                    ValueIs = "N";
                                    buy_in_gram.setText("Buy / Sell in GM");

                                } else {
                                    if (step % 1 != 0) {
                                        tv_selection.setText((double) min + " " + "Gms");
                                        System.out.print("Decimal");
                                    } else {
                                        tv_selection.setText(min + " " + "Gms");
                                        System.out.print("Integer");
                                    }
//                                    tv_selection.setText(min + " " + "Gms");
                                    ValueIs = "N";
                                    buy_in_gram.setText("Buy / Sell in GM");
                                }


                            } else {
                                if (min == 0 && max == 0) {
                                    tv_selection.setText("Select Quantity");
                                    ValueIs = "N";
                                    buy_in_gram.setText("Buy in KG");

                                } else {
                                    if (step % 1 != 0) {
                                        tv_selection.setText((double) min + " " + "KG");
                                        System.out.print("Decimal");
                                    } else {
                                        tv_selection.setText(min + " " + "KG");
                                        System.out.print("Integer");
                                    }
//                                    tv_selection.setText(min + " " + activity.getResources().getString(R.string.KG));
                                    ValueIs = "N";
                                    buy_in_gram.setText("Buy in KG");

                                }

                            }

                        } else {
                            Log.e("valueis", "" + ValueIs);

                        }


                        SymbolIdMain = PaperList.get(position).getSymbolId();


                        try {
                            try {
                                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                                try {
//                                    mySSLContext.init(null, trustAllCerts, null);
//                                    IO.setDefaultSSLContext(mySSLContext);
//
//                                    IO.Options opts = new IO.Options();
//
//                                    opts.reconnection = true;
//                                    opts.reconnectionDelay = 1000;
//                                    opts.timeout = 50000;
//
//                                    opts.transports = new String[] { WebSocket.NAME };
//                                    mSocket10008 = IO.socket(URL, opts);
                                    mSocket10008 = io.socket.client.IO.socket(Constants.SocketUrl);
                                    mSocket10008.connect();


                                    mSocket10008.emit("room", Constants.prjName).on("message", new io.socket.client.Socket.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            String GetLiverate = "" + args[0];
                                            Log.e("GetLiverate", GetLiverate);

                                            JSONObject result = null;
                                            try {
                                                result = new JSONObject("" + args[0]);
                                                Log.e("result", "" + result);
                                                final String Rate = result.getString("Rate");
                                                //Log.e("Rate===============",""+Rate);

                                                activity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        JSONArray resultArray = null;
                                                        try {
                                                            resultArray = new JSONArray(Rate);
                                                            for (int i = 0; i < resultArray.length(); i++) {
                                                                JSONObject p = (JSONObject) resultArray.get(i);

                                                                String SymbolId = p.getString("SymbolId");

                                                                if (SymbolId.equals(SymbolIdMain)) {
                                                                    int Ask = p.getInt("Ask");
                                                                    int Bid = p.getInt("Bid");
                                                                    //Log.e("jjjdkjjkdjff",""+PaperList.get(position).getIsSymbolHedge());


                                                           /* if(oldvalue.equals(PaperList.get(position).getIsSymbolHedge())){

                                                            }else {
                                                                dialog.dismiss();
                                                            }*/


                                                                    name = p.getString("Source");
                                                                    Log.e("nameSorce", name);

                                                                    //final String SecurityMin = p.getString("SecurityMin");
                                                                    //final String SecurityMax = p.getString("SecurityMax");


                                                                    if (PaperList.get(position).getSource().equals("Gold")) {

                                                                        for (int j = 0; j < Constants.SellPremiumIDDDD.size(); j++) {
                                                                            String IdSymbol = "" + Constants.SellPremiumIDDDD.get(j);
                                                                            if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                                                                                int SellPremium = Constants.SellPremium.get(j);
                                                                                Ask = Constants.GoldSellPremium + SellPremium + Ask;

                                                                            } else {
                                                                                // Ask= Constants.GoldSellPremium+Ask;
                                                                            }
                                                                        }


                                                                    } else {

                                                                        for (int j = 0; j < Constants.SellPremiumIDDDD.size(); j++) {
                                                                            String IdSymbol = "" + Constants.SellPremiumIDDDD.get(j);
                                                                            if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                                                                                int SellPremium = Constants.SellPremium.get(j);
                                                                                Ask = Constants.SilverSellPremium + SellPremium + Ask;

                                                                            } else {
                                                                                //Ask= Constants.SilverSellPremium+Ask;
                                                                            }
                                                                        }


                                                                    }

                                                                    if (PaperList.get(position).getSource().equals("Gold")) {

                                                                        for (int j = 0; j < Constants.SellPremiumIDDDD.size(); j++) {
                                                                            String IdSymbol = "" + Constants.SellPremiumIDDDD.get(j);
                                                                            if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                                                                                int SellPremium = Constants.BuyPremium.get(j);
                                                                                Bid = Constants.GoldBuyPremium + SellPremium + Bid;

                                                                            } else {
                                                                                // Ask= Constants.GoldSellPremium+Ask;
                                                                            }
                                                                        }


                                                                    } else {

                                                                        for (int j = 0; j < Constants.SellPremiumIDDDD.size(); j++) {
                                                                            String IdSymbol = "" + Constants.SellPremiumIDDDD.get(j);
                                                                            if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
                                                                                int SellPremium = Constants.BuyPremium.get(j);
                                                                                Bid = Constants.SilverSellPremium + SellPremium + Bid;

                                                                            } else {
                                                                                //Ask= Constants.SilverSellPremium+Ask;
                                                                            }
                                                                        }


                                                                    }


                                                                    Log.e("max", "" + max);

                                                                    String Asknew = String.valueOf(Ask);
                                                                    String Bidnew = String.valueOf(Bid);

                                                                    if (Asknew.equalsIgnoreCase("--")) {

                                                                        tv_amount.setBackgroundResource(0);
                                                                        tv_amount.setText(Bidnew);
                                                                        tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));

                                                                        tv_sellamount.setBackgroundResource(0);
                                                                        tv_sellamount.setText(Asknew);
                                                                        tv_sellamount.setTextColor(ContextCompat.getColor(activity, R.color.black));


                                                                        tv_bit_4.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                        tv_bit_5.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                    } else {
                                                                        if (!tv_amount.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(tv_amount.getText()) && !tv_sellamount.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(tv_sellamount.getText())) {

                                                                            if (Double.parseDouble(Bidnew) != Double.parseDouble(tv_amount.getText().toString()) && Double.parseDouble(Asknew) != Double.parseDouble(tv_sellamount.getText().toString())) {

                                                                                //tv_amount.setBackgroundResource(Double.parseDouble(Asknew) < Double.parseDouble(tv_amount.getText().toString()) ? R.drawable.liverate_red : R.drawable.liverate_green);

                                                                                if (Double.parseDouble(Bidnew) < Double.parseDouble(tv_amount.getText().toString()) && Double.parseDouble(Asknew) < Double.parseDouble(tv_sellamount.getText().toString())) {
                                                                                    tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                                    tv_sellamount.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                                    // tv_bit_4.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                                    //tv_bit_5.setTextColor(ContextCompat.getColor(activity, R.color.red));


                                                                                } else {
                                                                                    tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                                    tv_sellamount.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                                    //tv_bit_4.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                                    //tv_bit_5.setTextColor(ContextCompat.getColor(activity, R.color.green));

                                                                                }
                                                                                tv_amount.setText(Bidnew);
                                                                                tv_sellamount.setText(Asknew);
                                                                            } else {
                                                                                tv_amount.setText(Bidnew);
                                                                                tv_sellamount.setText(Asknew);
                                                                                tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                                tv_sellamount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                                tv_bit_4.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                                tv_bit_5.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                                tv_amount.setBackgroundResource(0);
                                                                                tv_sellamount.setBackgroundResource(0);
                                                                            }
                                                                        } else {
                                                                            tv_amount.setText(Bidnew);
                                                                            tv_sellamount.setText(Asknew);
                                                                            tv_amount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                            tv_sellamount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                                                                            tv_bit_4.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                                                            tv_bit_5.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                                                            tv_amount.setBackgroundResource(0);
                                                                            tv_sellamount.setBackgroundResource(0);
                                                                        }
                                                                    }

                                                                    tv_selection.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                            Log.e("Clickkkkkkk", "OK");

                                                                            if (min == 0 && max == 0) {
                                                                                Log.e("Clickkkkkkk", "Zero");


                                                                            } else {
                                                                                if (popupWindowGram != null && popupWindowGram.isShowing()) {
                                                                                    popupWindowGram.dismiss();
                                                                                    Log.e("Clickkkkkkk", "Null");

                                                                                } else {


//                                                                                    for (int i = 0; i < Constants.SellPremiumIDDDD.size(); i++) {
//                                                                                        String IdSymbol = "" + Constants.SellPremiumIDDDD.get(i);
//
//                                                                                        if (IdSymbol.equals(PaperList.get(position).getSymbolId())) {
//                                                                                            min = Integer.parseInt("" + Constants.OneClick.get(i));
//                                                                                            max = Integer.parseInt("" + Constants.InTotal.get(i));
//                                                                                            step = Integer.parseInt("" + Constants.Step.get(i));
//                                                                                            min1 = Integer.parseInt("" + Constants.OneClick.get(i));
//                                                                                            max1 = Integer.parseInt("" + Constants.InTotal.get(i));
//
//                                                                                        } else {
//
//                                                                                        }
//                                                                                    }


                                                                                    getListOFGram();

                                                                                    popupWindowGram = popupWindowGram();
                                                                                    popupWindowGram.setFocusable(true);
                                                                                    popupWindowGram.setWidth(tv_selection.getWidth());
                                                                                    popupWindowGram.showAsDropDown(tv_selection);


                                                                                }
                                                                            }


                                                                        }
                                                                    });


                                                                } else {

                                                                }


                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    });


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        buy_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LoginToken = Common.getPreferenceString(activity, "TokenLogin", "");
                                Log.e("LoginToken", LoginToken);
                                Log.e("WhatClick", WhatClick);


                                Double chekAmount = null;
                                if (WhatClick.equals("amount")) {
                                    if (et_amount_test.getText().toString().trim().length() == 0) {

                                    } else {
                                        String Amount = et_amount_test.getText().toString();
                                        chekAmount = Double.valueOf(Amount);

                                    }
                                } else {

                                }

                                if (WhatClick.equals("amount") && et_amount_test.getText().toString().trim().length() == 0) {
                                    et_amount_test.setError("Amount");
                                    et_amount_test.requestFocus();
                                } else if (WhatClick.equals("limit") && tv_parice.getText().toString().trim().length() == 0) {
                                    tv_parice.setError("Rate");
                                    tv_parice.requestFocus();
                                } else if (WhatClick.equals("amount") && Constants.totalBalance < chekAmount) {
                                    et_amount_test.setError("Entered amount is greater than total balance, please contact admin");
                                    et_amount_test.requestFocus();
                                } else {
                                    if (buy_now.getText().toString().trim().equals("Get Details")) {

                                        if (et_amount_test.getText().toString().trim().length() == 0) {

                                        } else {
                                            //Amount= Double.valueOf(Integer.parseInt(et_amount_test.getText().toString().trim()));
                                            Amount = Double.valueOf(et_amount_test.getText().toString().trim());
                                            Comment = "Amount";
                                            LimitPrice = "0";

                                            final Double AskRate = Double.valueOf(tv_amount.getText().toString().trim());
                                            Double TotalGramSymbol = Double.valueOf(PaperList.get(position).getGrams());
                                            Double Outy1 = Amount * TotalGramSymbol;

                                            ///chnege 12/01/2022
                                          /*  Double Outy11=null;
                                            if(Comment.equals("Amount")){
                                                Outy11=Outy1/Amount;

                                            }else {
                                                Outy11=Outy1/AskRate;
                                            }*/
                                            //fdfdfdfdf

                                            Double Outy11 = Outy1 / AskRate;


                                            String SorceIs = "";
                                            if (name.equals("Gold")) {
                                                SorceIs = "gm";
                                            } else {
                                                SorceIs = "kg";
                                                // Outy11=Outy11/1000;
                                                // Outy11=Outy11;


                                            }
                                            final DecimalFormat df = new DecimalFormat("0.000");
                                            DecimalFormat df2 = new DecimalFormat("0.00");

                                            final Double finalOuty1 = Outy11;

                                            //Log.e("WhatClick",""+df.format(finalOuty1));
                                            new AlertDialog.Builder(activity).setTitle(PaperList.get(position).getSymbol()).setMessage("Quantity: " + df.format(Outy11) + " " + SorceIs + "\n" + "Amount: " + df2.format(Amount)).setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    ObjVariable = "{" + "\"SymbolId\":\"" + PaperList.get(position).getSymbolId()
                                                            + "\"," + "\"Amount\":\"" + Amount
                                                            + "\"," + "\"Quantity\":\"" + df.format(finalOuty1)
                                                            + "\"," + "\"TradeFrom\":\"android\","
                                                            + "\"TradeType\":\"" + TradeType
                                                            + "\"," + "\"DeviceToken\":\"" + "" + "\","
                                                            + "\"BuyLimitPrice\":\"" + LimitPrice + "\","
                                                            + "\"SellLimitPrice\":\"" + "0" + "\","
                                                            + "\"Comment\":\"" + et_comment.getText().toString()
                                                            + "\"," + "\"Token\":\"" + LoginToken + "\"" +

                                                            "}";

                                                    Log.e("TreadeObject", ObjVariable);
                                                    dialog.dismiss();
                                                    SorceisEew = name;
                                                    new InsertOpenOrderDetailWithRegID().execute();
                                                }

                                            }).setNegativeButton("Cancel", null).show();
                                        }

                                    } else {

//                                        if(tv_manual_quntite.getText().toString().trim().length() == 0){
//
//                                            if(tv_selection.getText().toString().equals("Select Quantity")){
//
//                                            }else {
//                                                String[] splited = tv_selection.getText().toString().split(" ");
//                                                min= Integer.parseInt(splited[0]);
//                                            }
//
//
//                                        }else {
//                                            min= Integer.parseInt(tv_manual_quntite.getText().toString().trim());
//                                        }
                                        if (PaperList.get(position).getCustomeValume().equals("0")) {
                                            if (tv_selection.getText().toString().equals("Select Quantity")) {
                                                return;
                                            } else {
                                                String[] splited = tv_selection.getText().toString().split(" ");
                                                min = Integer.parseInt(splited[0]);
                                            }


                                        } else {

//                                            if(tv_manual_quntite.getText().toString().trim().length() == 0) {
//                                                return;
//                                            } else  {
//                                                min= Integer.parseInt(tv_manual_quntite.getText().toString().trim());
//                                            }

                                            if (tv_manual_quntite.getText().toString().trim().length() == 0) {

                                                if (tv_selection.getText().toString().equals("Select Quantity")) {
                                                    return;
                                                } else {
                                                    String[] splited = tv_selection.getText().toString().split(" ");
                                                    min = Integer.parseInt(splited[0]);
                                                }


                                            } else {
                                                min = Integer.parseInt(tv_manual_quntite.getText().toString().trim());
                                            }

                                        }


                                        Comment = "";


                                        if (TradeType.equals("3")) {
                                            Amount = 0.0;
                                            LimitPrice = tv_parice.getText().toString().trim();
                                        }

                                        ObjVariable = "{" + "\"SymbolId\":\"" + PaperList.get(position).getSymbolId()
                                                + "\"," + "\"Amount\":\"" + Amount
                                                + "\"," + "\"Quantity\":\"" + min + "\"," + "\"TradeFrom\":\"android\"," + "\"TradeType\":\"" + TradeType + "\"," + "\"DeviceToken\":\"" + "" + "\"," + "\"BuyLimitPrice\":\"" + LimitPrice + "\"," + "\"SellLimitPrice\":\"" + "0" + "\"," + "\"Comment\":\"" + et_comment.getText().toString() + "\"," + "\"Token\":\"" + LoginToken + "\"" +

                                                "}";

                                        Log.e("TreadeObject", ObjVariable);
                                        Log.e("mindddddddddd", "" + min);
                                        dialog.dismiss();
                                        SorceisEew = name;

                                        new InsertOpenOrderDetailWithRegID().execute();

                                    }


                                }


                            }
                        });

                        sell_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LoginToken = Common.getPreferenceString(activity, "TokenLogin", "");
                                Log.e("LoginToken", LoginToken);
                                Log.e("WhatClick", WhatClick);


                                Double chekAmount = null;
                                if (WhatClick.equals("amount")) {
                                    if (et_amount_test.getText().toString().trim().length() == 0) {

                                    } else {
                                        String Amount = et_amount_test.getText().toString();
                                        chekAmount = Double.valueOf(Amount);

                                    }
                                } else {

                                }

                                if (WhatClick.equals("amount") && et_amount_test.getText().toString().trim().length() == 0) {
                                    et_amount_test.setError("Amount");
                                    et_amount_test.requestFocus();
                                } else if (WhatClick.equals("limit") && tv_parice.getText().toString().trim().length() == 0) {
                                    tv_parice.setError("Rate");
                                    tv_parice.requestFocus();
                                } else if (WhatClick.equals("amount") && Constants.totalBalance < chekAmount) {
                                    et_amount_test.setError("Entered amount is greater than total balance, please contact admin");
                                    et_amount_test.requestFocus();
                                } else {
                                    if (sell_now.getText().toString().trim().equals("Get Details")) {

                                        if (et_amount_test.getText().toString().trim().length() == 0) {

                                        } else {
                                            //Amount= Double.valueOf(Integer.parseInt(et_amount_test.getText().toString().trim()));
                                            Amount = Double.valueOf(et_amount_test.getText().toString().trim());
                                            Comment = "Amount";
                                            LimitPrice = "0";

                                            final Double AskRate = Double.valueOf(tv_amount.getText().toString().trim());
                                            Double TotalGramSymbol = Double.valueOf(PaperList.get(position).getGrams());
                                            Double Outy1 = Amount * TotalGramSymbol;

                                            ///chnege 12/01/2022
                                          /*  Double Outy11=null;
                                            if(Comment.equals("Amount")){
                                                Outy11=Outy1/Amount;

                                            }else {
                                                Outy11=Outy1/AskRate;
                                            }*/
                                            //fdfdfdfdf

                                            Double Outy11 = Outy1 / AskRate;


                                            String SorceIs = "";
                                            if (name.equals("Gold")) {
                                                SorceIs = "gm";
                                            } else {
                                                SorceIs = "kg";
                                                // Outy11=Outy11/1000;
                                                // Outy11=Outy11;


                                            }
                                            final DecimalFormat df = new DecimalFormat("0.000");
                                            DecimalFormat df2 = new DecimalFormat("0.00");

                                            final Double finalOuty1 = Outy11;

                                            //Log.e("WhatClick",""+df.format(finalOuty1));
                                            new AlertDialog.Builder(activity).setTitle(PaperList.get(position).getSymbol()).setMessage("Quantity: " + df.format(Outy11) + " " + SorceIs + "\n" + "Amount: " + df2.format(Amount)).setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    ObjVariable = "{" + "\"SymbolId\":\"" + PaperList.get(position).getSymbolId() + "\"," + "\"Amount\":\"" + Amount + "\"," + "\"Quantity\":\"" + df.format(finalOuty1) + "\"," + "\"TradeFrom\":\"android\"," + "\"TradeType\":\"" + SellTradeType + "\"," + "\"DeviceToken\":\"" + "" + "\"," + "\"BuyLimitPrice\":\"" + LimitPrice + "\"," + "\"SellLimitPrice\":\"" + "0" + "\"," + "\"Comment\":\"" + et_comment.getText().toString() + "\"," + "\"Token\":\"" + LoginToken + "\"" +

                                                            "}";

                                                    Log.e("TreadeObject", ObjVariable);
                                                    dialog.dismiss();
                                                    SorceisEew = name;
                                                    new InsertOpenOrderDetailWithRegID().execute();
                                                }

                                            }).setNegativeButton("Cancel", null).show();
                                        }

                                    } else {

//                                        if(tv_manual_quntite.getText().toString().trim().length() == 0){
//
//                                            if(tv_selection.getText().toString().equals("Select Quantity")){
//
//                                            }else {
//                                                String[] splited = tv_selection.getText().toString().split(" ");
//                                                min= Integer.parseInt(splited[0]);
//                                            }
//
//
//                                        }else {
//                                            min= Integer.parseInt(tv_manual_quntite.getText().toString().trim());
//                                        }
                                        if (PaperList.get(position).getCustomeValume().equals("0")) {
                                            if (tv_selection.getText().toString().equals("Select Quantity")) {
                                                return;
                                            } else {
                                                String[] splited = tv_selection.getText().toString().split(" ");
                                                min = Integer.parseInt(splited[0]);
                                            }


                                        } else {

//                                            if(tv_manual_quntite.getText().toString().trim().length() == 0) {
//                                                return;
//                                            } else  {
//                                                min= Integer.parseInt(tv_manual_quntite.getText().toString().trim());
//                                            }

                                            if (tv_manual_quntite.getText().toString().trim().length() == 0) {

                                                if (tv_selection.getText().toString().equals("Select Quantity")) {
                                                    return;
                                                } else {
                                                    String[] splited = tv_selection.getText().toString().split(" ");
                                                    min = Integer.parseInt(splited[0]);
                                                }


                                            } else {
                                                min = Integer.parseInt(tv_manual_quntite.getText().toString().trim());
                                            }


                                        }


                                        Comment = "";


                                        if (SellTradeType.equals("4")) {
                                            Amount = 0.0;
                                            LimitPrice = tv_parice.getText().toString().trim();
                                        }

                                        ObjVariable = "{" + "\"SymbolId\":\"" + PaperList.get(position).getSymbolId() + "\"," + "\"Amount\":\"" + Amount + "\"," + "\"Quantity\":\"" + min + "\"," + "\"TradeFrom\":\"android\"," + "\"TradeType\":\"" + SellTradeType + "\"," + "\"DeviceToken\":\"" + "" + "\"," + "\"BuyLimitPrice\":\"" + "0" + "\"," + "\"SellLimitPrice\":\"" + LimitPrice + "\"," + "\"Comment\":\"" + et_comment.getText().toString() + "\"," + "\"Token\":\"" + LoginToken + "\"" +

                                                "}";

                                        Log.e("TreadeObject", ObjVariable);
                                        Log.e("mindddddddddd", "" + min);
                                        dialog.dismiss();
                                        SorceisEew = name;

                                        new InsertOpenOrderDetailWithRegID().execute();

                                    }


                                }

                            }
                        });


                        FrameLayout disable_layout = dialog.findViewById(R.id.disable_layout);
                        disable_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideKeyBoard(activity, view);
                                //dialog.dismiss();
                            }
                        });

                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                selectedItem = 0;
                                dialog.dismiss();
                            }
                        });


                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.CENTER);
                        dialog.setCanceledOnTouchOutside(false);


                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


//                if (!isclick.get(position)) {
//
//                    for (int i = 0; i < 2; i++) {
//                        isclick.add(i, false);
//
//
//                    }
//
//                    isclick.set(position, true);
//                    notifyDataSetChanged();
//
//                } else {
//
//                    for (int i = 0; i < 2; i++) {
//                        isclick.add(i, false);
//
//
//                    }
//
//                    isclick.set(position, false);
//                    notifyDataSetChanged();
//                }


            }
        });


//        if (!isclick.get(position)) {
//            holder.lay_on_off.setVisibility(View.GONE);
//
//
//        } else {
//            holder.lay_on_off.setVisibility(View.VISIBLE);
//
//        }


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


        LinearLayout lay_buy_now;
        LinearLayout lay_on_off;

        TextView market_price;
        TextView future_rate;
        TextView buy_in_amount;
        TextView buy_in_gram;

        TextView tv_name;
        TextView ask_rate;
        TextView tv_by_now;
        LinearLayout lay_item_live;
        TextView tv_gram;

        ImageView iv_menu;

        TextView tv_hight;
        TextView tv_low;

        public MyViewHolder(View view) {
            super(view);

            lay_buy_now = (LinearLayout) view.findViewById(R.id.lay_buy_now);
            tv_hight = view.findViewById(R.id.tv_hight);
//            lay_on_off = (LinearLayout) view.findViewById(R.id.lay_on_off);


//
//            market_price = (TextView) view.findViewById(R.id.market_price);
//            future_rate = (TextView) view.findViewById(R.id.future_rate);
//            buy_in_amount = (TextView) view.findViewById(R.id.buy_in_amount);
            buy_in_gram = (TextView) view.findViewById(R.id.buy_in_gram);
            tv_low = view.findViewById(R.id.tv_low);

            lay_item_live = view.findViewById(R.id.lay_item_live);
            iv_menu = view.findViewById(R.id.iv_menu);

            tv_name = view.findViewById(R.id.tv_name);
            ask_rate = view.findViewById(R.id.ask_rate);
            tv_by_now = view.findViewById(R.id.tv_by_now);
            tv_gram = view.findViewById(R.id.tv_gram);
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
    int selectedItem = 0;

    public PopupWindow popupWindowGram() {

        if (popupWindowGram == null) {
            popupWindowGram = new PopupWindow(activity);
        }

        try {
            ListView listViewGram = new ListView(activity);
            listViewGram.setDividerHeight(1);


            listViewGram.setBackgroundColor(activity.getResources().getColor(R.color.white));

            final List<String> gramList = getListOFGram();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, gramList) {
//                @Override
//                public View getDropDownView(int position, View convertView, ViewGroup parent)
//                {
//                    TextView v = new TextView(activity);
//                    v = (TextView) super.getDropDownView(position, null, parent);
//                    v.setGravity(Gravity.CENTER);
//                    v.setPadding(20, 20, 20, 20);
//                    // If this is the selected item position
//                    if (position == selectedItem) {
//                        v.setBackgroundColor(activity.getResources().getColor(R.color.gold));
////                        v.setTextColor(Color.BLUE);
////                        v.setBackgroundColor(Color.BLUE);
//                    }
//                    else {
//                        // for other views
//                        v.setBackgroundColor(activity.getResources().getColor(R.color.black));
////                        v.setBackgroundColor(Color.BLACK);
//
//                    }
//                    return v;
//                }


                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    TextView listItem = new TextView(activity);
                    if (position == selectedItem) {
                        listItem.setTextColor(activity.getResources().getColor(R.color.gold));
                    } else {
                        // for other views
                        listItem.setTextColor(activity.getResources().getColor(R.color.black));


                    }
                    listItem.setText(getItem(position));
                    listItem.setGravity(Gravity.CENTER);
                    listItem.setPadding(20, 20, 20, 20);
//                    listItem.setTextColor(Color.BLACK);

                    return listItem;
                }
            };


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listViewGram.setAdapter(adapter);

            listViewGram.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selectedItem = position;
//                    selectedItem=0;
                    tv_selection.setText(gramList.get(position));

//                    String[] splited = gramList.get(position).split(" ");
//                    min = Integer.parseInt(splited[0]);
                    Log.e("Lissttt", "" + min);

//                        /*When minimum security apply*/
//                        int minimumValue = Integer.parseInt(sec.getMinimumLots());
//                        if (minimumValue > 0) {
//                            symbolQuantity = position + minimumValue;
//                        } else {
//                            symbolQuantity = position + 1;
//                        }

                    popupWindowGram.dismiss();
//                    try {
//                        tv_selection.setText(gramList.get(position));
//
//                        String[] splited = gramList.get(position).split(" ");
//                        min = Integer.parseInt(splited[0]);
//
//                        Log.e("Lissttt", "" + min);
//
//
////                        /*When minimum security apply*/
////                        int minimumValue = Integer.parseInt(sec.getMinimumLots());
////                        if (minimumValue > 0) {
////                            symbolQuantity = position + minimumValue;
////                        } else {
////                            symbolQuantity = position + 1;
////                        }
//
//                        popupWindowGram.dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            });
            popupWindowGram.dismiss();
            popupWindowGram.setContentView(listViewGram);

            if (gramList.size() < 7) {
                popupWindowGram.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            } else {
                popupWindowGram.setHeight(700);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return popupWindowGram;
    }

    private List<String> getListOFGram() {
        List<String> gramList = new ArrayList<>();

        String Lable = "";
//        int totalvalueis = 0;
//        if (step != 0) {
//            totalvalueis = max / step;
//        } else {
//            totalvalueis = max / min;
//        }
//        totalvalueis = max / min;


        if (step % 1 != 0) {
            double todoay = min;
            double iiiii = 0;
            if (step <= 0) {
                step = min;
            }
            for (double i = min; i <= max; ) {

                if (iiiii == 0) {
                    String gram = String.valueOf(decimalFormat(min));
                    if (name.equals("Gold")) {
                        Lable = "Gms";
                    } else {
                        Lable = activity.getResources().getString(R.string.KG);
                    }

                    gramList.add(gram + " " + Lable);

                    iiiii++;
                } else {

                    if (step != 0) {
//                    totalvalueis = step;
                        todoay = todoay + step;
                    } else {
                        todoay = todoay + min;
                    }

                    String gram = String.valueOf(decimalFormat(todoay));

                    if (name.equals("Gold")) {
                        Lable = "Gms";
                    } else {
                        Lable = activity.getResources().getString(R.string.KG);

                    }
                    Log.e("gramList", "" + gramList);


                    gramList.add(gram + " " + Lable);

                }

                i = i + step;
            }
            System.out.print("Decimal");
        } else {
            int todoay = min;
            int iiiii = 0;
            int myInt = (int) step;
            if (myInt <= 0) {
                myInt = min;
            }
            for (int i = min; i <= max; ) {

                if (iiiii == 0) {
                    String gram = String.valueOf(min);
                    if (name.equals("Gold")) {
                        Lable = "Gms";
                    } else {
                        Lable = activity.getResources().getString(R.string.KG);
                    }

                    gramList.add(gram + " " + Lable);

                    iiiii++;
                } else {

                    if (myInt != 0) {
//                    totalvalueis = step;
                        todoay = todoay + myInt;
                    } else {
                        todoay = todoay + min;
                    }

                    String gram = String.valueOf(todoay);

                    if (name.equals("Gold")) {
                        Lable = "Gms";
                    } else {
                        Lable = activity.getResources().getString(R.string.KG);

                    }
                    Log.e("gramList", "" + gramList);


                    gramList.add(gram + " " + Lable);

                }

                i = i + myInt;
            }
            System.out.print("Integer");
        }
//        if (step <= 0) {
//            step = min;
//        }
//        for (double i = min; i <= max; ) {
//
//            if (iiiii == 0) {
//                String gram = String.valueOf(decimalFormat(min));
//                if (name.equals("Gold")) {
//                    Lable = "Gms";
//                } else {
//                    Lable = activity.getResources().getString(R.string.KG);
//                }
//
//                gramList.add(gram + " " + Lable);
//
//                iiiii++;
//            } else {
//
//                if (step != 0) {
////                    totalvalueis = step;
//                    todoay = todoay + step;
//                } else {
//                    todoay = todoay + min;
//                }
//
//                String gram = String.valueOf(decimalFormat(todoay));
//
//                if (name.equals("Gold")) {
//                    Lable = "Gms";
//                } else {
//                    Lable = activity.getResources().getString(R.string.KG);
//
//                }
//                Log.e("gramList", "" + gramList);
//
//
//                gramList.add(gram + " " + Lable);
//
//            }
//
//            i = i + step;
//        }


        return gramList;
    }

//    private ArrayAdapter<String> gramAdapter(List<String> gramList) {
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, gramList) {
//
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                TextView listItem = new TextView(activity);
//                listItem.setText(getItem(position));
//                listItem.setGravity(Gravity.CENTER);
//                listItem.setPadding(20, 20, 20, 20);
//                listItem.setTextColor(Color.BLACK);
//
//                return listItem;
//            }
//        };
//
//        return adapter;
//    }

//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

    private class InsertOpenOrderDetailWithRegID extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", InsertOpenOrderDetailWithRegID);
            request.addProperty("ObjOrder", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, InsertOpenOrderDetailWithRegID);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("TradeResponce", s);

                dismissProgress();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TradeResponce", "" + s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnCode = json.getString("ReturnCode");
                            if (ReturnCode.equals("200")) {

                                String OpeOrder = json.getString("OpeOrder");
                                Log.e("diiid", OpeOrder);

                                if (OpeOrder.equals("")) {
                                    String ReturnMsg = json.getString("ReturnMsg");
                                    new android.app.AlertDialog.Builder(activity).setTitle("Vicky Jewellery Works")
                                            .setMessage(ReturnMsg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                        }
                                    }).show();
                                } else {
                                    JSONArray jArray = new JSONArray(OpeOrder);
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject json_data = jArray.getJSONObject(i);
//                                        Constants.DealNo=json_data.getString("msg");
                                        String msg = json_data.getString("msg");
                                        Constants.ProductionName = json_data.getString("SymbolName");
                                        Constants.Volume = json_data.getString("Volume");
                                        Constants.Prize = json_data.getString("Prize");
                                        Constants.P_L = json_data.getString("P_L");
                                        Constants.TradeType = json_data.getString("TradeType");

                                        String[] parts = msg.split("~~");
                                        String part1 = parts[0]; // 004
                                        String part2 = parts[1];
                                        Constants.DealNo = part2;
                                        String GaramFinal = "";
                                        float valueis = Float.parseFloat(Constants.Volume);
                                        Double ValueIS = Double.valueOf(Constants.Volume);
                                        Double Priceseis = Double.valueOf(Constants.Prize);
                                        Double TotalIs = ValueIS * Priceseis;

                                        ///chnege 12/01/2022

                                        if (SorceisEew.equals("Gold")) {
                                            DecimalFormat threevalue2 = new DecimalFormat("0.0");
                                            Double GaramFinal2 = TotalIs / valueis;
                                            GaramFinal = threevalue2.format(GaramFinal2);
                                        } else {
                                            DecimalFormat threevalue2 = new DecimalFormat("0.000");
                                            float valueis22 = Float.parseFloat(Constants.Volume);
                                            double sliver = valueis22 * 1000;
                                            Double GaramFinal2 = TotalIs / sliver;
                                            GaramFinal = threevalue2.format(GaramFinal2);
                                        }
                                        //end
                                        Constants.OrderRate = GaramFinal;

                                    }

                                    Log.e("ConstantsVolume", Constants.Volume);
                                    Log.e("ConstantsPrize", Constants.Prize);
                                    Log.e("ConstantsOrderRate", Constants.OrderRate);


                                    if (Constants.DealNo.equals("")) {

                                    } else {
                                        Constants.Messsage = json.getString("ReturnMsg");
                                        Constants.Sorces = name;
                                        Intent Orderconfirmationpage = new Intent(activity, Orderconfirmationpage.class);
                                        Orderconfirmationpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.startActivity(Orderconfirmationpage);
                                    }
                                }

                            } else {
                                String ReturnMsg = json.getString("ReturnMsg");
                                new android.app.AlertDialog.Builder(activity).setTitle("Vicky Jewellery Works").setMessage(ReturnMsg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                    }
                                }).show();


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
