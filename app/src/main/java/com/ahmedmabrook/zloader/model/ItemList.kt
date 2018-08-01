package com.ahmedmabrook.zloader.model

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

class ItemList(

        var id: String? = null,
        var created_at: String? = null,
        var width: Int = 0,
        var height: Int = 0,
        var color: String? = null,
        var likes: Int = 0,
        var isLikedByUser: Boolean = false,
        var user: User? = null,
        var urls: Urls? = null,
        var categories: List<Category>? = null,
        var links: Links? = null

)
