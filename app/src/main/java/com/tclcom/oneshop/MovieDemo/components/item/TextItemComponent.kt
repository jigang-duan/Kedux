package com.tclcom.oneshop.MovieDemo.components.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tclcom.kedux.components.IXCoreComponent
import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.kedux.components.Recycler.item.XCoreItemUIComponent
import com.tclcom.oneshop.R
import com.tclcom.oneshop.helper.UIBinderHelperImpl
import com.tclcom.oneshop.MovieDemo.model.wrapper.TitleWrapper

class TextItemComponent : XCoreItemUIComponent() {

    private var uibinderHelper: UIBinderHelperImpl? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.item_title, container, false)
    }

    override fun onViewCreated(view: View) {
        uibinderHelper = UIBinderHelperImpl(view)
    }

    override val viewType: String
        get() = TextItemComponent::class.java.simpleName

    override fun bindView(coreComponent: IXCoreComponent,
                          coreRecyclerAdapter: XCoreRecyclerAdapter,
                          data: XCoreRecyclerAdapter.IDataWrapper,
                          pos: Int) {
        val title = data as TitleWrapper
        uibinderHelper?.from(R.id.category_title_tv)?.setText(title.categoryTitle!!)
    }

    override fun onViewDetachedFromWindow() {

    }
}
