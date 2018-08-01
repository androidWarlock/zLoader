package com.ahmedmabrook.zloaderlibrary.gadgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import  com.ahmedmabrook.zloaderlibrary.request.image.*;

import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnCompleteImageListener;
import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnImageBitmapListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class ZimageView extends AppCompatImageView {
    private static final int LOADING_THREADS = 5;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
    private ImageTask currentTask;
    private ImageCache imageCache;


    public ZimageView(Context context) {
        super(context);
        imageCache = ImageCache.getInstance();
    }

    public ZimageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageCache = ImageCache.getInstance();
    }

    public ZimageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        imageCache = ImageCache.getInstance();
    }

    public void setImageUrl(String url) {
        setImage(new ZimageRequest(url));
    }

    public void setImageUrl(String url, OnCompleteImageListener onCompleteImageListener) {
        setImage(new ZimageRequest(url), onCompleteImageListener);
    }

    public void setImageUrl(String url, final Integer fallbackResource) {
        setImage(new ZimageRequest(url), fallbackResource);
    }

    public void setImageUrl(String url, final Integer fallbackResource, OnCompleteImageListener onCompleteImageListener) {
        setImage(new ZimageRequest(url), fallbackResource, onCompleteImageListener);
    }

    public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource) {
        setImage(new ZimageRequest(url), fallbackResource, loadingResource);
    }

    public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource, OnCompleteImageListener onCompleteImageListener) {
        setImage(new ZimageRequest(url), fallbackResource, loadingResource, onCompleteImageListener);
    }

    public void setImage(final OnImageBitmapListener image) {
        setImage(image, null, null, null);
    }

    public void setImage(final OnImageBitmapListener image, final OnCompleteImageListener onCompleteImageListener) {
        setImage(image, null, null, onCompleteImageListener);
    }

    public void setImage(final OnImageBitmapListener image, final Integer fallbackResource) {
        setImage(image, fallbackResource, fallbackResource, null);
    }

    public void setImage(final OnImageBitmapListener image, final Integer fallbackResource, OnCompleteImageListener onCompleteImageListener) {
        setImage(image, fallbackResource, fallbackResource, onCompleteImageListener);
    }

    public void setImage(final OnImageBitmapListener image, final Integer fallbackResource, final Integer loadingResource) {
        setImage(image, fallbackResource, loadingResource, null);
    }

    public void setImage(final OnImageBitmapListener image, final Integer fallbackResource, final Integer loadingResource, final OnCompleteImageListener onCompleteImageListener) {
        if (loadingResource != null) {
            setImageResource(loadingResource);
        }
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
        currentTask = new ImageTask(getContext(), image);
        currentTask.setOnImageLoadedHandler(new OnImageLoadedHandler() {
            @Override
            public void onComplete(Bitmap bitmap) {
                if (bitmap != null) {
                    setImageBitmap(bitmap);
                } else {
                    if (fallbackResource != null) {
                        setImageResource(fallbackResource);
                    }
                }
                if (onCompleteImageListener != null) {
                    onCompleteImageListener.onComplete(bitmap);
                }
            }
        });
        threadPool.execute(currentTask);
    }

    public void clearCache() {
        imageCache.clearImageCache();
    }

    public void removeImage(String cacheKey) {
        imageCache.removeImage(cacheKey);
    }

    public void cancelAllTasks() {
        threadPool.shutdownNow();
        threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
    }
}
