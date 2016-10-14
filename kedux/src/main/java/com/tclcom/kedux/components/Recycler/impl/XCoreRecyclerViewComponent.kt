package com.tclcom.kedux.components.Recycler.impl

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.tclcom.kedux.R
import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.kedux.components.Recycler.item.XCoreItemUIComponent
import com.tclcom.kedux.components.XCoreUIBaseComponent
import com.tclcom.kedux.core.Store
import org.jetbrains.anko.find

class XCoreRecyclerViewComponent: XCoreUIBaseComponent, Store.ISingleStateChangeListener {

    var swipeRefreshLayout: SwipeRefreshLayout? = null
        private set

    var recyclerView: RecyclerView? = null
        private set
    var mLayoutManager: RecyclerView.LayoutManager? = null
        private set
    var xCoreRecyclerAdapter: XCoreRecyclerAdapter? = null
        private set


    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override val layoutResId: Int
        get() = R.layout.xcore_recyclerview_component

    override fun onViewCreated(view: View) {
        //初始化View
        swipeRefreshLayout = find(R.id.xcore_refresh_layout)
        swipeRefreshLayout?.apply {
            isEnabled = false
        }
        recyclerView = find(R.id.xcore_rv)
        mLayoutManager = LinearLayoutManager(context)
        xCoreRecyclerAdapter = XCoreRecyclerAdapter(this)
        //初始化RecyclerView
        recyclerView?.apply {
            layoutManager = mLayoutManager
            adapter = xCoreRecyclerAdapter
        }

    }

    /**
     * 对外提供item组件的注册

     * @param xCoreItemUIComponent
     * *
     * @return
     */
    fun registerItemComponent(xCoreItemUIComponent: XCoreItemUIComponent) {
        xCoreRecyclerAdapter?.registerItemUIComponent(xCoreItemUIComponent)
    }

    fun setRefreshEnable(enable: Boolean) {
        swipeRefreshLayout?.isEnabled = enable
    }

    /**
     * 当状态发生变化时,自动通知

     * @param state
     */
    override fun onStateChanged(state: Any?) {
        if (state != null && state is XCoreRecyclerAdapter.IDataItems?) {
            xCoreRecyclerAdapter?.dataSet = state.items
            xCoreRecyclerAdapter?.notifyDataSetChanged()
        }
    }
}

