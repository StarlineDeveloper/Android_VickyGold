package com.VijayAssayCenter.activity;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.VijayAssayCenter.R;

public class HelpPage extends AppCompatActivity {

    LinearLayout lay_back;

    LinearLayout lay_1;
    LinearLayout lay_2;
    LinearLayout lay_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);


        lay_1=findViewById(R.id.lay_1);
        lay_2=findViewById(R.id.lay_2);
        lay_3=findViewById(R.id.lay_3);

        String helpcheck = getIntent().getStringExtra("help");
        if(helpcheck.equals("1")){
            lay_1.setVisibility(View.VISIBLE);

            lay_2.setVisibility(View.GONE);
            lay_3.setVisibility(View.GONE);
        }else if(helpcheck.equals("2")){
            lay_2.setVisibility(View.VISIBLE);

            lay_1.setVisibility(View.GONE);
            lay_3.setVisibility(View.GONE);

        }else {
            lay_3.setVisibility(View.VISIBLE);

            lay_1.setVisibility(View.GONE);
            lay_2.setVisibility(View.GONE);

        }


        lay_back=findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
