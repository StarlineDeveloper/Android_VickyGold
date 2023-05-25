package com.VijayAssayCenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.Fragmet.Transactions;
import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.BankDetailsModel;
import com.VijayAssayCenter.model.GetTransection;
import com.VijayAssayCenter.model.MentalAddressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jatinkintu on 12/12/17.
 */

public class TranScetionAdapter extends RecyclerView.Adapter<TranScetionAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<GetTransection> arrayList;




    public TranScetionAdapter(Context context, ArrayList<GetTransection> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transection, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        GetTransection bankDetails = arrayList.get(position);

        holder.tv_account_no.setText(bankDetails.getPayeeAccountNumber());
        holder.tv_amount.setText(" ₹ "+bankDetails.getAmount());
        holder.tv_vitual_code.setText(bankDetails.getVirtualACCode());
        holder.tv_utr.setText(bankDetails.getUTR());
        holder.tv_date.setText(bankDetails.getPayeePaymentDate());
        holder.tv_ifcs.setText(bankDetails.getPayeeBankIFSC());
//        holder.tv_2.setText(bankDetails.getPayeePaymentDate());
//        holder.tv_3.setText(bankDetails.getUTR());
//        holder.tv_amount.setText(bankDetails.getAmount());


    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {




        TextView tv_account_no;
        TextView tv_amount;
        TextView tv_vitual_code;
        TextView tv_utr;
        TextView tv_date;
        TextView tv_ifcs;
        public MyViewHolder(View view) {
            super(view);
            tv_account_no=view.findViewById(R.id.tv_account_no);
            tv_amount=view.findViewById(R.id.tv_amount);
            tv_vitual_code=view.findViewById(R.id.tv_vitual_code);
            tv_utr=view.findViewById(R.id.tv_utr);
            tv_date=view.findViewById(R.id.tv_date);
            tv_ifcs=view.findViewById(R.id.tv_ifcs);

        }
    }


}
