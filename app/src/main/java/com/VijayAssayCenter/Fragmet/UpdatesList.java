package com.VijayAssayCenter.Fragmet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
//import android.support.design.widget.BottomSheetDialog;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.model.GetNews;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UpdatesList extends Fragment {


    RecyclerView rv_news;
    AdapterNews adapterNews;
 AlertDialog alertDialogcomman;

    AsyncProgressDialog ad;

    LinearLayout lay_add;

    String AddTitle="";
    String AddDescription="";
    String UpdateId="";



    public UpdatesList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_updates_list, container, false);


        GetNewsList();
        rv_news= (RecyclerView) view.findViewById(R.id.rv_news);
        adapterNews = new AdapterNews(getActivity());
        RecyclerView.LayoutManager homelayoutmanager = new GridLayoutManager(getActivity(), 1);
        rv_news.setLayoutManager(homelayoutmanager);
        rv_news.setItemAnimator(new DefaultItemAnimator());
        rv_news.setAdapter(adapterNews);

        lay_add= (LinearLayout) view.findViewById(R.id.lay_add);
        lay_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(R.layout.item_add);

                ImageView iv_cancle= (ImageView) dialog.findViewById(R.id.iv_cancle);
                final EditText et_title= (EditText) dialog.findViewById(R.id.et_title);
                final EditText et_description= (EditText) dialog.findViewById(R.id.et_description);
                TextView tv_submit= (TextView) dialog.findViewById(R.id.tv_submit);
                TextView tv_lable= (TextView) dialog.findViewById(R.id.tv_lable);
                tv_lable.setText("Add Updates");


                iv_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                tv_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_title.getText().toString().trim().length() == 0) {
                            et_title.setError("Title");
                            et_title.requestFocus();
                        } else if (et_description.getText().toString().trim().length() == 0) {
                            et_description.setError("Description");
                            et_description.requestFocus();
                        } else if (isConnected()) {
                            AddTitle=et_title.getText().toString().trim();
                            AddDescription=et_description.getText().toString().trim();
                            dialog.dismiss();

                            AddUpdates();


                        } else {
                            Toast.makeText(getActivity(), "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });


                dialog.show();
            }
        });

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    private void AddUpdates() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.AddNewsMobile);
                request.addProperty("Title", AddTitle);
                request.addProperty("Discription", AddDescription);



                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.AddNewsMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("AddResponce",response);

                    dismissProgress();

                    JSONObject Addrespnce = new JSONObject(response);

                    if(Addrespnce.getString("Status").equals("true")){
                        GetNewsList();
                        DialogSuccessComman();
                    }
                    else {
                        Toast.makeText(getActivity(),"failed...!", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }



    @SuppressLint("StaticFieldLeak")
    private void Updatenews() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.EditNewsMobile);
                request.addProperty("Id", UpdateId);
                request.addProperty("Title", AddTitle);
                request.addProperty("Discription", AddDescription);



                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.EditNewsMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("UpdateResponce",response);

                    dismissProgress();

                    JSONObject Addrespnce = new JSONObject(response);

                    if(Addrespnce.getString("Status").equals("true")){
                        GetNewsList();
                        DialogSuccessComman();
                    }
                    else {
                        Toast.makeText(getActivity(),"failed...!", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {

        private Activity activity;
        private List<GetNews.Newslist> PaperList = new ArrayList<GetNews.Newslist>();
        private LayoutInflater layoutInflater;
        private String product_id;
        Animation animFadein;

        private ProgressDialog pDialog;




        public AdapterNews(Activity activity) {
            this.activity = activity;
            layoutInflater = LayoutInflater.from(activity);
          /*  for (int i = 0; i < 50; i++) {
             PaperList.add("");
            }*/
        }

        public void addAll(List<GetNews.Newslist> bannerList) {
            this.PaperList = bannerList;
            notifyDataSetChanged();
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


              holder.tv_title.setText(PaperList.get(position).title);
              holder.tv_date.setText(PaperList.get(position).mdate);
              holder.tv_des.setText(PaperList.get(position).description);


              holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                              .setTitle("Delete")
                              .setMessage("Are you sure want to delete")
                              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {

                                      Delete(PaperList.get(position).newsId);

                                      Log.e("newsidd",PaperList.get(position).newsId);
                                  }
                              })
                              .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                  }
                              })
                              .show();
                  }
              });

              holder.et_edit.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                      dialog.setContentView(R.layout.item_add);

                      ImageView iv_cancle= (ImageView) dialog.findViewById(R.id.iv_cancle);
                      final EditText et_title= (EditText) dialog.findViewById(R.id.et_title);
                      final EditText et_description= (EditText) dialog.findViewById(R.id.et_description);
                      TextView tv_submit= (TextView) dialog.findViewById(R.id.tv_submit);
                      TextView tv_lable= (TextView) dialog.findViewById(R.id.tv_lable);
                      tv_lable.setText("Update");
                      tv_submit.setText("Update");


                      iv_cancle.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();
                          }
                      });

                      et_title.setText(PaperList.get(position).title);
                      et_description.setText(PaperList.get(position).description);

                      tv_submit.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              if (et_title.getText().toString().trim().length() == 0) {
                                  et_title.setError("Title");
                                  et_title.requestFocus();
                              } else if (et_description.getText().toString().trim().length() == 0) {
                                  et_description.setError("Description");
                                  et_description.requestFocus();
                              } else if (isConnected()) {
                                  AddTitle=et_title.getText().toString().trim();
                                  AddDescription=et_description.getText().toString().trim();
                                  UpdateId=PaperList.get(position).newsId;

                                  dialog.dismiss();
                                  Updatenews();



                              } else {
                                  Toast.makeText(getActivity(), "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();
                              }
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


            TextView tv_title;
            TextView tv_date;
            TextView tv_des;
            TextView tv_delete;
            TextView et_edit;


            public MyViewHolder(View view) {
                super(view);
                tv_title= (TextView) view.findViewById(R.id.tv_title);
                tv_date= (TextView) view.findViewById(R.id.tv_date);
                tv_des= (TextView) view.findViewById(R.id.tv_des);
                tv_delete= (TextView) view.findViewById(R.id.tv_delete);
                et_edit= (TextView) view.findViewById(R.id.et_edit);

            }
        }



    }


    @SuppressLint("StaticFieldLeak")
    private void GetNewsList() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetNewsMobile);
                //request.addProperty("Marquee", MarweeTest);


                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.GetNewsMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("getupdatelist",response);

                    GetNews pt = new Gson().fromJson(response, new TypeToken<GetNews>() {
                    }.getType());
                    adapterNews.addAll(pt.newslist);


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

    @SuppressLint("StaticFieldLeak")
    private void Delete(final String Id) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.DeleteNewsMobile);
                request.addProperty("Id", Id);


                try {

                    bankDetailsResponse = loadServiceForBullion(request, Constants.DeleteNewsMobile);
                } catch (Exception e) {

                    e.printStackTrace();
                }

                return bankDetailsResponse;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    Log.e("DeleteResponce",response);
                    dismissProgress();
                    JSONObject MarqueeJson = new JSONObject(response);
                    if(MarqueeJson.getString("Status").equals("true")){
                        DialogSuccessComman();
                        GetNewsList();

                    }
                    else {


                        Toast.makeText(getActivity(),""+MarqueeJson.getString("msg"), Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }


    public void DialogSuccessComman() {

        LayoutInflater li = LayoutInflater.from(getActivity());

        final View confirmDialog = li.inflate(R.layout.dailog_success, null);
        TextView tv_msg= (TextView) confirmDialog.findViewById(R.id.tv_msg);
        //tv_msg.setText(msg);

        final TextView tv_ok= (TextView) confirmDialog.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(300);
                tv_ok.setAlpha(1f);
                tv_ok.startAnimation(animation1);
                alertDialogcomman.dismiss();



            }
        });

       AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(confirmDialog);

        alertDialogcomman = alert.create();

        alertDialogcomman.show();

        alertDialogcomman.setCanceledOnTouchOutside(true);




    }


    public void showProgress(String msg) {

        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(getActivity());
            ad.show(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dismissProgress() {
        try {
            if (ad != null) {
                ad.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }






}
