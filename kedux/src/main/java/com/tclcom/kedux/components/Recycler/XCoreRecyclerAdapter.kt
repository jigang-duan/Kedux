package com.tclcom.kedux.components.Recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tclcom.kedux.components.IXCoreComponent
import com.tclcom.kedux.components.Recycler.item.XCoreItemUIComponent


class XCoreRecyclerAdapter : RecyclerView.Adapter<XCoreRecyclerAdapter.CommonViewHolder> {

    private val mIXCoreComponent: IXCoreComponent //外层UI组件
    /**
     * 获取Adapter的数据源

     * @return
     */
    /**
     * 设置Adapter的数据源

     * @param dataSet
     */
    var dataSet: List<IDataWrapper>? = arrayListOf()
        set(dataSet) {
            field = dataSet
            notifyDataSetChanged()
        }//数据源

    private val mConfigurationSparseArray = SparseArray<XCoreItemUIComponent>()//集合:type对应的Item组件
    private val mViewTypeMap = hashMapOf<String, Int>()//type的string和int映射

    constructor(mIXCoreComponent: IXCoreComponent) {
        this.mIXCoreComponent = mIXCoreComponent
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): CommonViewHolder {
        //根据数据类型获取对应的item组件
        val xcoreItemUIComponent = mConfigurationSparseArray.get(type) ?: //如果未获取到,展示空item
                return getDefaultViewHolder(viewGroup.context)
        try {
            //创建一个新的Item组件
            val realItem = xcoreItemUIComponent.javaClass.newInstance()
            //使用item组件创建一个新的View
            val view = realItem.onCreateView(LayoutInflater.from(viewGroup.context), viewGroup)

            //使用View构建内部的ViewHolder
            val commonViewHolder = CommonViewHolder(view)
            //将创建的View设置到真是的Item组件中
            realItem.itemView = view

            //使用内部ViewHolder
            commonViewHolder.xCoreItemUIComponent = realItem
            return commonViewHolder
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        return getDefaultViewHolder(viewGroup.context)
    }

    override fun onBindViewHolder(baseViewHolder: CommonViewHolder, pos: Int) {
        baseViewHolder.bindView(mIXCoreComponent, this, dataSet!![pos], pos)
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataSet!![position]
        val integer = mViewTypeMap[item.viewType] ?: return -1
        return integer
    }

    override fun getItemCount(): Int {
        if (dataSet != null) {
            return dataSet!!.size
        }
        return 0
    }

    /**
     * get the error view holder

     * @param context
     * *
     * @return
     */
    protected fun getDefaultViewHolder(context: Context): CommonViewHolder {
        return CommonViewHolder(View(context))
    }

    /**
     * get the unique int type

     * @param name
     * *
     * @return
     */
    private fun getUniqueIntType(name: String): Int {
        if (TextUtils.isEmpty(name)) {
            return -1
        }
        var type = name.hashCode()
        while (true) {
            val old = mConfigurationSparseArray.get(type)
            if (old != null) {
                val oldName = old.viewType
                if (name != oldName) {
                    type = type + 1
                } else {
                    return type
                }
            } else {
                return type
            }
        }
    }

    /**
     * 注册Item组件

     * @param XCoreItemUIComponent
     * *
     * @return
     */
    fun registerItemUIComponent(XCoreItemUIComponent: XCoreItemUIComponent?): XCoreRecyclerAdapter {
        if (XCoreItemUIComponent == null || TextUtils.isEmpty(XCoreItemUIComponent.viewType)) {
            return this
        }
        val viewTypeInt = getUniqueIntType(XCoreItemUIComponent.viewType)
        mViewTypeMap.put(XCoreItemUIComponent.viewType, viewTypeInt)
        mConfigurationSparseArray.put(viewTypeInt, XCoreItemUIComponent)
        return this
    }

    /**
     * 注销配置

     * @param XCoreItemUIComponent
     * *
     * @return
     */
    fun unregisterItemUIComponent(XCoreItemUIComponent: XCoreItemUIComponent?): XCoreRecyclerAdapter {
        if (XCoreItemUIComponent == null || TextUtils.isEmpty(XCoreItemUIComponent.viewType)) {
            return this
        }
        val index = mConfigurationSparseArray.indexOfValue(XCoreItemUIComponent)
        if (index == -1) {
            return this
        }
        mConfigurationSparseArray.remove(index)
        return this
    }

    /**
     * 数据源必须实现的接口
     */
    interface IDataWrapper {
        val viewType: String
    }

    interface IDataItems {
        val items: List<IDataWrapper>
    }

    /**
     * 使用CommonViewHolder代理XCoreItemUIComponent组件
     */
    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var xCoreItemUIComponent: XCoreItemUIComponent? = null

        fun bindView(mIXCoreComponent: IXCoreComponent,
                     XCoreRecyclerAdapter: XCoreRecyclerAdapter,
                     data: IDataWrapper,
                     pos: Int) {
            xCoreItemUIComponent?.bindView(mIXCoreComponent, XCoreRecyclerAdapter, data, pos)
        }

        fun onViewDetachedFromWindow() {
            xCoreItemUIComponent?.onViewDetachedFromWindow()
        }
    }

    override fun onViewDetachedFromWindow(holder: CommonViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        holder?.onViewDetachedFromWindow()
    }
}
