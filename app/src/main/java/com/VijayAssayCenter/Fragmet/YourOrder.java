package com.VijayAssayCenter.Fragmet;

import static com.VijayAssayCenter.utils.BaseActivity.loadServiceTerminal;
import static com.VijayAssayCenter.utils.Constants.GetCloseOrderDetails;
import static com.VijayAssayCenter.utils.Constants.GetDeleteOrderDetails;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.ApplicationController;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class YourOrder extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    AVLoadingIndicatorView avLoadingIndicatorView;

    String regresponse = "";

    private int mYear, mMonth, mDay;
    private String Y, M, D;

    String Firmname = "vickygold";
    String Fromdate = "";
    String Todate = "";
    int loginid = 0;
    String ObjVariable = "";

    TextView tv_balance;
    TextView tv_free_margin;
    TextView use_margin;
    TextView tv_pl;

    TextView fromDate;
    TextView toDAte;

    LinearLayout lay_vis;
    LinearLayout lay_not;


    TextView tv_search;


    public YourOrder() {
        // Required empty public constructor
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //do something  //Load or Refresh Data

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    new GetOpenOrderDetails().execute();
                    new GetDeleteOrder().execute();
                    new GetCloseOrder().execute();


                }
            }, 100);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_order, container, false);

        fromDate = view.findViewById(R.id.date1);
        toDAte = view.findViewById(R.id.date2);


        if (Common.getPreferenceString(getActivity(), "LoginId", "").equals("")) {

        } else {
            loginid = Integer.parseInt(Common.getPreferenceString(getActivity(), "LoginId", ""));

        }


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

        Todate = df.format(c);
        Fromdate = df.format(c);

        fromDate.setText(df.format(c));
        toDAte.setText(df.format(c));

        tv_search = view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjVariable = "{" +
                        "ClientID:1," +
                        "\"Firmname\":\"" + Firmname + "\"," +
                        "\"Fromdate\":\"" + Todate + "\"," +
                        "\"Todate\":\"" + Fromdate + "\"," +
                        "loginid:" + loginid + "}";


                Log.e("ObjVariable", ObjVariable);


                new GetOpenOrderDetails().execute();
                new GetDeleteOrder().execute();
                new GetCloseOrder().execute();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                D = String.valueOf(dayOfMonth);
                                M = String.valueOf(monthOfYear + 1);
                                Y = String.valueOf(year);

                                if (M.length() == 1) {
                                    M = "0" + M;
                                } else {

                                }

                                if (D.length() == 1) {
                                    D = "0" + D;
                                } else {

                                }


                                fromDate.setText(D + "/" + M + "/" + Y);
                                Todate = D + "/" + M + "/" + Y;


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        toDAte.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                D = String.valueOf(dayOfMonth);
                                M = String.valueOf(monthOfYear + 1);
                                Y = String.valueOf(year);

                                if (M.length() == 1) {
                                    M = "0" + M;
                                } else {

                                }

                                if (D.length() == 1) {
                                    D = "0" + D;
                                } else {

                                }


                                toDAte.setText(D + "/" + M + "/" + Y);
                                Fromdate = D + "/" + M + "/" + Y;

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        avLoadingIndicatorView = view.findViewById(R.id.avi);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        tv_balance = view.findViewById(R.id.tv_balance);
        tv_free_margin = view.findViewById(R.id.tv_free_margin);
        use_margin = view.findViewById(R.id.use_margin);
        // viewPager.setOffscreenPageLimit(5);

        avLoadingIndicatorView = view.findViewById(R.id.avi);


        lay_vis = view.findViewById(R.id.lay_vis);
        lay_not = view.findViewById(R.id.lay_not);

        final String UserName = Common.getPreferenceString(getActivity(), "UserName", "");
        Log.e("UserName", UserName);
        if (UserName.equals("")) {
            lay_vis.setVisibility(View.GONE);
            lay_not.setVisibility(View.VISIBLE);
        } else {

            lay_vis.setVisibility(View.VISIBLE);
            lay_not.setVisibility(View.GONE);

            ObjVariable = "{" +
                    "ClientID:1," +
                    "\"Firmname\":\"" + Firmname + "\"," +
                    "\"Fromdate\":\"" + Todate + "\"," +
                    "\"Todate\":\"" + Fromdate + "\"," +
                    "loginid:" + loginid + "}";
            Log.e("ObjVariable", ObjVariable);
            new GetOpenOrderDetails().execute();
            new GetDeleteOrder().execute();
            new GetCloseOrder().execute();
        }


        return view;
    }

    @Override
    public void onResume() {
        ApplicationController.getInstance().trackScreenView("Your Orders Screen_Android");

        super.onResume();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OpenOrder(), "Open Order");
        adapter.addFragment(new PendingOrder(), "Pending Order");
        //adapter.addFragment(new RequestsOrder(), "Requests Order");
        adapter.addFragment(new CloseTrade(), "Close Trade");
        adapter.addFragment(new DeletedTrade(), "Deleted Trade");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

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
        tabOne.setText("Open Order");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabTwo.setText("Pending Order");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

//        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
//        tabThree.setText("Requests Order");
//        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabFour.setText("Close Trade");
        tabLayout.getTabAt(2).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_oderser, null);
        tabFive.setText("Deleted Trade");
        tabLayout.getTabAt(3).setCustomView(tabFive);
    }


    private class GetOpenOrderDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetOpenOrderDetails);
            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            //Log.e("jkodfjgopkjgfg",ObjVariable);
            try {
                regresponse = loadServiceTerminal(request, Constants.GetOpenOrderDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                // Log.e("orderresponce", s);

                avLoadingIndicatorView.setVisibility(View.GONE);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);


                            Constants.OpenOrder = json.getString("OpenOrder");

                            Constants.Accountdetails = json.getString("Accountdetails");
                            tabLayout.setupWithViewPager(viewPager);
                            setupViewPager(viewPager);
                            setupTabIcons();
                            viewPager.setCurrentItem(Constants.Pgaeno);

                            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                public void onPageScrollStateChanged(int arg0) {
                                }

                                public void onPageScrolled(int arg0, float arg1, int arg2) {
                                }

                                public void onPageSelected(int currentPage) {
                                    //currentPage is the position that is currently displayed.

                                    Constants.Pgaeno = currentPage;
                                    Log.e("jkjdjjhf", "" + currentPage);
                                }

                            });


                            JSONArray jArray = new JSONArray(Constants.Accountdetails);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);

                                Constants.Balance = json_data.getString("Balance");
                                Constants.FreeMargin = json_data.getString("FreeMargin");
                                Constants.UsedMargin = json_data.getString("UsedMargin");
                                Constants.Msg = json_data.getString("Msg");

                            }
                            tv_balance.setText(Constants.Balance);
                            tv_free_margin.setText(Constants.FreeMargin);
                            use_margin.setText(Constants.UsedMargin);
                            tv_pl.setText(Constants.Msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("re_response", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private class GetDeleteOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("DeleteOrder", "Call");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetDeleteOrderDetails);

            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetDeleteOrderDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("DeleteOrder", s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            Constants.DeleteOrder = json.getString("DeleteOrder");
                            Log.e("DeleteOrder", Constants.DeleteOrder);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("DeleteOrder", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private class GetCloseOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("GetCloseOrderDetails", "Call");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetCloseOrderDetails);
            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetCloseOrderDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("GetCloseOrderDetails", s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);
                            Constants.ClosedOrder = json.getString("ClosedOrder");

                            Log.e("GetCloseOrderDetails", Constants.ClosedOrder);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("GetCloseOrderDetails", e.getMessage());
                e.printStackTrace();
            }
        }
    }


//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

}
