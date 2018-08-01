package com.ahmedmabrook.zloaderlibrary.request.utils;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class CacheUtil {
    public static String getCacheKey(String url) {
        if (url == null) {
            throw new RuntimeException("Null url passed in");
        } else {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
    }
}
