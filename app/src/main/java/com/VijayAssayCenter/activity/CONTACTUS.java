package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.BaseActivity.loadService;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
//importAlertDialog;
//importAppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.AsyncProgressDialog;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class CONTACTUS extends AppCompatActivity {

    LinearLayout lay_back;
    Socket mSocket;
    TextView tv_no_1;
    TextView tv_no_2;
    TextView tv_no_3;
    TextView tv_no_4;
    TextView tv_no_5;
    TextView tv_no_6;
    TextView tv_no_7;

    String regresponse="";


    TextView email_1;
    TextView email_2;

    AlertDialog dialogcommna;


    TextView go_address;

    String ObjVariable="";


    EditText et_name;
    EditText et_email;
    EditText et_phon_no;
    EditText et_company;
    EditText et_comments;
    LinearLayout lay_submit;
    AsyncProgressDialog ad;

    String Name="";
    String EmailId="";
    String PhoneNumber="";
    String Company="";
    String Comments="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_no_1=findViewById(R.id.tv_no_1);
        tv_no_2=findViewById(R.id.tv_no_2);
        tv_no_3=findViewById(R.id.tv_no_3);
        tv_no_4=findViewById(R.id.tv_no_4);
        tv_no_5=findViewById(R.id.tv_no_5);
        tv_no_6=findViewById(R.id.tv_no_6);
        tv_no_7=findViewById(R.id.tv_no_7);

        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_phon_no=findViewById(R.id.et_phon_no);
        et_company=findViewById(R.id.et_company);
        et_comments=findViewById(R.id.et_comments);
        lay_submit=findViewById(R.id.lay_submit);


        lay_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber=et_phon_no.getText().toString().trim();

                if (et_name.getText().toString().trim().length() == 0) {
                    et_name.setError("Name");
                    et_name.requestFocus();
                }else if(et_email.getText().toString().trim().length() == 0){
                    et_email.setError("Email Id");
                    et_email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches()) {
                    et_email.setError("Invalid email address");
                    et_email.requestFocus();
                }
                else if(!PhoneNumber.equals("") && et_phon_no.getText().toString().trim().length() < 10){
                        et_phon_no.setError("Invalid Phone Number");
                        et_phon_no.requestFocus();
                }
                else if(et_company.getText().toString().trim().length() == 0){
                    et_company.setError("Company");
                    et_company.requestFocus();
                }
                else if(et_comments.getText().toString().trim().length() == 0){
                    et_comments.setError("Comments");
                    et_comments.requestFocus();
                }else {

                     Name=et_name.getText().toString().trim();
                     EmailId=et_email.getText().toString().trim();
                     PhoneNumber=et_phon_no.getText().toString().trim();
                     Company=et_company.getText().toString().trim();
                     Comments=et_comments.getText().toString().trim();

                    ObjVariable="{\"Name\":\""+Name+"\"," +
                            "\"Email\":\""+EmailId+"\"," +
                            "\"Phone\":\""+PhoneNumber+"\"," +
                            "\"Sub\":\""+Company+"\"," +
                            "\"Client\":\"1\"," +
                            "\"Message\":\""+Comments+"\"}";

                     new InserFeedBack().execute();


                }
            }
        });

        email_1=findViewById(R.id.email_1);
        email_2=findViewById(R.id.email_2);

        go_address=findViewById(R.id.go_address);
        go_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com/maps/place/Vijay+Assay+Centre+Pvt.+Ltd/@22.5694236,88.3621777,18.22z/data=!4m8!1m2!2m1!1svikygold+jewellery!3m4!1s0x3a0277ab451ed623:0x82fa6c54f19abc9a!8m2!3d22.5690236!4d88.3619582";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        AsyncTaskExample asyncTask=new AsyncTaskExample();
        asyncTask.execute("");
    }


    private class AsyncTaskExample extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {

            return null;
        }
        @Override
        protected void onPostExecute(String result2) {
            super.onPostExecute(result2);


            Log.e("Diee","Clllllll");


            final String BookingNo1=Common.getPreferenceString(CONTACTUS.this, "BookingNo1", "");
            final String BookingNo2=Common.getPreferenceString(CONTACTUS.this, "BookingNo2", "");
            final String BookingNo3=Common.getPreferenceString(CONTACTUS.this, "BookingNo3", "");
            final String BookingNo4=Common.getPreferenceString(CONTACTUS.this, "BookingNo4", "");
            final String BookingNo5=Common.getPreferenceString(CONTACTUS.this, "BookingNo5", "");
            final String BookingNo6=Common.getPreferenceString(CONTACTUS.this, "BookingNo6", "");
            final String BookingNo7=Common.getPreferenceString(CONTACTUS.this, "BookingNo7", "");


            final String Email1=Common.getPreferenceString(CONTACTUS.this, "Email1", "");
            final String Email2=Common.getPreferenceString(CONTACTUS.this, "Email2", "");


            tv_no_1.setText(BookingNo1);
            tv_no_2.setText(BookingNo2);
            tv_no_3.setText(BookingNo3);
            tv_no_4.setText(BookingNo4);
            tv_no_5.setText(BookingNo5);
            tv_no_6.setText(BookingNo6);
            tv_no_7.setText(BookingNo7);

            email_1.setText("Email Id: "+Email1);
            email_2.setText("Email Id: "+Email2);


            if(Email1.equals("")|| Email1.equals("null")){
                email_1.setVisibility(View.GONE);
            }else {
                email_1.setVisibility(View.VISIBLE);

            }

            if(Email2.equals("")|| Email2.equals("null")){
                email_2.setVisibility(View.GONE);
            }else {
                email_2.setVisibility(View.VISIBLE);

            }



            if(BookingNo1.equals("")|| BookingNo1.equals("null")){
                tv_no_1.setVisibility(View.GONE);
            }else {
                tv_no_1.setVisibility(View.VISIBLE);

            }

            if(BookingNo2.equals("")|| BookingNo2.equals("null")){
                tv_no_2.setVisibility(View.GONE);
            }else {
                tv_no_2.setVisibility(View.VISIBLE);

            }

            if(BookingNo3.equals("")|| BookingNo3.equals("null")){
                tv_no_3.setVisibility(View.GONE);
            }else {
                tv_no_3.setVisibility(View.VISIBLE);

            }




            if(BookingNo4.equals("")|| BookingNo4.equals("null")){
                tv_no_4.setVisibility(View.GONE);
            }else {
                tv_no_4.setVisibility(View.VISIBLE);

            }

            if(BookingNo5.equals("")|| BookingNo5.equals("null")){
                tv_no_5.setVisibility(View.GONE);
            }else {
                tv_no_5.setVisibility(View.VISIBLE);

            }

            if(BookingNo6.equals("")|| BookingNo6.equals("null")){
                tv_no_6.setVisibility(View.GONE);
            }else {
                tv_no_6.setVisibility(View.VISIBLE);

            }

            if(BookingNo7.equals("")|| BookingNo7.equals("null")){
                tv_no_7.setVisibility(View.GONE);
            }else {
                tv_no_7.setVisibility(View.VISIBLE);

            }


            tv_no_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo1.replaceAll("[^0-9+]", ""));

                }
            });


            tv_no_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo2.replaceAll("[^0-9+]", ""));

                }
            });

            tv_no_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo3.replaceAll("[^0-9+]", ""));

                }
            });

            tv_no_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo4.replaceAll("[^0-9+]", ""));

                }
            });

            tv_no_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo5.replaceAll("[^0-9+]", ""));

                }
            });

            tv_no_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo6.replaceAll("[^0-9+]", ""));

                }
            });

            tv_no_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(CONTACTUS.this, BookingNo7.replaceAll("[^0-9+]", ""));

                }
            });


            email_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",Email1, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

            email_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",Email2, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });



        }

    }

    public static void call(Context context, String phone_no) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone_no));
        context.startActivity(callIntent);
    }


    private class InserFeedBack extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", Constants.Feedback);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadService(request, Constants.Feedback);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("RegisterRespoce", s);

                dismissProgress();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("RegisterRespoce", ""+s);
                        Log.e("Tessttstts",s);
                        if(s.equals("")){

                        }
                        else {
                            SuccesDialog("Success","Successful");
                            et_name.setText("");
                            et_email.setText("");
                            et_phon_no.setText("");
                            et_company.setText("");
                            et_comments.setText("");
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("re_response", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void showProgress(String msg) {

        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(CONTACTUS.this);
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

//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/WebService.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

    public void SuccesDialog(String Titile,String Message) {

        LayoutInflater li = LayoutInflater.from(CONTACTUS.this);

        final View confirmDialog = li.inflate(R.layout.dailog_success, null);

       AlertDialog.Builder alert = new AlertDialog.Builder(CONTACTUS.this);


        TextView tv_title=confirmDialog.findViewById(R.id.tv_title);
        TextView tv_message=confirmDialog.findViewById(R.id.tv_message);
        TextView tv_ok=confirmDialog.findViewById(R.id.tv_ok);


        tv_title.setText(Html.fromHtml(Titile));
        tv_message.setText(Html.fromHtml(Message));


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcommna.dismiss();

            }
        });

        alert.setView(confirmDialog);

        dialogcommna = alert.create();

        dialogcommna.show();

        dialogcommna.setCanceledOnTouchOutside(true);


    }

    public void DialogFaid(String Titile,String Message) {

        LayoutInflater li = LayoutInflater.from(CONTACTUS.this);

        final View confirmDialog = li.inflate(R.layout.dialog_filed, null);

       AlertDialog.Builder alert = new AlertDialog.Builder(CONTACTUS.this);


        TextView tv_title=confirmDialog.findViewById(R.id.tv_title);
        TextView tv_message=confirmDialog.findViewById(R.id.tv_message);
        TextView tv_ok=confirmDialog.findViewById(R.id.tv_ok);


        tv_title.setText(Html.fromHtml(Titile));
        tv_message.setText(Html.fromHtml(Message));





        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcommna.dismiss();

            }
        });

        alert.setView(confirmDialog);

        dialogcommna = alert.create();

        dialogcommna.show();

        dialogcommna.setCanceledOnTouchOutside(true);


    }

}
