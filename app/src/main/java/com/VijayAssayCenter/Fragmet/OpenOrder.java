package com.VijayAssayCenter.Fragmet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
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
import com.VijayAssayCenter.activity.PaymentBankDetails;
import com.VijayAssayCenter.model.BankDetailsModel;
import com.VijayAssayCenter.model.OpenOrderModel;
import com.VijayAssayCenter.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class OpenOrder extends Fragment {

    RecyclerView rv_cart;
    CategoryOpenOrder cartItems;




    public OpenOrder() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_open_order, container, false);



        Log.e("tettetetetetetettetet",Constants.OpenOrder);
        if(Constants.OpenOrder.equals("")){

        }else {
            rv_cart=view.findViewById(R.id.rv_cart);
            cartItems = new CategoryOpenOrder(getActivity());
            RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
            rv_cart.setLayoutManager(homelayoutmanager);
            rv_cart.setItemAnimator(new DefaultItemAnimator());
            rv_cart.setAdapter(cartItems);

            cartItems.addAll((List<OpenOrderModel>) new Gson().fromJson(Constants.OpenOrder, new TypeToken<ArrayList<OpenOrderModel>>() {
            }.getType()));
        }



        return view;
    }


    public class CategoryOpenOrder extends RecyclerView.Adapter<CategoryOpenOrder.MyViewHolder> {

        private Activity activity;
        private List<OpenOrderModel> PaperList = new ArrayList<OpenOrderModel>();
        private LayoutInflater layoutInflater;
        private String product_id;
        Animation animFadein;
        private List<Boolean> isclick = new ArrayList<>();

        AlertDialog abstudent;



        public CategoryOpenOrder(Activity activity) {
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

            String formatted="";
            holder.edit_image.setVisibility(View.GONE);

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


//                  if(separated.length<=1){
//                      Log.e("tessss","11");
//
//                  }else {
//                      Volumne=PaperList.get(position).getVolume();
//                  }

//                  if(separated[1].length()<1){
//                  }else {
//
//                  }


                  String SeeIS=df.format(TotalIs);

                    if(PaperList.get(position).getTradeType().trim().equals("3")){

                        holder.buy_gream.setText("BuyLimit "+formatted+" "+Lable+": Rate "+GaramFinal+" -> Amount "+SeeIS);

                    }else if(PaperList.get(position).getTradeType().trim().equals("5")){
                        holder.buy_gream.setText("BuyRequst "+formatted+" "+Lable+": Rate "+GaramFinal+" -> Amount "+SeeIS);

                    }else {
                        holder.buy_gream.setText("Buy "+formatted+" "+Lable+": Rate "+GaramFinal+" -> Amount "+SeeIS);

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
                        holder.buy_gream.setText("SellLimit "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount"+SeeIS);

                    }else if(PaperList.get(position).getTradeType().trim().equals("6")){
                        holder.buy_gream.setText("SellRequst "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount"+SeeIS);

                    }else {
                        holder.buy_gream.setText("Sell "+formatted+" "+Lable+" : Rate "+GaramFinal+" -> Amount"+SeeIS);

                    }

                }






              holder.close_price.setVisibility(View.GONE);
              holder.close_price.setText("Close Price: "+PaperList.get(position).getClosePrice());

            // Log.e("Types",PaperList.get(position).getTradeType());
          //  Log.e("Types",PaperList.get(position).getTradeMode());
            //Log.e("Volume",PaperList.get(position).getVolume());


            if(PaperList.get(position).getTradeMode().trim().equals("M")){
                if(PaperList.get(position).getTradeType().trim().equals("1")||PaperList.get(position).getTradeType().trim().equals("2")){
                    holder.lay_cart.setVisibility(View.VISIBLE);




                }else {
                    holder.lay_cart.setVisibility(View.GONE);


                }


            }else{
                holder.lay_cart.setVisibility(View.GONE);


            }

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


    }


}
