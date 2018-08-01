package com.ahmedmabrook.zloaderlibrary.request.generic.listeners;

import com.ahmedmabrook.zloaderlibrary.request.generic.ContentType;
/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

public interface OnResponseListener {

    void onSuccessResponse(String response, ContentType contentType, boolean isCached);

    void onErrorResponse(String error, String message, int code);

}
