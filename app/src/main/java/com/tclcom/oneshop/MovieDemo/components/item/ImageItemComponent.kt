package com.tclcom.oneshop.MovieDemo.components.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tclcom.kedux.components.IXCoreComponent
import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.kedux.components.Recycler.item.XCoreItemUIComponent
import com.tclcom.kedux.eventbus.XCoreBus
import com.tclcom.kedux.helper.onClick
import com.tclcom.kedux.helper.xfrom
import com.tclcom.oneshop.R
import com.tclcom.oneshop.MovieDemo.actions.MovieActionCreator
import com.tclcom.oneshop.helper.UIBinderHelperImpl
import com.tclcom.oneshop.MovieDemo.model.wrapper.MovieWrapper

class ImageItemComponent : XCoreItemUIComponent() {

    private var uibinderHelper: UIBinderHelperImpl? = null

    private var movieWrapper: MovieWrapper? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.item_detail, container, false)
    }

    override fun onViewCreated(view: View) {
        uibinderHelper = UIBinderHelperImpl(view)
    }

    override val viewType: String
        get() = ImageItemComponent::class.java.simpleName

    override fun bindView(coreComponent: IXCoreComponent,
                          coreRecyclerAdapter: XCoreRecyclerAdapter,
                          data: XCoreRecyclerAdapter.IDataWrapper,
                          pos: Int) {
        movieWrapper = data as MovieWrapper
        movieWrapper?.let {
            uibinderHelper?.apply {
                from(R.id.item_content_tv).setText(it.bindContentText())
                from(R.id.item_pic_iv).setImageUrl(it.avatarUrl)
                from(R.id.item_title_tv).setText(it.bindItemTitle())
                xfrom(R.id.item_check_box) {
                    setButtonDrawable(if (it.isChecked) R.mipmap.checkbox_checked else R.mipmap.checkbox_normal)
                    onClick {
                        XCoreBus.instance.post(MovieActionCreator.checkBoxClick(movieWrapper!!))
                    }
                }
            }
        }

    }

    override fun onViewDetachedFromWindow() {

    }

}
