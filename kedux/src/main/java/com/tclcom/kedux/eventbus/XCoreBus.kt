package com.tclcom.kedux.eventbus


import com.squareup.otto.Bus


class XCoreBus private constructor() {
    private var mBus: Bus? = null


    init {
        mBus = Bus()
    }

    /**
     * 注册

     * @param component
     */
    fun registerComponent(component: Any?) {
        if (component == null) {
            return
        }
        mBus?.register(component)
    }

    fun unregisterComponent(component: Any?) {
        if (component == null) {
            return
        }
        mBus?.unregister(component)
    }

    fun post(event: Any) {
        mBus?.post(event)
    }

    companion object {
        val instance: XCoreBus by lazy { XCoreBus() }
    }

}
