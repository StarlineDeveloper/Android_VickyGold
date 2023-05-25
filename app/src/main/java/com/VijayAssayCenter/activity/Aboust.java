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

public class Aboust extends BaseActivity {


    WebView myWebView;
    WebView mWebView10;

    LinearLayout lay_back;

    private String video_url="https://drive.google.com/open?id=1ThYBj7gG3cwSxYvpbcq-Bu44ezXrn6CK";
    ImageView iv_video1;
    ImageView iv_video2;
    ImageView iv_video3;
    ImageView iv_video4;
    ImageView iv_video5;
    ImageView iv_video6;
    ImageView iv_video7;
    ImageView iv_video8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboust);


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
        iv_video3=findViewById(R.id.iv_video3);
        iv_video4=findViewById(R.id.iv_video4);
        iv_video5=findViewById(R.id.iv_video5);
        iv_video6=findViewById(R.id.iv_video6);
        iv_video7=findViewById(R.id.iv_video7);
        iv_video8=findViewById(R.id.iv_video8);

        iv_video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1NY674CTFrP6SUxXvP4ac50iLnvaFfS7S");
            }
        });

        iv_video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1FepL8q6lcGn4oUhFN5MX5rz_PzUQ85sN");
            }
        });

        iv_video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1217eG9AIQouqtLxPIdv879UzFDotDGJ0");
            }
        });

        iv_video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/10IbTuN3JeoukNcOHU4LBRYZqlz7JhmoA");
            }
        });

        iv_video5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1d0szpyT5DbQqY9VOoFsOWkX-2xymnOsX");
            }
        });

        iv_video6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1tD-h1DiVCjZGkE9pv_S9QbbfAfcxzEpp");

            }
        });

        iv_video7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1gK8fj7Ms_RyoWX0NV3hygwAT1pQ15Uhn");
            }
        });

        iv_video8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.loadUrl("https://drive.google.com/file/d/1sWZZeLQkrL3-ufX2gx3ohRjWO9dcPc0X");
            }
        });
    }
}