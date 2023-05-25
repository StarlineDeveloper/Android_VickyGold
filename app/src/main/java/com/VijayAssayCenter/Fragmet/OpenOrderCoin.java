package com.VijayAssayCenter.Fragmet;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
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
import com.VijayAssayCenter.model.OpenOrderModel;
import com.VijayAssayCenter.model.OpenOrderModelCoin;
import com.VijayAssayCenter.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class OpenOrderCoin extends Fragment {

    RecyclerView rv_cart;
    CategoryOpenOrder cartItems;




    public OpenOrderCoin() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_open_order, container, false);



        if(Constants.OpenOrderCoin.equals("")){

        }else {
            rv_cart=view.findViewById(R.id.rv_cart);
            cartItems = new CategoryOpenOrder(getActivity());
            RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
            rv_cart.setLayoutManager(homelayoutmanager);
            rv_cart.setItemAnimator(new DefaultItemAnimator());
            rv_cart.setAdapter(cartItems);

            cartItems.addAll((List<OpenOrderModelCoin>) new Gson().fromJson(Constants.OpenOrderCoin, new TypeToken<ArrayList<OpenOrderModelCoin>>() {
            }.getType()));
        }



        return view;
    }


    public class CategoryOpenOrder extends RecyclerView.Adapter<CategoryOpenOrder.MyViewHolder> {

        private Activity activity;
        private List<OpenOrderModelCoin> PaperList = new ArrayList<OpenOrderModelCoin>();
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

        public void addAll(List<OpenOrderModelCoin> bannerList) {
            this.PaperList = bannerList;

        for (int i = 0; i < bannerList.size(); i++) {
            isclick.add(i, false);

        }
            //notifyDataSetChanged();
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_oder_open, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {



            holder.tv_name.setText(PaperList.get(position).getSymbolName());
            holder.open_date.setText(PaperList.get(position).getOpenTradeDateTime());
            holder.tv_deal_no.setText("Order No: "+PaperList.get(position).getDealNo());

            if(PaperList.get(position).getTradeType().trim().equals("1")||PaperList.get(position).getTradeType().trim().equals("3")){

                float Rate= Float.parseFloat(PaperList.get(position).getRate());
                float Volume= Float.parseFloat(PaperList.get(position).getVolume());
                float Total= Rate*Volume;
                DecimalFormat threevalue55 = new DecimalFormat("0.00");
                String formattedee = threevalue55.format(Total);

                holder.buy_gream.setText("Buy("+PaperList.get(position).getVolume()+" Qty):"+PaperList.get(position).getRate()+"->"+formattedee);

            } else {
                float Rate= Float.parseFloat(PaperList.get(position).getRate());
                float Volume= Float.parseFloat(PaperList.get(position).getVolume());
                float Total= Rate*Volume;
                DecimalFormat threevalue55 = new DecimalFormat("0.00");
                String formattedee = threevalue55.format(Total);

                holder.buy_gream.setText("Sell("+PaperList.get(position).getVolume()+" Qty):"+PaperList.get(position).getRate()+"->"+formattedee);
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
                holder.close_layout.setVisibility(View.GONE);


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
