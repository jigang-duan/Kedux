@file:Suppress("NOTHING_TO_INLINE")
package com.tclcom.oneshop.UIKit

import android.support.design.widget.Snackbar
import android.view.View

/**
* Created by jiangduan on 16/9/28.
*/


fun View.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val sb = Snackbar.make(this, text, duration)
    sb.f()
    sb.show()
}

fun View.longSnackbar(text: CharSequence, f: Snackbar.() -> Unit) = this.snackbar(text, Snackbar.LENGTH_SHORT, f)

var Snackbar.action: CharSequence
    get() = ""
    set(value) {
        this.setAction(value, null)
    }
