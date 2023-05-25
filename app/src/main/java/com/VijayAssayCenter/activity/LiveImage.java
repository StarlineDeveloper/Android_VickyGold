package com.VijayAssayCenter.activity;

import android.content.Intent;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.VijayAssayCenter.R;
//import com.squareup.picasso.Picasso;

public class LiveImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_image);


        ImageView image = findViewById(R.id.liveimg);
        Log.e("dsjkfhjdksbn", getIntent().getStringExtra("imgpath"));
//        Picasso.with(this).load(getIntent().getStringExtra("imgpath")).into(image);
    }

    public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)); //MainActivity
        finish();
    }
}