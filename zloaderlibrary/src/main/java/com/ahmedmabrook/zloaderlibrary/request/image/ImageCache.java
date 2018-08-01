package com.ahmedmabrook.zloaderlibrary.request.image;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.ahmedmabrook.zloaderlibrary.request.utils.CacheUtil;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public class ImageCache {

    private static ImageCache imageCache;
    private LruCache<String, Bitmap> imageLruCache;

    private ImageCache() {
        imageMemoryCache();
    }

    public static ImageCache getInstance() {
        if (imageCache == null)
            imageCache = new ImageCache();
        return imageCache;
    }

    private void imageMemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        imageLruCache = new LruCache<>(cacheSize);
    }

    void addImageToCache(String key, Bitmap bitmap) {
        if (getImageFromCache(key) == null) {
            imageLruCache.put(CacheUtil.getCacheKey(key), bitmap);
        }
    }

    Bitmap getImageFromCache(String key) {
        return imageLruCache.get(CacheUtil.getCacheKey(key));
    }

    public void clearImageCache() {
        imageLruCache.evictAll();
    }

    public void removeImage(String cacheKey) {
        imageLruCache.remove(CacheUtil.getCacheKey(cacheKey));
    }

}
