package com.VijayAssayCenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.NavigationList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UV on 29-Nov-16.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private List<String> drawerList = new ArrayList<>();
    private List<Integer> iconList = new ArrayList<>();
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context) {
        this.context = context;
        drawerList = NavigationList.getInstance().getTitleList();
        iconList = NavigationList.getInstance().getImageList();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return drawerList.size();
    }

    @Override
    public Object getItem(int i) {
        return drawerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        NavigationDrawerViewHolder listViewHolder;
        listViewHolder = new NavigationDrawerViewHolder();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_nav, viewGroup, false);

            listViewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            listViewHolder.tvGroupLable = (TextView) convertView.findViewById(R.id.tvGroupLable);
            listViewHolder.ivLeft = (ImageView) convertView.findViewById(R.id.ivLeft);
            listViewHolder.ivRight = (ImageView) convertView.findViewById(R.id.ivRight);
            listViewHolder.llInfo = (LinearLayout) convertView.findViewById(R.id.llInfo);
            convertView.setTag(listViewHolder);

        } else {
            listViewHolder = (NavigationDrawerViewHolder) convertView.getTag();
        }


        listViewHolder.ivRight.setVisibility(View.GONE);
        listViewHolder.llInfo.setVisibility(View.GONE);
        listViewHolder.tvName.setText(drawerList.get(i));
        listViewHolder.ivLeft.setImageResource(iconList.get(i));
        return convertView;
    }
}

class NavigationDrawerViewHolder {

    TextView tvName, tvGroupLable;
    ImageView ivLeft, ivRight;
    LinearLayout llInfo;
}