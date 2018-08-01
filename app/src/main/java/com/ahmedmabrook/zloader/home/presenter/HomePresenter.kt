package com.ahmedmabrook.zloader.home.presenter

import android.content.Context
import android.net.Uri
import com.ahmedmabrook.zloader.R
import com.ahmedmabrook.zloader.home.view.HomeActivity
import com.ahmedmabrook.zloader.home.HomeContract
import com.ahmedmabrook.zloader.model.ItemList
import com.ahmedmabrook.zloaderlibrary.request.generic.ContentType
import com.ahmedmabrook.zloaderlibrary.request.generic.Zrequest
import com.ahmedmabrook.zloaderlibrary.request.generic.RequestType
import com.ahmedmabrook.zloaderlibrary.request.generic.listeners.OnResponseListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

class HomePresenter(val context: Context): HomeContract.HomePresenter, OnResponseListener {
    val  homeView: HomeContract.HomeView = context as HomeActivity
    var items: List<ItemList>  = listOf()

    private val BASE_URL = "http://pastebin.com/raw"
    private val END_POINT = Uri.parse("wgkJgazE").toString()
    lateinit var zrequest: Zrequest


    override fun init() {
        homeView.init()
        zrequest = Zrequest.getInstance(context)
                .baseUrl(BASE_URL)
                .endpoint(END_POINT)
                .requestType(RequestType.GET)
                .contentType(ContentType.JSON)
                .onNetworkRequestResponseListener(this)
    }

    override fun onSuccessResponse(response: String?, contentType: ContentType?, isCached: Boolean) {
        if (contentType === ContentType.JSON) {
            try {
                val menuItemJsonArray = JSONArray(response)
                val menuItemListType = object : TypeToken<List<ItemList>>() {

                }.type
                val gson = Gson()
                val itemLists = gson.fromJson<List<ItemList>>(menuItemJsonArray.toString(), menuItemListType)
                items = itemLists
                homeView.showList(itemLists)

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    override fun onErrorResponse(error: String?, message: String?, code: Int) {
        homeView.showError(error?:context.getString(R.string.generic_error_message))
    }

    override fun loadList() {
        zrequest.fireRequest()
    }

}