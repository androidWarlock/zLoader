package com.ahmedmabrook.zloaderlibrary.request.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnImageBitmapListener;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class ImageTask implements Runnable {

    private static final int BITMAP_READY = 0;

    private Context context;

    private boolean cancelled = false;

    private OnImageLoadedHandler onImageLoadedHandler;
    private OnImageBitmapListener onImageBitmapListener;


    public ImageTask(Context context, OnImageBitmapListener onImageBitmapListener) {
        this.onImageBitmapListener = onImageBitmapListener;
        this.context = context;
    }

    @Override
    public void run() {
        if (onImageBitmapListener != null) {
            complete(onImageBitmapListener.getBitmap(context));
            context = null;
        }
    }

    public void setOnImageLoadedHandler(OnImageLoadedHandler onImageLoadedHandler) {
        this.onImageLoadedHandler = onImageLoadedHandler;
    }

    public void cancel() {
        cancelled = true;
    }

    private void complete(Bitmap bitmap) {
        if (onImageLoadedHandler != null && !cancelled) {
            onImageLoadedHandler.sendMessage(onImageLoadedHandler.obtainMessage(BITMAP_READY, bitmap));
        }
    }


}