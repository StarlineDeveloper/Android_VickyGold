package com.VijayAssayCenter.adapter;

import android.content.Context;
//import android.support.v7.widget.AppCompatImageView;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.BankDetailsModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by ${Vrund} on 20-12-2017.
 */

public class BankDetailsAdapterBullion extends RecyclerView.Adapter<BankDetailsAdapterBullion.Holder> {

    private Context context;
    private ArrayList<BankDetailsModel> arrayList;

    public BankDetailsAdapterBullion(Context context, ArrayList<BankDetailsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.bankdetail_row, null);
        return new Holder(root);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        try {
            BankDetailsModel bankDetails = arrayList.get(position);
            holder.tvAccountName.setText(bankDetails.getAccountName());
            holder.tvBankName.setText(bankDetails.getBankName());
            holder.tvAccountNo.setText(bankDetails.getAccountNo());
            holder.tvIFSCCode.setText(bankDetails.getIfsc());
            holder.tvBranchName.setText(bankDetails.getBranchName());

//            String[] separated = bankDetails.getBankLogo().split("https");
//            String Urlis=separated[1];


                Glide.with(context)
                        .load(arrayList.get(position).getBankLogo())
                        .into(holder.ivBankLogo);




            Log.e("Logo",arrayList.get(position).getBankLogo());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public BankDetailsModel getItem(int pos) {
        return arrayList.get(pos);
    }

    public void refill(ArrayList<BankDetailsModel> d) {
        arrayList.clear();
        arrayList.addAll(d);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tvAccountName)
        TextView tvAccountName;
//        @BindView(R.id.tvBankName)
        TextView tvBankName;
//        @BindView(R.id.tvAccountNo)
        TextView tvAccountNo;
//        @BindView(R.id.tvIFSCCode)
        TextView tvIFSCCode;
//        @BindView(R.id.tvBranchName)
        TextView tvBranchName;

//        @BindView(R.id.ivBankLogo)
        AppCompatImageView ivBankLogo;

        private Holder(View vi) {
            super(vi);
            tvAccountName=vi.findViewById(R.id.tvAccountName);
            tvBankName=vi.findViewById(R.id.tvBankName);
            tvAccountNo=vi.findViewById(R.id.tvAccountNo);
            tvIFSCCode=vi.findViewById(R.id.tvIFSCCode);
            tvBranchName=vi.findViewById(R.id.tvBranchName);
            ivBankLogo=vi.findViewById(R.id.ivBankLogo);
            ivBankLogo=vi.findViewById(R.id.ivBankLogo);
//            ButterKnife.bind(this, vi);
        }
    }


}
