package com.ahmedmabrook.zloaderlibrary.request.generic;


import android.content.Context;

import com.ahmedmabrook.zloaderlibrary.request.generic.listeners.OnResponseListener;

import org.json.JSONObject;

import java.util.Map;

public class Zrequest {
    /**
     * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
     *
     */

    private static Zrequest zrequest;

    private Context context;
    private String baseUrl;
    private String endpoint;
    private boolean isDecodedUrl;
    private RequestType requestType;
    private ContentType contentType;
    private Map<String, String> params;
    private Map<String, String> headers;
    private JSONObject bodyJsonObject;
    private OnResponseListener onResponseListener;
    private final ResponseCache responseCache;
    private ZrequestAsyncTask zrequestAsyncTask;

    private Zrequest(Context context) {
        this.context = context;
        this.responseCache = ResponseCache.getInstance();
    }

    public static Zrequest getInstance(Context context) {
        if (zrequest == null)
            createInstance(context);
        return zrequest;
    }

    private synchronized static void createInstance(Context context) {
        if (zrequest == null)
            zrequest = new Zrequest(context);
    }

    public Zrequest baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return getInstance(context);
    }

    public Zrequest endpoint(String endpoint) {
        this.endpoint = endpoint;
        return getInstance(context);
    }

    public Zrequest decodedUrl(boolean decodedUrl) {
        isDecodedUrl = decodedUrl;
        return getInstance(context);
    }

    public Zrequest requestType(RequestType requestType) {
        this.requestType = requestType;
        return getInstance(context);
    }

    public Zrequest contentType(ContentType contentType) {
        this.contentType = contentType;
        return getInstance(context);
    }

    public Zrequest params(Map<String, String> params) {
        this.params = params;
        return getInstance(context);
    }

    public Zrequest headers(Map<String, String> headers) {
        this.headers = headers;
        return getInstance(context);
    }

    public Zrequest bodyJsonObject(JSONObject bodyJsonObject) {
        this.bodyJsonObject = bodyJsonObject;
        return getInstance(context);
    }

    public Zrequest onNetworkRequestResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
        return getInstance(context);
    }

    public void fireRequest() {
        zrequestAsyncTask = new ZrequestAsyncTask(context, baseUrl, endpoint, requestType, contentType,
                params, headers, bodyJsonObject);
        zrequestAsyncTask.setDecodedUrlInUTF(isDecodedUrl);
        zrequestAsyncTask.setOnResponseListener(onResponseListener);
        zrequestAsyncTask.execute();
    }

    public void clearResponseCache() {
        responseCache.clearResponseCache();
    }

    public void removeResponse(String cacheKey) {
        responseCache.removeResponse(cacheKey);
    }

    public void cancelRequest(boolean mayInterruptIfRunning) {
        if (zrequestAsyncTask != null)
            zrequestAsyncTask.cancel(mayInterruptIfRunning);
    }

}
