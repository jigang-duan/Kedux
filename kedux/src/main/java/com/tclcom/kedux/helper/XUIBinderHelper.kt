@file:Suppress("NOTHING_TO_INLINE")

package com.tclcom.kedux.helper

import android.widget.CompoundButton


/**
* Created by jiangduan on 16/10/13.
*/

inline fun XCoreUIBinderHelper.xuibinder(block: XCoreUIBinderHelper.() -> Unit) {
    block();
}

inline fun XCoreUIBinderHelper.xfrom(viewId: Int, block: XCoreUIBinderHelper.() -> Unit) {
    this.from(viewId).block()
}


fun XCoreUIBinderHelper.onClick(l: (v: android.view.View?) -> Unit) {
    this.currentView.setOnClickListener(l)
}

fun XCoreUIBinderHelper.onLongClick(l: (v: android.view.View?) -> Boolean) {
    this.currentView.setOnLongClickListener(l)
}

fun XCoreUIBinderHelper.onCheckedChange(l: (buttonView: android.widget.CompoundButton?, isChecked: Boolean) -> Unit) {
    val view = this.currentView
    if (view is CompoundButton) {
        view.setOnCheckedChangeListener(l)
    }
}