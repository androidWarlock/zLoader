package com.ahmedmabrook.zloader.home.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import com.ahmedmabrook.zloader.R
import com.ahmedmabrook.zloader.home.view.listeners.OnLoadMoreListener
import com.ahmedmabrook.zloader.home.view.listeners.OnMainAdapterClickListener
import com.ahmedmabrook.zloader.model.ItemList
import com.ahmedmabrook.zloaderlibrary.gadgets.ZimageView
import com.ahmedmabrook.zloaderlibrary.request.image.listeners.OnCompleteImageListener

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

internal class HomeAdapter(private val context: Context, private var items: MutableList<ItemList>, recyclerView: RecyclerView,
                           private val onCompleteImageListener: OnCompleteImageListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater: LayoutInflater
    private var lastPosition = -1
    private var onMainAdapterClickListener: OnMainAdapterClickListener? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null

    private var isLoading: Boolean = false
    private val visibleThreshold = 1
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    init {
        this.layoutInflater = LayoutInflater.from(context)
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView
                    .layoutManager as LinearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?,
                                        dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener!!.onLoadMore(true)
                        }
                        isLoading = true
                    } else {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener!!.onLoadMore(false)
                        }
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        if (viewType == VIEW_TYPE_ITEM) {
            viewHolder = ItemViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
        } else if (viewType == VIEW_TYPE_LOADING) {
            viewHolder = LoadingViewHolder(layoutInflater.inflate(R.layout.loading_progress_item, parent, false))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = items!![position]
            holder.userNameTextView.setText(item.user?.name)
            holder.userImageView.setImageUrl(item.user?.profile_image?.large, R.drawable.warning,
                    R.drawable.loading, onCompleteImageListener)
            setAnimation(holder.itemView, position)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    fun setItems(items: List<ItemList>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    fun clear() {
        items!!.clear()
        notifyDataSetChanged()
    }

    fun setOnMainAdapterClickListener(onMainAdapterClickListener: OnMainAdapterClickListener) {
        this.onMainAdapterClickListener = onMainAdapterClickListener
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun setLoaded(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    private inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var userImageView: ZimageView
        internal var userNameTextView: TextView

        init {

            userImageView = itemView.findViewById(R.id.user_image_iv) as ZimageView
            userNameTextView = itemView.findViewById(R.id.user_name_tv) as TextView

            itemView.setOnClickListener {
                if (onMainAdapterClickListener != null)
                    onMainAdapterClickListener!!.onMainAdapterClickListener(items!![adapterPosition],
                            userImageView)
            }
        }
    }

    private inner class LoadingViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.loading_progressBar) as ProgressBar
        }
    }

    companion object {

        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_LOADING = 1
    }
}
