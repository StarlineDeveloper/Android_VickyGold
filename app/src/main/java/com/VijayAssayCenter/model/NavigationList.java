package com.VijayAssayCenter.model;


import com.VijayAssayCenter.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by UV on 29-Nov-16.
 */
public class NavigationList {
    public static NavigationList _naviNavigationList;
    public static List<Integer> imageList;
    public static List<String> titleList;
    public static String location = "General Premium";
    public static String cartCount;

    public static NavigationList getInstance() {
        if (_naviNavigationList == null) {
            _naviNavigationList = new NavigationList();
        }
        imageList = Arrays.asList(R.drawable.ic_demo, R.drawable.ic_demo
                , R.drawable.ic_demo, R.drawable.ic_demo);
        titleList = Arrays.asList(location, "Updates List", "Marquee", "Logout");
        return _naviNavigationList;
    }

    public static List<Integer> getImageList() {
        return imageList;
    }

    public static List<String> getTitleList() {
        return titleList;
    }

    public static void changeLocation(String mylocation) {
        location = mylocation;
        titleList.set(0, mylocation);
    }
}
