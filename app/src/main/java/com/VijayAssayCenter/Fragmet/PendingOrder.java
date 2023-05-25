package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;
import static com.VijayAssayCenter.utils.Constants.DeleteCloseOrder;
import static com.VijayAssayCenter.utils.Constants.UpdateLimitByDeal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.ForgotPassword;
import com.VijayAssayCenter.activity.Login;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.activity.Orderconfirmationpage;
import com.VijayAssayCenter.model.OpenOrderModel;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PendingOrder extends Fragment {

    RecyclerView rv_cart;
    Category cartItems;
    AlertDialog dialogcommna;

    String regresponse="";
    String ObjVariable="";


    public PendingOrder() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_order, container, false);


        Log.e("tettetetetetetettetet", Constants.OpenOrder);
        if(Constants.OpenOrder.equals("")){

        }else {
            rv_cart=view.findViewById(R.id.rv_cart);
            cartItems = new Category(getActivity());
            RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
            rv_cart.setLayoutManager(homelayoutmanager);
            rv_cart.setItemAnimator(new DefaultItemAnimator());
            rv_cart.setAdapter(cartItems);

            cartItems.addAll((List<OpenOrderModel>) new Gson().fromJson(Constants.OpenOrder, new TypeToken<ArrayList<OpenOrderModel>>() {
            }.getType()));
        }



        return view;
    }


    public class Category extends RecyclerView.Adapter<Category.MyViewHolder> {

        private Activity activity;
        private List<OpenOrderModel> PaperList = new ArrayList<OpenOrderModel>();
        private LayoutInflater layoutInflater;
        private String product_id;
        Animation animFadein;
        private List<Boolean> isclick = new ArrayList<>();
        AsyncProgressDialog ad;

        AlertDialog abstudent;



        public Category(Activity activity) {
            this.activity = activity;
            layoutInflater = LayoutInflater.from(activity);
           /* for (int i = 0; i < 10; i++) {
             PaperList.add("");
           }*/
        }

        public void addAll(List<OpenOrderModel> bannerList) {
            this.PaperList = bannerList;

        for (int i = 0; i < bannerList.size(); i++) {
            isclick.add(i, false);

        }
            //notifyDataSetChanged();
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_order, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.lay_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isclick.get(position)) {
                        for (int i = 0; i < PaperList.size(); i++) {
                            isclick.add(i, false);


                        }

                        isclick.set(position, true);
                        notifyDataSetChanged();

                    } else {
                        for (int i = 0; i < PaperList.size(); i++) {
                            isclick.add(i, false);

                        }

                        isclick.set(position, false);
                        notifyDataSetChanged();
                    }
                }
            });


            if (!isclick.get(position)) {
                holder.close_layout.setVisibility(View.VISIBLE);


            } else {
                holder.close_layout.setVisibility(View.VISIBLE);

            }



            holder.tv_name.setText(PaperList.get(position).getSymbolName());



            String date=PaperList.get(position).getOpenTradeDateTime();
            Log.e("timeem",PaperList.get(position).getOpenTradeDateTime());
            SimpleDateFormat spf=new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            Date newDate= null;
            try {
                newDate = spf.parse(date);
                spf= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                date = spf.format(newDate);
                System.out.println(date);

                String[] separated = date.split(" ");
                String d=separated[0];
                String m=separated[1];
                String y=separated[2];
                String time=separated[3];

                String datenew=" "+d+" "+m+" "+y;

                holder.open_date.setText("Date :"+datenew+" / Time "+time);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            holder.tv_deal_no.setText("Order No: "+PaperList.get(position).getDealNo());

            holder.edit_image.setVisibility(View.VISIBLE);


            String formatted="";
            String Lable="";
            if(PaperList.get(position).getSource().trim().equals("Gold")){
                Lable="gm";
            }else {
                Lable="kg";

            }

            if(PaperList.get(position).getTradeType().trim().equals("1")||PaperList.get(position).getTradeType().trim().equals("3")||PaperList.get(position).getTradeType().trim().equals("5")){


                Double ValueIS= Double.valueOf(PaperList.get(position).getVolume());
                Double Priceseis= Double.valueOf(PaperList.get(position).getPrize());
                Double TotalIs=ValueIS*Priceseis;
                DecimalFormat df = new DecimalFormat("0");
                String SeeIS=df.format(TotalIs);

                DecimalFormat threevalue = new DecimalFormat("0.000");
                float valueis= Float.parseFloat(PaperList.get(position).getVolume());
                formatted = threevalue.format(valueis);
                Log.e("tessss",""+formatted);

                String GaramFinal="";
                if(PaperList.get(position).getSource().trim().equals("Gold")){
                    DecimalFormat threevalue2 = new DecimalFormat("0.0");
                    Double GaramFinal2= TotalIs/valueis;
                    GaramFinal = threevalue2.format(GaramFinal2);
                }else {
                    DecimalFormat threevalue2 = new DecimalFormat("0.000");
                    float valueis22= Float.parseFloat(PaperList.get(position).getVolume());
                    double sliver=valueis22*1000;
                    Double GaramFinal2= TotalIs/sliver;
                    GaramFinal = threevalue2.format(GaramFinal2);
                }

                if(PaperList.get(position).getTradeType().trim().equals("3")){

                    holder.buy_gream.setText("BuyLimit "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }else if(PaperList.get(position).getTradeType().trim().equals("5")){
                    holder.buy_gream.setText("BuyRequst "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }else {
                    holder.buy_gream.setText("Buy "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }

            }else {

                Double ValueIS= Double.valueOf(PaperList.get(position).getVolume());
                Double Priceseis= Double.valueOf(PaperList.get(position).getPrize());
                Double TotalIs=ValueIS*Priceseis;
                DecimalFormat df = new DecimalFormat("0");
                String SeeIS=df.format(TotalIs);

                DecimalFormat threevalue = new DecimalFormat("0.000");
                float valueis= Float.parseFloat(PaperList.get(position).getVolume());
                formatted = threevalue.format(valueis);
                Log.e("tessss",""+formatted);

                String GaramFinal="";
                if(PaperList.get(position).getSource().trim().equals("Gold")){
                    DecimalFormat threevalue2 = new DecimalFormat("0.0");
                    Double GaramFinal2= TotalIs/valueis;
                    GaramFinal = threevalue2.format(GaramFinal2);
                }else {
                    DecimalFormat threevalue2 = new DecimalFormat("0.000");
                    float valueis22= Float.parseFloat(PaperList.get(position).getVolume());
                    double sliver=valueis22*1000;
                    Double GaramFinal2= TotalIs/sliver;
                    GaramFinal = threevalue2.format(GaramFinal2);
                }

                if(PaperList.get(position).getTradeType().trim().equals("4")){
                    holder.buy_gream.setText("SellLimit "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }else if(PaperList.get(position).getTradeType().trim().equals("6")){
                    holder.buy_gream.setText("SellRequst "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }else {
                    holder.buy_gream.setText("Sell "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount "+SeeIS);

                }

            }






            holder.close_price.setVisibility(View.GONE);
            holder.close_price.setText("Close Price: "+PaperList.get(position).getClosePrice());

            // Log.e("Types",PaperList.get(position).getTradeType());
            //  Log.e("Types",PaperList.get(position).getTradeMode());
            //Log.e("Volume",PaperList.get(position).getVolume());


                if(PaperList.get(position).getTradeType().trim().equals("3")||PaperList.get(position).getTradeType().trim().equals("4")){
                    holder.lay_cart.setVisibility(View.VISIBLE);

                }else {
                    holder.lay_cart.setVisibility(View.GONE);

                }

            holder.edit_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    LayoutInflater li = LayoutInflater.from(getActivity());

                    final View confirmDialog = li.inflate(R.layout.pedding_dilog, null);

                   AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    TextView tv_symbol_name=confirmDialog.findViewById(R.id.tv_symbol_name);
                    TextView tv_deal_no=confirmDialog.findViewById(R.id.tv_deal_no);
                    TextView tv_quntity=confirmDialog.findViewById(R.id.tv_quntity);
                    TextView et_price=confirmDialog.findViewById(R.id.et_price);
                    tv_symbol_name.setText(PaperList.get(position).getSymbolName());
                    tv_deal_no.setText(PaperList.get(position).getDealNo());
                    tv_quntity.setText(PaperList.get(position).getVolume());
                    et_price=confirmDialog.findViewById(R.id.et_price);
                    et_price.setText(PaperList.get(position).getRate());

                    TextView tv_cancel=confirmDialog.findViewById(R.id.tv_cancel);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogcommna.dismiss();
                        }
                    });

                    TextView tv_update_limit=confirmDialog.findViewById(R.id.tv_update_limit);
                    final TextView finalEt_price = et_price;
                    tv_update_limit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new AlertDialog.Builder(activity)
                                    .setTitle("Vicky Jewellery Works")
                                    .setMessage("Are you sure want to update limit ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            regresponse="";
                                            dialogcommna.dismiss();

                                            String Rate= finalEt_price.getText().toString().trim();
                                            int Sybolid= Integer.parseInt(PaperList.get(position).getSymbolID().trim());

                                            String Token=Common.getPreferenceString(activity,"TokenLogin","");

                                            ObjVariable="{\"SymbolID\":\""+Sybolid+"\"," +
                                                    "\"DealNo\":\""+PaperList.get(position).getDealNo().trim()+"\"," +
                                                    "\"Volume\":\""+PaperList.get(position).getVolume().trim()+"\"," +
                                                    "\"Token\":\""+Token+"\"," +
                                                    "\"OpenOrderID\":\""+PaperList.get(position).getOpenOrderID().trim()+"\"," +
                                                    "\"TradeType\":\""+PaperList.get(position).getTradeType().trim()+"\"," +
                                                    "\"Rate\":\""+Rate+"\"}";




                                            new UpdateLimit().execute();

                                            Log.e("UpdateLimit",ObjVariable);


                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    });

                    TextView tv_delete_limit=confirmDialog.findViewById(R.id.tv_delete_limit);
                    tv_delete_limit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new AlertDialog.Builder(activity)
                                    .setTitle("Vicky Jewellery Works")
                                    .setMessage("Are you sure want to delete the dealNo: "+PaperList.get(position).getDealNo()+" ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialogcommna.dismiss();
                                            regresponse="";

                                            String Token=Common.getPreferenceString(activity,"TokenLogin","");
                                            String LoginId=Common.getPreferenceString(activity,"LoginId","");

                                            ObjVariable="{\"LoginId\":"+LoginId+"," +
                                                    "\"DealNo\":"+PaperList.get(position).getDealNo().trim()+"," +
                                                    "\"Token\":\""+Token+"\"," +
                                                    "\"ClientId\":1}";



                                             Log.e("deleteObj",ObjVariable);

                                            new DeleteLimit().execute();
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    });


                    alert.setView(confirmDialog);

                    dialogcommna = alert.create();

                    dialogcommna.show();

                    dialogcommna.setCanceledOnTouchOutside(true);
                }
            });






        }


        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @Override
        public int getItemCount() {
            Log.e("sizeis",""+PaperList.size());
            return PaperList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView tv_name;
            LinearLayout lay_cart;
            TextView tv_deal_no;
            TextView open_date;
            TextView buy_gream;
            TextView close_price;
            ImageView edit_image;
            LinearLayout close_layout;


            public MyViewHolder(View view) {
                super(view);

                tv_name=view.findViewById(R.id.tv_name);
                lay_cart=view.findViewById(R.id.lay_cart);
                tv_deal_no=view.findViewById(R.id.tv_deal_no);
                open_date=view.findViewById(R.id.open_date);
                buy_gream=view.findViewById(R.id.buy_gream);
                close_price=view.findViewById(R.id.close_price);
                edit_image=view.findViewById(R.id.edit_image);
                close_layout=view.findViewById(R.id.close_layout);
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

        private class UpdateLimit extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress("");

            }

            @Override
            protected String doInBackground(String... params) {
                SoapObject request = new SoapObject("http://tempuri.org/", UpdateLimitByDeal);
                request.addProperty("ObjOrder",ObjVariable);
                Log.e("request", request.toString());
                try {
                    regresponse = loadServiceTerminal(request, UpdateLimitByDeal);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }

                return regresponse;
            }

            @Override
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                try {
                    Log.e("UpdateLimit", s);
                     dismissProgress();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("UpdateLimit", ""+s);


                            final JSONObject json;
                            try {
                                json = new JSONObject(s);

                                String ReturnMsg = json.getString("ReturnMsg");
                                new android.app.AlertDialog.Builder(getActivity())
                                        .setTitle("Vicky Jewellery Works")
                                        .setMessage(ReturnMsg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation
                                                Constants.page=1;
                                                Intent intent=new Intent(activity,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);

                                            }
                                        })
                                        .show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (Exception e) {
                    Log.e("UpdateLimit", e.getMessage());
                    e.printStackTrace();
                }
            }
        }


        private class DeleteLimit extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress("");

            }

            @Override
            protected String doInBackground(String... params) {
                SoapObject request = new SoapObject("http://tempuri.org/", DeleteCloseOrder);
                request.addProperty("ObjOrder",ObjVariable);
                Log.e("request", request.toString());
                try {
                    regresponse = loadServiceTerminal(request, DeleteCloseOrder);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }

                return regresponse;
            }

            @Override
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                try {
                    Log.e("DelteLimit", s);
                    dismissProgress();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("DelteLimit", ""+s);


                            final JSONObject json;
                            try {
                                json = new JSONObject(s);

                                String ReturnMsg = json.getString("ReturnMsg");
                                new android.app.AlertDialog.Builder(getActivity())
                                        .setTitle("Vicky Jewellery Works")
                                        .setMessage(ReturnMsg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation
                                                Constants.page=1;
                                                Intent intent=new Intent(activity,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (Exception e) {
                    Log.e("UpdateLimit", e.getMessage());
                    e.printStackTrace();
                }
            }
        }


//        public String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.setOutputSoapObject(request);
//            envelope.dotNet = true;
//            HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
//            transport.call("http://tempuri.org/" + method, envelope);
//            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//            return response.toString();
//        }

    }





}
