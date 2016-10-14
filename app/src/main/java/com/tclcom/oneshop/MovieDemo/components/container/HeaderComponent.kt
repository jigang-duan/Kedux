package com.tclcom.oneshop.MovieDemo.components.container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.tclcom.kedux.components.XCoreUIBaseComponent
import com.tclcom.kedux.core.Store
import com.tclcom.kedux.eventbus.XCoreBus
import com.tclcom.kedux.helper.onClick
import com.tclcom.kedux.helper.xfrom
import com.tclcom.kedux.helper.xuibinder
import com.tclcom.oneshop.R
import com.tclcom.oneshop.MovieDemo.actions.MovieActionCreator
import com.tclcom.oneshop.helper.UIBinderHelperImpl
import com.tclcom.oneshop.MovieDemo.model.Title
import com.tclcom.oneshop.MovieDemo.reducers.MovieReducer

class HeaderComponent : XCoreUIBaseComponent, Store.ISingleStateChangeListener {

    private var uibinderHelper: UIBinderHelperImpl? = null
    private var count: Int = 0

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override val layoutResId: Int
        get() = R.layout.reycler_view_header

    override fun onViewCreated(view: View) {
        uibinderHelper = UIBinderHelperImpl(rootView)
        uibinderHelper?.xuibinder {
            xfrom(R.id.add_contacts) {
                onClick {
                    XCoreBus.instance.post(MovieActionCreator.fetchMovie(count))
                }
            }
            xfrom(R.id.add_title) {
                onClick {
                    XCoreBus.instance.post(MovieActionCreator.addCategory(Title("剧情")))
                }
            }
            xfrom(R.id.delete_last) {
                onClick {
                    XCoreBus.instance.post(MovieActionCreator.deleteLast())
                }
            }
        }
    }

    override fun onStateChanged(state: Any?) {
        if (state != null && state is MovieReducer.States) {

            uibinderHelper?.from(R.id.add_contacts)?.setEnabled(!state.isFetching)

            count = state.items.size
            uibinderHelper?.from(R.id.list_count_tv)?.setText(String.format("count = %s", state.items.size))
        }
    }
}
