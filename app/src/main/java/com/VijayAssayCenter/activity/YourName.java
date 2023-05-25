package com.VijayAssayCenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;

public class YourName extends BaseActivity {

    TextView tv_condition;

    EditText et_name;
    LinearLayout lay_next;
    LinearLayout lay_skip;
    String YourName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_name);

        tv_condition = (TextView) findViewById(R.id.tv_condition);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String red = "By proceeding, you agree to our";
        SpannableString redSpannable = new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
        builder.append(redSpannable);

        String white = "Terms & Conditions & Privacy Policy. ";
        SpannableString whiteSpannable = new SpannableString(white);
        whiteSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#009BE0")), 0, white.length(), 0);
        builder.append(whiteSpannable);

        String blue = "Standard operator charges may apply for SMS";
        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, blue.length(), 0);
        builder.append(blueSpannable);

        tv_condition.setText(builder, TextView.BufferType.SPANNABLE);

        et_name = findViewById(R.id.et_name);
        lay_next = findViewById(R.id.lay_next);
        lay_skip = findViewById(R.id.lay_skip);


        lay_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_name.getText().toString().trim().length() == 0) {
                    et_name.setError("Name");
                    et_name.requestFocus();
                } else {
                    if (isConnected()) {
                        YourName = et_name.getText().toString().trim();
                        Intent MainActivity = new Intent(YourName.this, StoreName.class);
                        MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(MainActivity);

                    } else {
                        Toast.makeText(YourName.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        lay_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainActivity = new Intent(YourName.this, MainActivity.class);
                MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MainActivity);
            }
        });

    }
}
