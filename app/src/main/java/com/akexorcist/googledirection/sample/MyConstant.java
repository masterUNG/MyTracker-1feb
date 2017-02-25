package com.akexorcist.googledirection.sample;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by masterUNG on 2/1/2017 AD.
 */

public class MyConstant {

    //Explicit
    private String urlDriver = "http://swiftcodingthai.com/joy2/get_data.php";
    private String urlRoutes = "http://swiftcodingthai.com/joy2/get_routes.php";
    private String urlGetDetail = "http://swiftcodingthai.com/joy2/getDetailWhereID.php";
    private LatLng thpLatLng = new LatLng(13.9038265, 100.5858786);

    public LatLng getThpLatLng() {
        return thpLatLng;
    }

    public String getUrlGetDetail() {
        return urlGetDetail;
    }

    public String getUrlRoutes() {
        return urlRoutes;
    }

    public String getUrlDriver() {
        return urlDriver;
    }
}   // MainClass
