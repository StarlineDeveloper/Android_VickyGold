package com.VijayAssayCenter.adapter;

import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.UpdateModel;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {
    private List<UpdateModel> symbolModelsList;
    private Context context;


    public UpdateAdapter(List<UpdateModel> symbolModelsList, Context context) {
        this.symbolModelsList = symbolModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.updates_row_new, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            UpdateModel generalPremiumBean = symbolModelsList.get(position);

            holder.tvTitle.setText(generalPremiumBean.getTitle());
            holder.time.setText(context.getResources().getString(R.string.time)+" "+generalPremiumBean.getTime());
            holder.date.setText(generalPremiumBean.getDay()+", "+generalPremiumBean.getMonth());
            holder.tv_des.setText(Html.fromHtml(generalPremiumBean.getSortnews()));

            Log.e("Datatata",generalPremiumBean.getTitle());


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public int getItemCount() {
        return symbolModelsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView date;
        TextView time;
        TextView tv_des;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            tv_des = view.findViewById(R.id.tv_des);


        }
    }


}

