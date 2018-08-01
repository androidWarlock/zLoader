package com.ahmedmabrook.zloaderlibrary.request.utils;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class StringUtils {
    public static boolean stringIsNotEmpty(String string) {
        if (string != null && !string.equals("null")) {
            if (!string.trim().equals("")) {
                return true;
            }
        }
        return false;
    }
}
