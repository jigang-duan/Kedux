package com.tclcom.oneshop.components.container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.tclcom.kedux.components.XCoreUIBaseComponent
import com.tclcom.oneshop.MovieDemo.Activitys.RecyclerViewActivity
import com.tclcom.oneshop.R
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity


class MainPageUIComponent : XCoreUIBaseComponent {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override val layoutResId: Int
        get() = R.layout.xcore_activity_main

    override fun onViewCreated(view: View) {
        find<TextView>(R.id.text_hello).onClick {
            context.startActivity<RecyclerViewActivity>()
        }
    }

}
