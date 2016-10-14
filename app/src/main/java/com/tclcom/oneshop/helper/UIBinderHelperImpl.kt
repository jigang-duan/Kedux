package com.tclcom.oneshop.helper

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tclcom.kedux.helper.XCoreUIBinderHelper

class UIBinderHelperImpl(view: View) : XCoreUIBinderHelper(view) {

    override fun setImageUrl(url: String?): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is ImageView && !TextUtils.isEmpty(url)) {
            Picasso.with(view.context).load(url).into(view)
        }
        return this
    }
}
