package com.ahmedmabrook.zloader.custom.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

class SimpleItemDecoration(private val mSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.bottom = mSpace

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace
    }
}