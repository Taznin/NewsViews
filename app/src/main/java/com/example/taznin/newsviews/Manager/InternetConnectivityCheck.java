package com.example.taznin.newsviews.Manager;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnectivityCheck {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                .getActiveNetworkInfo().isConnected());

        return connected;

    }
}
