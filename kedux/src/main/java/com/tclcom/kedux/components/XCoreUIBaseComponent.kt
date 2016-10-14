package com.tclcom.kedux.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.tclcom.kedux.eventbus.XCoreBus

abstract class XCoreUIBaseComponent : FrameLayout, IXCoreComponent {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    protected fun init() {
        XCoreBus.instance.registerComponent(this)
        onCreateView(null, this)
        onViewCreated(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?): View {
        val layoutId = layoutResId
        try {
            if (layoutId <= 0) {
                return showErrorView(layoutId)
            }
            View.inflate(context, layoutId, container)
        } catch (e: Throwable) {
            e.printStackTrace()
            showErrorView(layoutId)
        }

        return this
    }

    protected fun showErrorView(layoutId: Int): View {
        val textView = TextView(context)
        textView.text = String.format("inflate layout(%s) id failed.", layoutId)
        addView(textView)
        return this
    }

    /**
     * 布局的layout资源ID

     * @return
     */
    abstract val layoutResId: Int

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDestroy()
    }

    protected fun onDestroy() {
        XCoreBus.instance.unregisterComponent(this)
    }

}
