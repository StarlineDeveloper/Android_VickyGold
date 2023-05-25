package com.VijayAssayCenter.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by jatinkintu on 15/12/17.
 */

public class MyTextViewBold extends TextView {

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/calibribold1.ttf");
            setTypeface(tf);
        }
    }

}
