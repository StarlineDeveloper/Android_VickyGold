package com.VijayAssayCenter.Fragmet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.model.GetGenralPrimum;
import com.VijayAssayCenter.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeneralPremium extends Fragment {

    RecyclerView rv_genral_priminum;
    AdapterGenralPrimum adapterGenralPrimum;

    public GeneralPremium() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_premium, container, false);

        GetGenralprimum();
        rv_genral_priminum = (RecyclerView) view.findViewById(R.id.rv_genral_priminum);
        adapterGenralPrimum = new AdapterGenralPrimum(getActivity());
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
        rv_genral_priminum.setLayoutManager(homelayoutmanager);
        rv_genral_priminum.setItemAnimator(new DefaultItemAnimator());
        rv_genral_priminum.setAdapter(adapterGenralPrimum);

        return view;
    }

    public class AdapterGenralPrimum extends RecyclerView.Adapter<AdapterGenralPrimum.MyViewHolder> {

        private Activity activity;
        private List<GetGenralPrimum.SymbolMasterList> PaperList = new ArrayList<GetGenralPrimum.SymbolMasterList>();
        private LayoutInflater layoutInflater;
        private String product_id;
        Animation animFadein;

        private ProgressDialog pDialog;


        public AdapterGenralPrimum(Activity activity) {
            this.activity = activity;
            layoutInflater = LayoutInflater.from(activity);
//           for (int i = 0; i < 5; i++) {
//             PaperList.add("");
//            }
        }

        public void addAll(List<GetGenralPrimum.SymbolMasterList> bannerList) {
            this.PaperList = bannerList;
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genral_primum, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            if (PaperList.get(position).productType.trim().equals("0")) {
                holder.tv_product_type.setText("Product");

            } else if (PaperList.get(position).productType.trim().equals("1")) {
                holder.tv_product_type.setText("Comex");

            } else if (PaperList.get(position).productType.trim().equals("2")) {
                holder.tv_product_type.setText("Future");

            } else {

            }


            holder.tv_symbol.setText(PaperList.get(position).symbolName);
            holder.tv_sorce.setText(PaperList.get(position).source);

            if (PaperList.get(position).displayRateType.trim().equals("mcx")) {
                holder.tv_dispay_rate_type.setText("Exchange");

            } else {
                holder.tv_dispay_rate_type.setText("default");

            }

            holder.tv_digit.setText(PaperList.get(position).digits);


            if (PaperList.get(position).stocks.trim().equals("0")) {
                holder.tv_stoke_dispay.setText("Available/");

            } else {
                holder.tv_stoke_dispay.setText("Not Available/");

            }

            if (PaperList.get(position).isDisplay.trim().equals("True")) {
                holder.tv_display.setText("Yes");

            } else {
                holder.tv_display.setText("No");

            }


            holder.tv_time.setText(PaperList.get(position).createdDate);


            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                    dialog.setContentView(R.layout.bottom_dialog_edit_genral_primum);

                    ImageView iv_cancle = (ImageView) dialog.findViewById(R.id.iv_cancle);


                    iv_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();


                }
            });


        }


        @Override
        public int getItemCount() {
            return PaperList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView tv_edit;
            TextView tv_product_type;
            TextView tv_symbol;
            TextView tv_sorce;
            TextView tv_dispay_rate_type;
            TextView tv_digit;
            TextView tv_stoke_dispay;
            TextView tv_time;
            TextView tv_display;

            public MyViewHolder(View view) {
                super(view);

                tv_edit = (TextView) view.findViewById(R.id.tv_edit);
                tv_product_type = (TextView) view.findViewById(R.id.tv_product_type);
                tv_symbol = (TextView) view.findViewById(R.id.tv_symbol);
                tv_sorce = (TextView) view.findViewById(R.id.tv_sorce);
                tv_dispay_rate_type = (TextView) view.findViewById(R.id.tv_dispay_rate_type);
                tv_digit = (TextView) view.findViewById(R.id.tv_digit);
                tv_stoke_dispay = (TextView) view.findViewById(R.id.tv_stoke_dispay);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                tv_display = (TextView) view.findViewById(R.id.tv_display);

            }
        }


    }


    @SuppressLint("StaticFieldLeak")
    private void GetGenralprimum() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetSymbolDetailsMobile);
                //request.addProperty("Marquee", MarweeTest);


                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.GetSymbolDetailsMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("getgenralprimum", response);

                    GetGenralPrimum pt = new Gson().fromJson(response, new TypeToken<GetGenralPrimum>() {
                    }.getType());
                    adapterGenralPrimum.addAll(pt.symbolMasterList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public static String loadServiceForBullion(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("http://192.168.3.23:1155/webservice/MobileAdmin.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }


}
