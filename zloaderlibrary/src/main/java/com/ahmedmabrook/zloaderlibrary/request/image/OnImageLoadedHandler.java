package com.ahmedmabrook.zloaderlibrary.request.image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */
public class OnImageLoadedHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        Bitmap bitmap = (Bitmap) msg.obj;
        onComplete(bitmap);
    }

    public void onComplete(Bitmap bitmap) {

    }
}