package com.VijayAssayCenter.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.MentalAddressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jatinkintu on 12/12/17.
 */

public class MendalDelevory extends RecyclerView.Adapter<MendalDelevory.MyViewHolder> {

    private Activity activity;
    private List<MentalAddressModel> PaperList = new ArrayList<MentalAddressModel>();
    private LayoutInflater layoutInflater;
    private String product_id;
    Animation animFadein;
    private List<Boolean> isclick = new ArrayList<>();

    AlertDialog abstudent;



    public MendalDelevory(Activity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
//        for (int i = 0; i < 2; i++
//                ) {
//            PaperList.add("");
//        }
    }

    public void addAll(List<MentalAddressModel> bannerList) {
        this.PaperList = bannerList;

       /* for (int i = 0; i < bannerList.size(); i++) {
            isclick.add(i, false);

        }*/
        //notifyDataSetChanged();
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentel_delevry, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.office_title.setText(PaperList.get(position).getOfficeTitle());
            holder.tv_address.setText(PaperList.get(position).getaddress());
            holder.avalable_service.setText(PaperList.get(position).getServiceAvailable());

            holder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse(PaperList.get(position).getmapURL()));
                    activity.startActivity(httpIntent);
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



        TextView office_title;
        TextView tv_address;
        TextView avalable_service;
        TextView click;

        public MyViewHolder(View view) {
            super(view);

            office_title=view.findViewById(R.id.office_title);
            tv_address=view.findViewById(R.id.tv_address);
            avalable_service=view.findViewById(R.id.avalable_service);
            click=view.findViewById(R.id.click);
        }
    }


}
