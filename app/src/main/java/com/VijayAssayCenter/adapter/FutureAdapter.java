package com.VijayAssayCenter.adapter;

import android.app.Activity;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jatinkintu on 12/12/17.
 */

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.MyViewHolder> {

    private Activity activity;
    private List<String> PaperList = new ArrayList<String>();
    private LayoutInflater layoutInflater;
    private String product_id;
    Animation animFadein;
    private List<Boolean> isclick = new ArrayList<>();

    AlertDialog abstudent;



    public FutureAdapter(Activity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        for (int i = 0; i < 6; i++
                ) {
            PaperList.add("");
        }
    }

//    public void addAll(List<GetCategory.Datum> bannerList) {
//        this.PaperList = bannerList;
//
//       /* for (int i = 0; i < bannerList.size(); i++) {
//            isclick.add(i, false);
//
//        }*/
//        //notifyDataSetChanged();
//        notifyDataSetChanged();
//    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future_rate, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {




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




        public MyViewHolder(View view) {
            super(view);


        }
    }


}
