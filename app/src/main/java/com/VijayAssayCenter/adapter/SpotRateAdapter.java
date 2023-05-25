package com.VijayAssayCenter.adapter;

import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.LiverateSymbolModel;

import java.util.List;

public class SpotRateAdapter extends RecyclerView.Adapter<SpotRateAdapter.LiveRateViewHolder> {
    private List<LiverateSymbolModel.MCXBean> spotSymbolList;
    private int red, green, black, white;


    public SpotRateAdapter(List<LiverateSymbolModel.MCXBean> spotSymbolList, Context context) {
        this.spotSymbolList = spotSymbolList;
        red = ContextCompat.getColor(context, R.color.red);
        green = ContextCompat.getColor(context, R.color.green);
        black = ContextCompat.getColor(context, R.color.black);
        white = ContextCompat.getColor(context, R.color.white);
    }

    @NonNull
    @Override
    public LiveRateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_spot_fut_list, viewGroup, false);

        return new LiveRateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveRateViewHolder holder, int position) {

        try {
            LiverateSymbolModel.MCXBean mcxBean = spotSymbolList.get(position);
            /*set symbolname*/
            holder.tvSymbolName.setText(mcxBean.getSymbol_Name());
            /*set high*/
            holder.tvHighValue.setText(mcxBean.getHigh());
            /*set low*/
            holder.tvLowValue.setText(mcxBean.getLow());

            /* Buyvalue set Up and Down With Text Color */
            /* Buyvalue set Up and Down */
            if (mcxBean.getBid().equalsIgnoreCase("--")) {
                holder.tvBuyValue.setBackgroundResource(0);
                holder.tvBuyValue.setText(mcxBean.getBid());
                holder.tvBuyValue.setTextColor(black);
            } else {
                if (!holder.tvBuyValue.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.tvBuyValue.getText())) {

                    if (Double.parseDouble(mcxBean.getBid()) != Double.parseDouble(holder.tvBuyValue.getText().toString())) {

//                        holder.tvBuyValue.setBackgroundResource(Double.parseDouble(mcxBean.getBid())
//                                < Double.parseDouble(holder.tvBuyValue.getText().toString()) ? R.drawable.liverate_red : R.drawable.liverate_green);

                        /*Log.e("test",""+R.drawable.liverate_red);
                        Log.e("test",""+R.drawable.liverate_green);*/

                        if(Double.parseDouble(mcxBean.getBid()) < Double.parseDouble(holder.tvBuyValue.getText().toString())){
                            holder.tvBuyValue.setTextColor(red);

                        }else {
                            holder.tvBuyValue.setTextColor(green);

                        }

                        holder.tvBuyValue.setText(mcxBean.getBid());
                        //holder.tvBuyValue.setTextColor(black);

                    } else {
                        holder.tvBuyValue.setText(mcxBean.getBid());
                        holder.tvBuyValue.setTextColor(black);
                        holder.tvBuyValue.setBackgroundResource(0);
                    }
                } else {
                    holder.tvBuyValue.setText(mcxBean.getBid());
                    holder.tvBuyValue.setTextColor(black);
                    holder.tvBuyValue.setBackgroundResource(0);
                }
            }

            /* Sellvalue set Up and Down  With Backgroud Resource Color */
            if (mcxBean.getAsk().equalsIgnoreCase("--")) {

                holder.tvSellValue.setBackgroundResource(0);
                holder.tvSellValue.setText(mcxBean.getAsk());
                holder.tvSellValue.setTextColor(black);
            } else {

                if (!holder.tvSellValue.getText().toString().equalsIgnoreCase("--") && !TextUtils.isEmpty(holder.tvSellValue.getText())) {

                    if (Double.parseDouble(mcxBean.getAsk()) != Double.parseDouble(holder.tvSellValue.getText().toString())) {

//                        holder.tvSellValue.setBackgroundResource(Double.parseDouble(mcxBean.getAsk())
//                                < Double.parseDouble(holder.tvSellValue.getText().toString()) ? R.drawable.liverate_red : R.drawable.liverate_green);

                        if(Double.parseDouble(mcxBean.getAsk()) < Double.parseDouble(holder.tvSellValue.getText().toString())){
                            holder.tvSellValue.setTextColor(red);

                        }else {
                            holder.tvSellValue.setTextColor(green);

                        }

                        holder.tvSellValue.setText(mcxBean.getAsk());

                    } else {
                        holder.tvSellValue.setText(mcxBean.getAsk());
                        holder.tvSellValue.setTextColor(black);
                        holder.tvSellValue.setBackgroundResource(0);
                    }
                } else {
                    holder.tvSellValue.setText(mcxBean.getAsk());
                    holder.tvSellValue.setTextColor(black);
                    holder.tvSellValue.setBackgroundResource(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refillData(List<LiverateSymbolModel.MCXBean> symbolModelsList) {
        this.spotSymbolList = symbolModelsList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return spotSymbolList.size();
    }

    class LiveRateViewHolder extends RecyclerView.ViewHolder {
        TextView tvSymbolName, tvBuyValue, tvSellValue, tvHighValue, tvLowValue;
        FrameLayout fl_BuyValue, fl_SellValue;

        LiveRateViewHolder(View view) {
            super(view);
            tvSymbolName = view.findViewById(R.id.tvSymbolName);
            tvBuyValue = view.findViewById(R.id.tvBuyValue);
            tvSellValue = view.findViewById(R.id.tvSellValue);
            tvHighValue = view.findViewById(R.id.tvHighValue);
            tvLowValue = view.findViewById(R.id.tvLowValue);
            fl_BuyValue = view.findViewById(R.id.fl_BuyValue);
            fl_SellValue = view.findViewById(R.id.fl_SellValue);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

