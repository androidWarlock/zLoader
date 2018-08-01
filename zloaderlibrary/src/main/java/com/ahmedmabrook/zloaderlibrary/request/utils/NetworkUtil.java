package com.ahmedmabrook.zloaderlibrary.request.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
