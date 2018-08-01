package com.ahmedmabrook.zloaderlibrary.request.generic;

import android.util.LruCache;

import com.ahmedmabrook.zloaderlibrary.request.utils.CacheUtil;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

class ResponseCache {

    private static ResponseCache responseCache;
    private LruCache<String, String> responseLruCache;

    private ResponseCache() {
        responseCache();
    }

    static ResponseCache getInstance() {
        if (responseCache == null)
            responseCache = new ResponseCache();
        return responseCache;
    }

    private void responseCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        responseLruCache = new LruCache<>(cacheSize);
    }

    void addResponseToCache(String key, String response) {
        if (getResponseFromCache(key) == null) {
            responseLruCache.put(CacheUtil.getCacheKey(key), response);
        }
    }

    String getResponseFromCache(String key) {
        return responseLruCache.get(CacheUtil.getCacheKey(key));
    }

    void clearResponseCache() {
        responseLruCache.evictAll();
    }

    void removeResponse(String cacheKey) {
        responseLruCache.remove(CacheUtil.getCacheKey(cacheKey));
    }
}
