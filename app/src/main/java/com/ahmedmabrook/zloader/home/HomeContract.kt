package com.ahmedmabrook.zloader.home

import com.ahmedmabrook.zloader.model.ItemList

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

interface HomeContract {
    interface HomePresenter {
        open fun init()

        open fun loadList()
    }

    interface HomeView {
        open fun init()
        open fun showError(error: String)

        open fun showList(items: List<ItemList>)
    }
}