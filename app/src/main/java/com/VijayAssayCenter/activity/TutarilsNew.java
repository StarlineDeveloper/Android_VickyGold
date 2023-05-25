package com.VijayAssayCenter.activity;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;

public class TutarilsNew extends BaseActivity {

    WebView myWebView;
    WebView mWebView10;

    LinearLayout lay_back;

    ImageView iv_video1;
    ImageView iv_video2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutarils_new);

        myWebView=findViewById(R.id.mWebView);

        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        String url1 = "https://i.ytimg.com/vi/";
        String url2 = "/hqdefault.jpg";

        String imageurlfinal = url1 + "1fhAph4PgIbp5xN72jlWESUt7q8DeE2gI" + url2;
        Log.e("imageurlfinal",imageurlfinal);
        iv_video1=findViewById(R.id.iv_video1);
        iv_video2=findViewById(R.id.iv_video2);
        iv_video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1rLJ9FLSdJmB8UnQrppsWwc2V3Xy8J0B2");
            }
        });

        iv_video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1fhAph4PgIbp5xN72jlWESUt7q8DeE2gI");
            }
        });

    }
}