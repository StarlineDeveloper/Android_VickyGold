package com.VijayAssayCenter.activity;

import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import java.text.DecimalFormat;

public class CoinOrderConfirm extends AppCompatActivity {


    ImageView iv_close;

    TextView tv_call;

    TextView tv_msg;
    TextView tv_deal_no;
    TextView symob_name;
    TextView tv_net_amount;
    TextView tv_q;
    TextView tv_rate;
    String Lable="";

    float volume;
    float NetAmount;

    TextView view_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_order_confirm);

        tv_msg=findViewById(R.id.tv_msg);
        tv_deal_no=findViewById(R.id.tv_deal_no);
        symob_name=findViewById(R.id.symob_name);
        tv_q=findViewById(R.id.tv_q);
        tv_rate=findViewById(R.id.tv_rate);
        tv_net_amount=findViewById(R.id.tv_net_amount);



        tv_msg.setText(Constants.Messsage);
        tv_deal_no.setText("Order No : "+Constants.DealNo);
        symob_name.setText("Product Name : "+Constants.ProductionName);

        if(Constants.Sorces.equals("Gold")){
            Lable="";
        }else {
            Lable="";
        }



        DecimalFormat threevalue = new DecimalFormat("0.000");
        float valueis= Float.parseFloat(Constants.Volume);
        String formatted = threevalue.format(valueis);


        tv_q.setText("Quantity : "+formatted+" "+Lable);
        tv_rate.setText("Rate : ₹ "+Constants.OrderRate);


        volume= Float.parseFloat(Constants.Volume);

        NetAmount= Float.parseFloat(Constants.Total);

        DecimalFormat threevalue55 = new DecimalFormat("0.00");
        String formattedee = threevalue55.format(NetAmount);

        tv_net_amount.setText("Net Amount: ₹ "+""+formattedee);





        view_order=findViewById(R.id.view_order);
        view_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.page=3;
                Constants.Pgaeno=0;
                Intent intent=new Intent(CoinOrderConfirm.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_close=findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_call=findViewById(R.id.tv_call);
        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String BookingNo1= Common.getPreferenceString(CoinOrderConfirm.this, "BookingNo1", "");

                if(BookingNo1.equals("")){

                }else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + BookingNo1));
                    startActivity(callIntent);
                }


            }
        });

    }
}