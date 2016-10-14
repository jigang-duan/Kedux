package com.tclcom.kedux.components.Recycler.item

import android.view.View
import com.tclcom.kedux.components.IXCoreComponent
import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter

abstract class XCoreItemUIComponent : IXCoreComponent {

    /**
     * item组件支持的ViewType,与IDataComponent的getViewType对应

     * @return
     */
    abstract val viewType: String

    /**
     * 绑定数据-频繁回调

     * @param coreComponent       外出UI组件
     * *
     * @param coreRecyclerAdapter Adapter
     * *
     * @param data                对应的数据源
     * *
     * @param pos                 item所在列表的位置
     */
    abstract fun bindView(coreComponent: IXCoreComponent,
                          coreRecyclerAdapter: XCoreRecyclerAdapter,
                          data: XCoreRecyclerAdapter.IDataWrapper,
                          pos: Int)

    /**
     * item组件销毁时调用
     */
    open fun onViewDetachedFromWindow() {
        //TODO
    }

    //必须有无参数的构造函数
    var itemView: View? = null
        set(itemView) {
            field = itemView
            if (itemView != null) {
                onViewCreated(itemView)
            }
        }

}
