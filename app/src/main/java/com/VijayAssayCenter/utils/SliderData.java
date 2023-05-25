package com.VijayAssayCenter.utils;

import android.app.Activity;



public class SliderData {

    // image url is used to
    // store the url of image
    private String imgUrl;
    private String RedirectLink;

    // Constructor method.
    public SliderData(String imgUrl,String RedirectLink) {
        this.imgUrl = imgUrl;
        this.RedirectLink = RedirectLink;
    }


    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    public String getRedirect() {
        return RedirectLink;
    }


}
