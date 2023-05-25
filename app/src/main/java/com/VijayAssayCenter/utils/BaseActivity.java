package com.VijayAssayCenter.utils;

import static com.VijayAssayCenter.utils.Constants.BaseUrl;
import static com.VijayAssayCenter.utils.Constants.BaseUrlTerminal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.VijayAssayCenter.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;


/**
 * Created by root on 21/4/16.
 */
public class BaseActivity extends AppCompatActivity {

    AsyncProgressDialog ad;
    private Toast toast;
    public Toolbar toolbar;
    TextView tv_title;
    ImageView iv_menu;

    AlertDialog dialogcommna;

    FrameLayout ivDrawer;


    public void bindToolbar() {
        ivDrawer = (FrameLayout) findViewById(R.id.ivDrawer);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);

    }

    public void setTitle(String titlea) {
        tv_title.setText(titlea + "");
    }

    public void showBack() {
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showEmptyOnly() {
        iv_menu.setVisibility(View.GONE);

    }

    public void showMenuOnly() {
        iv_menu.setImageResource(R.drawable.ic_settings);

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public void showToast(final String text, final int duration) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    public void showToast(final int text, final int duration) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(getResources().getString(text));
                toast.setDuration(duration);
                toast.show();
            }
        });

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

    public BaseActivity getActivity() {
        return this;
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

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
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
    public static String loadServiceTerminal(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(BaseUrlTerminal);
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }

    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(BaseUrl);
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }
    public static  double decimalFormat(double number){
        DecimalFormat dtime = new DecimalFormat("#.##");
       number=  Double.valueOf(dtime.format(number));

        return number;
    }
    public static  double checkIsInteger(double number){
        if (number % 1 != 0)
        {
            System.out.print("Decimal");
        }
        else
        {

            System.out.print ("Integer");
        }

        return number;
    }
    public void SuccesDialog(String Titile, String Message) {

        LayoutInflater li = LayoutInflater.from(getActivity());

        final View confirmDialog = li.inflate(R.layout.dailog_success, null);

       AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


        TextView tv_title = confirmDialog.findViewById(R.id.tv_title);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        TextView tv_ok = confirmDialog.findViewById(R.id.tv_ok);


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

    public void DialogFaid(String Titile, String Message) {

        LayoutInflater li = LayoutInflater.from(getActivity());

        final View confirmDialog = li.inflate(R.layout.dialog_filed, null);

       AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


        TextView tv_title = confirmDialog.findViewById(R.id.tv_title);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        TextView tv_ok = confirmDialog.findViewById(R.id.tv_ok);


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
