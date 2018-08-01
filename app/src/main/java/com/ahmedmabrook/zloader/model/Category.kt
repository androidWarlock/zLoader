package com.ahmedmabrook.zloader.model

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

data class Category(
        var id: Int = 0,
        var title: String = "",
        var photoCount: Int = 0,
        var links: Links? = null)
