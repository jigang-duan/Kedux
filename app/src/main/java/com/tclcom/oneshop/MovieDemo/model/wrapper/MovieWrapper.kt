package com.tclcom.oneshop.MovieDemo.model.wrapper

import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.oneshop.R
import com.tclcom.oneshop.MovieDemo.components.item.ImageItemComponent
import com.tclcom.oneshop.MovieDemo.model.Movie

class MovieWrapper : XCoreRecyclerAdapter.IDataWrapper {

    var movie: Movie? = null

    var isChecked = false

    override val viewType: String
        get() = ImageItemComponent::class.java.simpleName

    fun bindContentText(): String {
        return String.format("主演:%s 导演:%s", movie?.actor, movie?.director)
    }

    fun bindItemTitle(): String {
        return String.format("片名: %s", movie?.name)
    }

    val avatarUrl: String?
        get() = movie?.avatarUrl

    override fun toString(): String {
        return "MovieWrapper{" +
                "movie=" + movie +
                '}'
    }

    val avatarResId: Int
        get() = R.drawable.contacts
}
