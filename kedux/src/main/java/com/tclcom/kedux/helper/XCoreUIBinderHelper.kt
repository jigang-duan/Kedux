package com.tclcom.kedux.helper

import android.graphics.Bitmap
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find


abstract class XCoreUIBinderHelper(val rootView: View) {

    private val mCacheView = SparseArray<View>()

    private var currentViewId = -1

    fun from(viewId: Int): XCoreUIBinderHelper {
        this.currentViewId = viewId
        mCacheView.put(viewId, rootView.findViewById(viewId))
        return this
    }

    fun end(): XCoreUIBinderHelper {
        this.currentViewId = -1
        return this
    }

    /**
     * 获取当前的View

     * @return
     */
    val currentView: View
        get() = ensureView()

    @JvmOverloads protected fun ensureView(viewId: Int = currentViewId): View {
        this.currentViewId = viewId
        var view: View? = mCacheView.get(viewId)
        if (view == null) {
            view = rootView.find(viewId)
        }
        if (view != null) {
            mCacheView.put(viewId, view)
        }
        if (view == null && currentViewId == -1) {
            return rootView
        }
        return view!!
    }

    fun setText(content: String): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is TextView) {
            if (!TextUtils.isEmpty(content)) {
                view.text = content
            } else if ("" == content) {
                view.text = ""
            }
        }
        return this
    }

    fun setVisibleOrInVisible(visibleOrInVisible: Boolean): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        view.visibility = if (visibleOrInVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    fun setVisibleOrGone(visibleOrGone: Boolean): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        view.visibility = if (visibleOrGone) View.VISIBLE else View.GONE
        return this
    }

    fun setVisibilityByEmpty(content: String): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        view.visibility = if (TextUtils.isEmpty(content)) View.GONE else View.VISIBLE
        return this
    }

    fun setEnabled(enable: Boolean): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        view.isEnabled = enable
        return this
    }

    fun setChecked(checked: Boolean): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is CompoundButton) {
            view.isChecked = checked
        }
        return this
    }

    fun setOnClickListener(onClickListener: View.OnClickListener?): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (onClickListener == null) {
            view.setOnClickListener(null)
        } else {
            view.setOnClickListener { v -> onClickListener.onClick(v) }
        }
        return this
    }

    fun setOnLongClickListener(onLongClickListener: View.OnLongClickListener?): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (onLongClickListener == null) {
            view.setOnClickListener(null)
        } else {
            view.setOnLongClickListener { view ->
                onLongClickListener.onLongClick(view)
                true
            }
        }
        return this
    }

    /**
     * setOnCheckedChangeListener for compoundButton

     * @param onCheckedChangeListener
     * *
     * @return
     */
    fun setOnCheckedChangeListener(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener?): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is CompoundButton) {
            view.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                onCheckedChangeListener?.onCheckedChanged(buttonView, isChecked)
            })
        }
        return this
    }

    fun setBackgroundColor(@ColorInt color: Int): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        view.setBackgroundColor(color)
        return this
    }

    fun setButtonDrawable(@DrawableRes resId: Int): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is CompoundButton) {
            view.setButtonDrawable(resId)
        }
        return this
    }

    fun setBackgroundDrawable(@DrawableRes resId: Int): XCoreUIBinderHelper {
        val view = ensureView() ?: return this

        view.setBackgroundResource(resId)
        return this
    }

    fun setImageResource(@DrawableRes resId: Int): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is ImageView) {
            view.setImageResource(resId)
        }
        return this
    }

    fun setImageBitmap(bitmap: Bitmap): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is ImageView) {
            view.setImageBitmap(bitmap)
        }
        return this
    }

    /**
     * 设置删除线

     * @return
     */
    fun setDeleteLine(): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is TextView) {
            view.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        return this
    }

    val isChecked: Boolean
        get() {
            val view = ensureView() ?: return false
            if (view is CompoundButton) {
                return view.isChecked
            }
            return false
        }

    val text: CharSequence?
        get() {
            val view = ensureView() ?: return null
            if (view is TextView) {
                return view.text
            }
            return null
        }

    fun addView(childView: View): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is ViewGroup) {
            view.addView(childView)
        }
        return this
    }

    fun removeAllViews(): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is ViewGroup) {
            view.removeAllViews()
        }
        return this
    }

    /**
     * 设置TextView的Drawable是否可见，比如drawableLeft，drawableRight等

     * @param isVisible 是否可见
     * *
     * @param pos       drawable的位置，只能是0,1,2,3 分别是左上右下
     * *
     * @return
     */
    fun setTextViewDrawableVisible(isVisible: Boolean, pos: Int): XCoreUIBinderHelper {
        val view = ensureView() ?: return this
        if (view is TextView) {
            val drawables = view.compoundDrawables
            if (drawables.size == 4 && pos >= 0 && pos < 4) {
                val drawable = drawables[pos]
                if (drawable != null) {
                    drawable.alpha = if (isVisible) 255 else 0
                }
            }
        }
        return this
    }

    abstract fun setImageUrl(url: String?): XCoreUIBinderHelper

}

