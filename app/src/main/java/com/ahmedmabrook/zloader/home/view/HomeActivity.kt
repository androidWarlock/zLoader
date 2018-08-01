package com.ahmedmabrook.zloader.home.view

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.ahmedmabrook.zloader.R
import com.ahmedmabrook.zloader.model.ItemList
import com.ahmedmabrook.zloader.base.view.BaseActivity
import com.ahmedmabrook.zloader.custom.view.SimpleItemDecoration
import com.ahmedmabrook.zloader.home.HomeContract
import com.ahmedmabrook.zloader.home.presenter.HomePresenter
import com.ahmedmabrook.zloader.home.view.listeners.OnLoadMoreListener
import com.ahmedmabrook.zloader.home.view.listeners.OnMainAdapterClickListener
import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnCompleteImageListener
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

class HomeActivity : BaseActivity() , HomeContract.HomeView , SwipeRefreshLayout.OnRefreshListener ,
        OnCompleteImageListener, OnMainAdapterClickListener, OnLoadMoreListener {

    val  homePresneter: HomeContract.HomePresenter = HomePresenter(this)
    private var adapter: HomeAdapter? = null
    private var items: MutableList<ItemList> = mutableListOf<ItemList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        startLoading()

        homePresneter.init()

        homePresneter.loadList()


    }

    open fun initView(){

        swipe_to_refresh.setColorSchemeResources(R.color.loader_green,
                R.color.loader_blue, R.color.loader_red, R.color.loader_orange)
        swipe_to_refresh.setOnRefreshListener(this)

        recycler_view.setHasFixedSize(true)
        val glm = GridLayoutManager(this, 2)
        recycler_view.setLayoutManager(glm)


        adapter = HomeAdapter(this, items.toMutableList(), recycler_view, this)
        adapter?.setOnMainAdapterClickListener(this)
        adapter?.setOnLoadMoreListener(this)
        recycler_view.setAdapter(adapter)
        val decoration = SimpleItemDecoration(16)
        recycler_view.addItemDecoration(decoration)

        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter?.getItemViewType(position)) {
                    HomeAdapter.VIEW_TYPE_ITEM -> return 1
                    HomeAdapter.VIEW_TYPE_LOADING -> return 2 //number of columns of the grid
                    else -> return -1
                }
            }
        }

    }

    override fun onRefresh() {
        adapter?.setLoaded(false)
        adapter?.clear()
        if (homePresneter != null)
            homePresneter.loadList()
    }

    override fun showList(items: List<ItemList>) {
        this.items = items.toMutableList()
        adapter?.setItems(items)
        stopLoading()
    }

    override fun showError(error: String) {
        swipe_to_refresh.setRefreshing(false)
    }

    override fun onLoadMore(isLoading: Boolean) {
        if (!isLoading) {
          //TODO: here you can load more data
        }
    }

    override fun onComplete(bitmap: Bitmap?) {
    }

    override fun onMainAdapterClickListener(itemList: ItemList, view: View) {
    }

    override fun init() {
    }

    private fun startLoading() {
        swipe_to_refresh.post(Runnable { swipe_to_refresh.setRefreshing(true) })
    }


    private fun stopLoading() {
        swipe_to_refresh.post(Runnable { swipe_to_refresh.setRefreshing(false) })
    }


}
