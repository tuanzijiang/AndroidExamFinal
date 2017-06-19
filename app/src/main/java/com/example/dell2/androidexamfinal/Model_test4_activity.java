package com.example.dell2.androidexamfinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;

import collector.BaseActivity;

/**
 * Created by wangyan on 2017/6/19.
 */

public class Model_test4_activity extends BaseActivity{
    private WebView test4_webview;
    private LocationManager locationManager;
    private String locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_model_test4);
        init();
    }

    public void init() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            test4_webview=(WebView)findViewById(R.id.test4_webview);
            test4_webview.getSettings().setJavaScriptEnabled(true);
//        test4_webview.addJavascriptInterface(new JsInteraction(),"control");
            test4_webview.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            test4_webview.loadUrl("file:///android_asset/firstTest.html?121.48&31.23");
//            test4_webview.loadUrl("http://ditu.amap.com/");
            return;
        }
        //获取Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        Toast.makeText(Model_test4_activity.this,String.valueOf(location.getLatitude()),Toast.LENGTH_SHORT).show();
        String longitude=String.valueOf(location.getLongitude());
        String latitude=String.valueOf(location.getLatitude());

        test4_webview=(WebView)findViewById(R.id.test4_webview);
        test4_webview.getSettings().setJavaScriptEnabled(true);
//        test4_webview.addJavascriptInterface(new JsInteraction(),"control");
        test4_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        test4_webview.loadUrl("file:///android_asset/firstTest.html?"+longitude+"&"+latitude);
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Model_test4_activity.class);
        context.startActivity(intent);
    }
}
