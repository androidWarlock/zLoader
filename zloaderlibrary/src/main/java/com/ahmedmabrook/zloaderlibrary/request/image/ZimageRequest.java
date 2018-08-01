package com.ahmedmabrook.zloaderlibrary.request.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnImageBitmapListener;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class ZimageRequest implements OnImageBitmapListener {

    private static final int CONNECT_TIMEOUT = 50000;
    private static final int READ_TIMEOUT = 10000;

    private String imageUrl;

    public ZimageRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public Bitmap getBitmap(Context context) {
        ImageCache imageCache = ImageCache.getInstance();
        Bitmap bitmap = null;
        if (imageUrl != null) {
            bitmap = imageCache.getImageFromCache(imageUrl);
            if (bitmap == null) {
                bitmap = getBitmapFromUrl(imageUrl);
                if (bitmap != null) {
                    imageCache.addImageToCache(imageUrl, bitmap);
                }
            }
        }
        return bitmap;
    }
}
