package com.VijayAssayCenter.Fragmet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.ApplicationController;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class BankInfo extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public BankInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_info, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager2);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();


        return view;
    }

    @Override
    public void onResume() {

        ApplicationController.getInstance().trackScreenView("Bank Info Screen_Android");

        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BankDetails(), "Bank Details");
        adapter.addFragment(new Transactions(), "Transactions");
        viewPager.setAdapter(adapter);


        //viewPager.setOffscreenPageLimit(1);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabOne.setText("Bank Details");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabTwo.setText("Transactions");
        tabLayout.getTabAt(1).setCustomView(tabTwo);


    }

}