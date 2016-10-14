package com.tclcom.oneshop.MovieDemo.model.wrapper

import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.oneshop.MovieDemo.components.item.TextItemComponent
import com.tclcom.oneshop.MovieDemo.model.Title

class TitleWrapper : XCoreRecyclerAdapter.IDataWrapper {

    var title: Title? = null

    override val viewType: String
        get() = TextItemComponent::class.java.simpleName

    val categoryTitle: String?
        get() = title?.categoryTitle

    override fun toString(): String {
        return "TitleWrapper{" +
                "title=" + title +
                '}'
    }
}
